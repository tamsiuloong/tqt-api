package com.coachtam.tqt.interceptor;

import com.coachtam.tqt.config.properties.JwtProperties;
import com.coachtam.tqt.config.utils.JwtUtils;
import com.coachtam.tqt.config.utils.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Copyright (C), 2018-2020
 * @Author: JAVA在召唤
 * @Date: 2020-01-07 14:37
 * @Description:
 */
@Component
@EnableConfigurationProperties(JwtProperties.class)
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private JwtProperties jwtProperties;



    private final static ThreadLocal<UserInfo> threadLocal = new ThreadLocal<>();


    public static UserInfo getCurrUser() {
        return threadLocal.get();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getParameter(jwtProperties.getParamName());
        if (StringUtils.isBlank(token)){
            //2.未登录，返回401
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        //3.有token，查询用户信息
        try{
            //4.解析成功，说明已经登录
            UserInfo userInfo = JwtUtils.getInfoFromToken(token,jwtProperties.getPublicKey());
            //5.放入线程域
            threadLocal.set(userInfo);
            return true;
        }catch (Exception e){
            //6.抛出异常，证明未登录，返回401
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        threadLocal.remove();
    }
}
