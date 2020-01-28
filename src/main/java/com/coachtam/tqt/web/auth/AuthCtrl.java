package com.coachtam.tqt.web.auth;

import com.coachtam.tqt.config.properties.JwtProperties;
import com.coachtam.tqt.config.utils.JwtUtils;
import com.coachtam.tqt.config.utils.UserInfo;
import com.coachtam.tqt.entity.User;
import com.coachtam.tqt.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Copyright (C), 2018-2020
 * @Author: JAVA在召唤
 * @Date: 2020-01-28 13:46
 * @Description:
 */
@Slf4j
@RestController
@EnableConfigurationProperties(JwtProperties.class)
public class AuthCtrl {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/oauth/token")
    public ResponseEntity<Map<String,String>> token(
            @RequestHeader("Authorization")String authorization,
            @RequestParam("grant_type")String grantType,
            @RequestParam("username")String username,
            @RequestParam("password")String password,HttpServletRequest request,HttpServletResponse response
    ){


        Map<String, String> map = new HashMap<>();
        if(!checkHeaderAuth(request,response))
        {
            map.put("error","invalid_grant");
            map.put("error_description","未认证客户端");
            return ResponseEntity.badRequest().body(map);
        }

        User user = userService.findByUsername(username);
        if(user==null)
        {
            map.put("error","invalid_grant");
            map.put("error_description","用户名错误");
            return ResponseEntity.badRequest().body(map);
        }
        //判断是否过期
        if(user.getState().equals(0))
        {
            map.put("error","invalid_grant");
            map.put("error_description","用户已失效");
            return ResponseEntity.badRequest().body(map);
        }
        //判断密码是否正确
        if(!passwordEncoder.matches(password,user.getPassword()))
        {
            map.put("error","invalid_grant");
            map.put("error_description","密码错误");
            return ResponseEntity.badRequest().body(map);
        }
        UserInfo userInfo = new UserInfo(user.getId(), user.getUserName());
        try {
            String token = JwtUtils.generateToken(userInfo, jwtProperties.getPrivateKey(), jwtProperties.getExpire());
            map.put("access_token", token);
            map.put("token_type", "bearer");
            map.put("expires_in", jwtProperties.getExpire().toString());
            map.put("scope", "all");
            return ResponseEntity.ok(map);
        } catch (Exception e) {
           log.error("登陆认证错误:{}",e.getMessage());
            map.put("error","invalid_grant");
            map.put("error_description","创建token失败");
            return ResponseEntity.badRequest().body(map);
        }


    }

    @PostMapping("/logout")
    public ResponseEntity<Void> token(
    ){
        return ResponseEntity.ok(null);
    }


    private boolean checkHeaderAuth(HttpServletRequest request, HttpServletResponse response) {

        String auth = request.getHeader("Authorization");
//        System.out.println("auth encoded in base64 is " + getFromBASE64(auth));

        if ((auth != null) && (auth.length() > 6)) {
            auth = auth.substring(6, auth.length());

            String decodedAuth = getFromBASE64(auth);
            if(jwtProperties.getClients().contains(decodedAuth))
            {
                return true;
            }
            return false;
        }else{
            return false;
        }

    }

    private String getFromBASE64(String s) {
        if (s == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(s);
            return new String(b);
        } catch (Exception e) {
            return null;
        }
    }
}
