package com.carroll.blog.cms.dao;

import com.carroll.blog.mbg.model.CmsManagerRole;
import com.carroll.blog.mbg.model.CmsResource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by carroll on 2020/5/31.
 */

@Component
public interface CmsManagerDao {
    /**
     * 获取资源相关用户ID列表
     */
    List<Integer> getCmsManagerIdList(@Param("resourceId") Integer resourceId);

    /**
     * 获取用户所有可访问资源
     */
    List<CmsResource> getCmsResourceList(@Param("managerId") Integer mcManagerId);

    /**
     * 获取所有用户账号
     */
    List<String> getAllUserName();

    /**
     * 批量添加关系
     */
    void addManagerRole(List<CmsManagerRole> cmsManagerRoleList);
}
