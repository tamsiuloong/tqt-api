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
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            @RequestParam("password")String password
    ){


        Map<String, String> map = new HashMap<>();

        Base64 base64 = new Base64();
        try {
            String s = new String(base64.decode(authorization), "UTF-8");
            System.out.println(s);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        User user = userService.findByUsername(username);
        if(user==null)
        {
            map.put("error","invalid_grant");
            map.put("error_description","用户名错误");
        }
        //判断是否过期
        if(user.getState().equals(0))
        {
            map.put("error","invalid_grant");
            map.put("error_description","用户已失效");
        }
        //判断密码是否正确
        if(!passwordEncoder.matches(password,user.getPassword()))
        {
            map.put("error","invalid_grant");
            map.put("error_description","密码错误");
        }
        UserInfo userInfo = new UserInfo(user.getId(), user.getUserName());
        try {
            String token = JwtUtils.generateToken(userInfo, jwtProperties.getPrivateKey(), jwtProperties.getExpire());
            map.put("access_token", token);
            map.put("token_type", "bearer");
            map.put("expires_in", jwtProperties.getExpire().toString());
            map.put("scope", "all");
        } catch (Exception e) {
           log.error("登陆认证错误:{}",e.getMessage());
            map.put("error","invalid_grant");
            map.put("error_description","创建token失败");
        }

        return ResponseEntity.ok(map);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> token(
    ){
        return ResponseEntity.ok(null);
    }
}
