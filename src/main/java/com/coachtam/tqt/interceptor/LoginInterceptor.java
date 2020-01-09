package com.coachtam.tqt.interceptor;

import com.coachtam.tqt.config.security.UserDetail;
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
public class LoginInterceptor implements HandlerInterceptor {


    private final static ThreadLocal<UserDetail> threadLocal = new ThreadLocal<>();


    public static UserDetail getCurrUser() {
        return threadLocal.get();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserDetail user  = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        threadLocal.set(user);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        threadLocal.remove();
    }
}
