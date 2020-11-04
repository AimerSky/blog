package com.carroll.blog.cms.dto;

public enum CmsRoleStateEnum {
    Normal(1), Stop(2),Delete(3);
    // 成员变量
    private int state;

    // 构造方法
    private CmsRoleStateEnum(int state) {
        this.state = state;
    }

    public int getValue() {
        return state;
    }



}
