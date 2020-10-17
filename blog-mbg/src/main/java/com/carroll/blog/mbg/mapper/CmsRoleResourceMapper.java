package com.carroll.blog.mbg.mapper;

import com.carroll.blog.mbg.model.CmsRoleResource;
import com.carroll.blog.mbg.model.CmsRoleResourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface CmsRoleResourceMapper {
    long countByExample(CmsRoleResourceExample example);

    int deleteByExample(CmsRoleResourceExample example);

    int deleteByPrimaryKey(Integer roleResourceId);

    int insert(CmsRoleResource record);

    int insertSelective(CmsRoleResource record);

    List<CmsRoleResource> selectByExample(CmsRoleResourceExample example);

    CmsRoleResource selectByPrimaryKey(Integer roleResourceId);

    int updateByExampleSelective(@Param("record") CmsRoleResource record, @Param("example") CmsRoleResourceExample example);

    int updateByExample(@Param("record") CmsRoleResource record, @Param("example") CmsRoleResourceExample example);

    int updateByPrimaryKeySelective(CmsRoleResource record);

    int updateByPrimaryKey(CmsRoleResource record);
}