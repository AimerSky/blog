package com.carroll.blog.mbg.mapper;

import com.carroll.blog.mbg.model.CmsRoleMenu;
import com.carroll.blog.mbg.model.CmsRoleMenuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface CmsRoleMenuMapper {
    long countByExample(CmsRoleMenuExample example);

    int deleteByExample(CmsRoleMenuExample example);

    int deleteByPrimaryKey(Integer roleMenuId);

    int insert(CmsRoleMenu record);

    int insertSelective(CmsRoleMenu record);

    List<CmsRoleMenu> selectByExample(CmsRoleMenuExample example);

    CmsRoleMenu selectByPrimaryKey(Integer roleMenuId);

    int updateByExampleSelective(@Param("record") CmsRoleMenu record, @Param("example") CmsRoleMenuExample example);

    int updateByExample(@Param("record") CmsRoleMenu record, @Param("example") CmsRoleMenuExample example);

    int updateByPrimaryKeySelective(CmsRoleMenu record);

    int updateByPrimaryKey(CmsRoleMenu record);
}