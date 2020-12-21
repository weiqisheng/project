package com.weiqs.learn.project.security.jwt;

/**
 * @author weiqisheng
 * @Title: JWTAuthenticationFilter
 * @ProjectName myProject
 * @Description: TODO  登录拦截器，返回token给前端
 * @date 2020/12/1114:41
 */



import com.weiqs.learn.project.security.utils.ResponseUtil;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * 验证用户名和密码正确之后返回token给前端
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
        // 设置登录URL
        super.setFilterProcessesUrl("/auth/login");
  }

    /**
     * 验证操作，接收并解析用户凭证
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        // 从输入流中获取到登录的信息
        // 创建一个token并调用authenticationManager.authenticate() 让Spring security进行验证
        String username= request.getParameter("username");
        String password= request.getParameter("password");

        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
    }

    /**
     * 验证成功后待用的方法
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        // 从userDetail中获取权限信息
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        //创建token
        String token = JwtTokenUtils.createToken(user.getUsername(), authorities.toString());
        response.setHeader("token", JwtTokenUtils.TOKEN_PREFIX +token);
        ResponseUtil.responseJson(response,200, token);
    }

    /**
     * 验证失败调用的方法
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        String returenData = "";
        //账号过期
        if (failed instanceof AccountExpiredException){
            returenData="账号过期";
        }
        //密码错误
        else if (failed instanceof BadCredentialsException){
            returenData="密码错误";
        }
        else if (failed instanceof CredentialsExpiredException){
            returenData="密码过期";
        }else if (failed instanceof DisabledException){
            returenData = "账号不可用";
        }else if (failed instanceof LockedException){
            returenData = "账号锁定";
        }else if (failed instanceof InternalAuthenticationServiceException){
            returenData = "用户不存在";
        }else {
            returenData = "未知异常";
        }
        ResponseUtil.responseJson(response,400,returenData);
    }
}
