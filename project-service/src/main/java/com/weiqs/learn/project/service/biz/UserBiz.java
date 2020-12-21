package com.weiqs.learn.project.service.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author weiqisheng
 * @Title: UserBiz
 * @ProjectName project
 * @Description: TODO
 * @date 2020/12/1816:11
 */
@Service
public class UserBiz implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(userName)){
            throw new RuntimeException("用户名不能为空");
        }
        String password = passwordEncoder.encode("123456");
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE"));
        return new User("admin",password,authorities);
    }
}
