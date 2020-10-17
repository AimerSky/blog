package com.carroll.blog.mbg.mapper;

import com.carroll.blog.mbg.model.CmsResource;
import com.carroll.blog.mbg.model.CmsResourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface CmsResourceMapper {
    long countByExample(CmsResourceExample example);

    int deleteByExample(CmsResourceExample example);

    int deleteByPrimaryKey(Integer resourceId);

    int insert(CmsResource record);

    int insertSelective(CmsResource record);

    List<CmsResource> selectByExample(CmsResourceExample example);

    CmsResource selectByPrimaryKey(Integer resourceId);

    int updateByExampleSelective(@Param("record") CmsResource record, @Param("example") CmsResourceExample example);

    int updateByExample(@Param("record") CmsResource record, @Param("example") CmsResourceExample example);

    int updateByPrimaryKeySelective(CmsResource record);

    int updateByPrimaryKey(CmsResource record);
}