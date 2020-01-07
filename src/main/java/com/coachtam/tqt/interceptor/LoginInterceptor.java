package com.coachtam.tqt.interceptor;

import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Copyright (C), 2018-2020
 * @Author: JAVA在召唤
 * @Date: 2020-01-07 14:37
 * @Description:
 */
@Component
@Order(10)
public class LoginInterceptor implements HandlerInterceptor {


    private final static ThreadLocal<org.springframework.security.core.userdetails.User> threadLocal = new ThreadLocal<>();

    public static User getCurrUser() {
        return threadLocal.get();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        org.springframework.security.core.userdetails.User user  = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        threadLocal.set(user);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        threadLocal.remove();
    }
}
