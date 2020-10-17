package com.carroll.blog.common.api;

/**
 * 封装API的错误码
 * Created by carroll on 2020/5/30.
 */
public interface IErrorCode {
    long getCode();

    String getMessage();
}
