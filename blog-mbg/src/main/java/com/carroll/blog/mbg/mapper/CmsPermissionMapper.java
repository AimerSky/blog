package com.carroll.blog.mbg.mapper;

import com.carroll.blog.mbg.model.CmsPermission;
import com.carroll.blog.mbg.model.CmsPermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface CmsPermissionMapper {
    long countByExample(CmsPermissionExample example);

    int deleteByExample(CmsPermissionExample example);

    int deleteByPrimaryKey(Integer permissionId);

    int insert(CmsPermission record);

    int insertSelective(CmsPermission record);

    List<CmsPermission> selectByExample(CmsPermissionExample example);

    CmsPermission selectByPrimaryKey(Integer permissionId);

    int updateByExampleSelective(@Param("record") CmsPermission record, @Param("example") CmsPermissionExample example);

    int updateByExample(@Param("record") CmsPermission record, @Param("example") CmsPermissionExample example);

    int updateByPrimaryKeySelective(CmsPermission record);

    int updateByPrimaryKey(CmsPermission record);
}