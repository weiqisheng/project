package com.weiqs.learn.project.security.jwt;


import com.weiqs.learn.project.security.utils.ResponseUtil;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author weiqisheng
 * @Title: JWTAuthenticationEntryPoint
 * @ProjectName myProject
 * @Description: TODO 配置一个自定义类 用于进行匿名用户访问资源时无权限的处理
 * @date 2020/12/1116:26
 */
public class JWTAuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint{
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {

        ResponseUtil.responseJson(response,401,"您未登录，没有访问权限");
    }
}
