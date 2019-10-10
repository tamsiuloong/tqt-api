package com.coachtam.tqt.config.security;//package cn.tqt.security.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created on 2018/1/19.
 *
 * @author coachtam
 * @since 1.0
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager manager = super.authenticationManagerBean();
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/api/user/register","/image","/sound","/swagger*/**","/webjars/**","/swagger-resources/**","/v2/api-docs/**").permitAll()
//                .requestMatchers()
//                .antMatchers(HttpMethod.POST,"/api/user/register")
//                .antMatchers(HttpMethod.OPTIONS, "/oauth/*", "/**")
//                .and()
//                .cors()
                .and()
                .formLogin().and()
                .httpBasic().and()
                .csrf().disable();

//        http.requestMatchers().antMatchers(HttpMethod.OPTIONS, "/oauth/**")
//                .and()
//                .cors()
//                .and()
//                .csrf().disable();
    }
}
