package com.coachtam.tqt.interceptor;

import com.coachtam.tqt.config.security.UserDetail;
import com.coachtam.tqt.entity.UserEventLog;
import com.coachtam.tqt.event.UserEvent;
import com.coachtam.tqt.utils.ExamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @Copyright (C), 2018-2020
 * @Author: JAVA在召唤
 * @Date: 2020-01-07 14:37
 * @Description:
 */
@Component
public class LogInterceptor implements HandlerInterceptor {

    @Autowired
    private  ApplicationEventPublisher eventPublisher;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserDetail user  = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEventLog userEventLog = new UserEventLog(user.getId(), user.getUsername(), user.getName(), new Date());
        userEventLog.setContent("登陆系统");
        eventPublisher.publishEvent(new UserEvent(userEventLog));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
