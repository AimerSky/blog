package com.carroll.blog.cms.dto;

public enum CmsManagerStateEnum {
    Delete(1), Normal(2),Stop(3);
    // 成员变量
    private int state;

    // 构造方法
    private CmsManagerStateEnum(int state) {
        this.state = state;
    }

    public int getValue() {
        return state;
    }



}
