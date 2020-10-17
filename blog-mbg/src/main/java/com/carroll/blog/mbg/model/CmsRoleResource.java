package com.carroll.blog.mbg.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class CmsRoleResource implements Serializable {
    private Integer roleResourceId;

    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

    @ApiModelProperty(value = "资源ID")
    private Integer resourceId;

    private static final long serialVersionUID = 1L;

    public Integer getRoleResourceId() {
        return roleResourceId;
    }

    public void setRoleResourceId(Integer roleResourceId) {
        this.roleResourceId = roleResourceId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", roleResourceId=").append(roleResourceId);
        sb.append(", roleId=").append(roleId);
        sb.append(", resourceId=").append(resourceId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}