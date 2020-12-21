package com.weiqs.learn.project.security.utils;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author weiqisheng
 * @Title: ResponseUtil
 * @ProjectName myProject
 * @Description: TODO
 * @date 2020/12/1115:06
 */
public class ResponseUtil {

    public static void responseJson(HttpServletResponse response, int status, Object data){
        try {
            //设置编码，防止乱码问题
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(status);

            //在请求头里返回创建成功的token
            //设置请求头为带有Bearer前缀的token字符串
            response.setHeader("Access-Control-Allow-Origin","*");

            //处理编码方式，防止中文乱码
            response.setContentType("text/json;charset=utf-8");
            //将反馈塞到httpServletResponse中返回给前台
            response.getWriter().write(JSONObject.toJSONString(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
