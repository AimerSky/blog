package com.carroll.blog.mbg.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class CmsManagerRole implements Serializable {
    private Integer managerRoleId;

    private Integer managerId;

    private Integer roleId;

    private static final long serialVersionUID = 1L;

    public Integer getManagerRoleId() {
        return managerRoleId;
    }

    public void setManagerRoleId(Integer managerRoleId) {
        this.managerRoleId = managerRoleId;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", managerRoleId=").append(managerRoleId);
        sb.append(", managerId=").append(managerId);
        sb.append(", roleId=").append(roleId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}