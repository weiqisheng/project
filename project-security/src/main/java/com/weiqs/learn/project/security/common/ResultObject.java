package com.weiqs.learn.project.security.common;

import lombok.Data;

/**
 * @author weiqisheng
 * @Title: ResultObject
 * @ProjectName myProject
 * @Description: TODO
 * @date 2020/12/1114:43
 */
@Data
public class ResultObject<T> {

    private String code;

    private String msg;

    private T data;

    public ResultObject(){

    }

    public ResultObject(String code,String msg,T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResultObject<T> success(T data){
        return new ResultObject<T>("200","操作成功",data);
    }

    public static <T> ResultObject<T> success(T data, String msg){
        return new ResultObject<T>("200",msg,data);
    }

    public static <T> ResultObject<T> success(T data, String code, String msg){
        return new ResultObject<T>(code,msg,data);
    }

    public static <T> ResultObject<T> failed(T data){
        return new ResultObject<T>("400","操作失败",data);
    }

    public static <T> ResultObject<T> failed(T data, String msg){
        return new ResultObject<T>("400",msg,data);
    }

    public static <T> ResultObject<T> failed(T data, String code, String msg){
        return new ResultObject<T>(code,msg,data);
    }
}
