package com.coachtam.tqt.interceptor;

import com.coachtam.tqt.config.properties.JwtProperties;
import com.coachtam.tqt.utils.jwt.JwtUtils;
import com.coachtam.tqt.utils.jwt.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * @Copyright (C), 2018-2020
 * @Author: JAVA在召唤
 * @Date: 2020-01-07 14:37
 * @Description:
 */
@Component
@EnableConfigurationProperties(JwtProperties.class)
public class AuthInterceptor implements HandlerInterceptor {

    @Resource
    private JwtProperties jwtProperties;



    private final static ThreadLocal<UserInfo> threadLocal = new ThreadLocal<>();


    public static UserInfo getCurrUser() {
        return threadLocal.get();
    }

//    private final static String ERROR_MSG = "{\n" +
//            "        \"code\":401\n" +
//            "        \"message\": \"Unauthorized\"\n" +
//            "    }";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getParameter(jwtProperties.getParamName());
        if (StringUtils.isBlank(token)){
            //2.未登录，返回401
            render(response,401,"未授权");
            return false;
        }
        //3.有token，查询用户信息
        try{
            //4.解析成功，说明已经登录
            UserInfo userInfo = JwtUtils.getInfoFromToken(token,jwtProperties.getPublicKey());
            //5.放入线程域
            threadLocal.set(userInfo);

            //6.判断是否有权限
            if (handler instanceof HandlerMethod){
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                //查看方法注解
                RolesAllowed rolesAllowd = handlerMethod.getMethodAnnotation(RolesAllowed.class);
                if(rolesAllowd==null)
                {
                    //再查看类注解
                    rolesAllowd = ((HandlerMethod) handler).getBean().getClass().getAnnotation(RolesAllowed.class);
                    if(rolesAllowd==null)
                    {
                        return true;
                    }
                }
                String[] value = rolesAllowd.value();

                if(userInfo.getRoles().stream().anyMatch(role -> Arrays.stream(value).anyMatch(v -> v.equals(role))))
                {
                    return true;
                }
                else
                {
                    render(response,403,"没有对应权限");
                    return false;
                }
            }
            return true;
        }catch (Exception e){
            //7.抛出异常，证明未登录，返回401
            render(response,401,"未授权");
            return false;
        }
    }

    private void render(HttpServletResponse response,int status, String msg) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(status);
        response.getWriter().write("{" +
                "\"code\":401" +
                ",\"message\": \""+msg+"\"" +
                "}");
        response.getWriter().flush();
    }



    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        threadLocal.remove();
    }
}
