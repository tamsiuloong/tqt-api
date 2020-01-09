package com.coachtam.tqt.config.mvc;

import com.coachtam.tqt.interceptor.LogInterceptor;
import com.coachtam.tqt.interceptor.LoginInterceptor;
import com.coachtam.tqt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;

/**
 * @Copyright (C), 2018-2020
 * @Author: JAVA在召唤
 * @Date: 2020-01-07 14:44
 * @Description:
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor ;

    @Autowired
    private LogInterceptor logInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/api/**");
        registry.addInterceptor(logInterceptor).addPathPatterns("/api/user/info","/api/student/dashboard/index");
    }
}
