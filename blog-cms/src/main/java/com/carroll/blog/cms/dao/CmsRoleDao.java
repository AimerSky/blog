package com.carroll.blog.cms.dao;

import com.carroll.blog.mbg.model.CmsMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by carroll on 2020/6/8.
 */
@Component
public interface CmsRoleDao {
    List<CmsMenu> getManinMenuList(@Param("managerId") Integer managerId);

    List<String> getRoleNames(@Param("managerId") Integer managerId);


}
