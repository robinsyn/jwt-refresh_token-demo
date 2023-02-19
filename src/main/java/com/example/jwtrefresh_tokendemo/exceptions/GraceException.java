package com.example.jwtrefresh_tokendemo.exceptions;


import com.example.jwtrefresh_tokendemo.result.ResponseStatusEnum;

/**
 * 优雅的处理异常，统一封装
 */
public class GraceException {

    public static void display(ResponseStatusEnum responseStatusEnum) {
        throw new MyCustomException(responseStatusEnum);
    }

    public static void display(ResponseStatusEnum responseStatusEnum, Object object) {
        throw new MyCustomException(responseStatusEnum, object);
    }

}
