package com.carroll.blog.mbg.mapper;

import com.carroll.blog.mbg.model.CmsRole;
import com.carroll.blog.mbg.model.CmsRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface CmsRoleMapper {
    long countByExample(CmsRoleExample example);

    int deleteByExample(CmsRoleExample example);

    int deleteByPrimaryKey(Integer roleId);

    int insert(CmsRole record);

    int insertSelective(CmsRole record);

    List<CmsRole> selectByExample(CmsRoleExample example);

    CmsRole selectByPrimaryKey(Integer roleId);

    int updateByExampleSelective(@Param("record") CmsRole record, @Param("example") CmsRoleExample example);

    int updateByExample(@Param("record") CmsRole record, @Param("example") CmsRoleExample example);

    int updateByPrimaryKeySelective(CmsRole record);

    int updateByPrimaryKey(CmsRole record);
}