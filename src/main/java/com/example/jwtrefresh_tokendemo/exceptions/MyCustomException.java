package com.example.jwtrefresh_tokendemo.exceptions;

import com.example.jwtrefresh_tokendemo.result.ResponseStatusEnum;

/**
 * 自定义异常
 * 目的：统一处理异常信息
 * 便于解耦，拦截器、service与controller 异常错误的解耦，
 * 不会被service返回的类型而限制
 */
public class MyCustomException extends RuntimeException {

    private ResponseStatusEnum responseStatusEnum;
    private Object object;

    public MyCustomException(ResponseStatusEnum responseStatusEnum) {
        super("异常状态码为：" + responseStatusEnum.status()
                + "；具体异常信息为：" + responseStatusEnum.msg());
        this.responseStatusEnum = responseStatusEnum;
    }

    public MyCustomException(ResponseStatusEnum responseStatusEnum, Object object) {
        super("异常状态码为：" + responseStatusEnum.status()
                + "；具体异常信息为：" + responseStatusEnum.msg());
        this.responseStatusEnum = responseStatusEnum;
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public ResponseStatusEnum getResponseStatusEnum() {
        return responseStatusEnum;
    }

    public void setResponseStatusEnum(ResponseStatusEnum responseStatusEnum) {
        this.responseStatusEnum = responseStatusEnum;
    }
}
