package com.weiqs.learn.project.security.jwt;

/**
 * @author weiqisheng
 * @Title: JWTAuthorizationFilter
 * @ProjectName myProject
 * @Description: TODO 权限校验
 * @date 2020/12/1116:06
 */

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * 登录成功后，走此类进行鉴权操作
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager){
        super(authenticationManager);
    }

    /**
     * 在过滤之前和之后执行的事件
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String tokenHeader = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
        //若请求头中没有Authorization信息，或者Authorization不以Bearer开头则直接放行
        if (StringUtils.isBlank(tokenHeader) || !tokenHeader.startsWith(JwtTokenUtils.TOKEN_PREFIX)){
            chain.doFilter(request,response);
            return;
        }
        //若请求头中有token,则调用下面的方法进行解析，并设置认证信息
        SecurityContextHolder.getContext().setAuthentication(getAuthentications(tokenHeader));
        super.doFilterInternal(request,response,chain);
    }

    /**
     * 从token中获取用户信息并新建一个token
     * @param tokenHeader
     * @return
     */
    private UsernamePasswordAuthenticationToken getAuthentications(String tokenHeader){
        //去掉前缀，获取token字符串
        String token = tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX,"");
        //从token中解密获取用户名
        String userName = JwtTokenUtils.getUserName(token);
        //从token中解密获取用户资源
        String resources  = JwtTokenUtils.getUserRole(token);

        String[] userResourceList = StringUtils.strip(resources,"[]").split(", ");
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Arrays.stream(userResourceList).forEach(resource -> authorities.add(new SimpleGrantedAuthority(resource)));
        if (StringUtils.isNoneBlank(userName)){
            return new UsernamePasswordAuthenticationToken(userName,null,authorities);
        }
        return null;
    }
}
