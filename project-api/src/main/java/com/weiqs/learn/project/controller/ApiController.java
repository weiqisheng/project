package com.weiqs.learn.project.controller;

import com.weiqs.learn.project.security.common.ResultObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weiqisheng
 * @Title: ApiController
 * @ProjectName project
 * @Description: TODO
 * @date 2020/12/1814:41
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/test")
    public ResultObject test(){
        return ResultObject.success("hellow");
    }
}
