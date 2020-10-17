package com.carroll.blog.mbg.mapper;

import com.carroll.blog.mbg.model.CmsManagerRole;
import com.carroll.blog.mbg.model.CmsManagerRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface CmsManagerRoleMapper {
    long countByExample(CmsManagerRoleExample example);

    int deleteByExample(CmsManagerRoleExample example);

    int deleteByPrimaryKey(Integer managerRoleId);

    int insert(CmsManagerRole record);

    int insertSelective(CmsManagerRole record);

    List<CmsManagerRole> selectByExample(CmsManagerRoleExample example);

    CmsManagerRole selectByPrimaryKey(Integer managerRoleId);

    int updateByExampleSelective(@Param("record") CmsManagerRole record, @Param("example") CmsManagerRoleExample example);

    int updateByExample(@Param("record") CmsManagerRole record, @Param("example") CmsManagerRoleExample example);

    int updateByPrimaryKeySelective(CmsManagerRole record);

    int updateByPrimaryKey(CmsManagerRole record);
}