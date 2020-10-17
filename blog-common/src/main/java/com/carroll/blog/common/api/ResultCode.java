package com.carroll.blog.common.api;

/**
 * 枚举了一些常用API操作码
 * Created by carroll on 2020/5/30.
 */
public enum ResultCode implements IErrorCode {
    SUCCESS(10000, "操作成功!"),
    FAILED(50000, "操作失败!"),
    VALIDATE_FAILED(40004, "参数检验失败!"),
    UNAUTHORIZED(40001, "暂未登录或token已经过期!"),
    FORBIDDEN(40003, "没有相关权限!");
    private long code;
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
