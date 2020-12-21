package com.weiqs.learn.project.controller;

import com.weiqs.learn.project.security.common.ResultObject;
import org.springframework.context.annotation.Role;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weiqisheng
 * @Title: RoleController
 * @ProjectName project
 * @Description: TODO
 * @date 2020/12/2115:02
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/test")
    public ResultObject test(){
        return ResultObject.success("权限设置");
    }
}
