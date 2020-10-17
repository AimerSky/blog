package com.carroll.blog.mbg.mapper;

import com.carroll.blog.mbg.model.CmsResourceCategory;
import com.carroll.blog.mbg.model.CmsResourceCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface CmsResourceCategoryMapper {
    long countByExample(CmsResourceCategoryExample example);

    int deleteByExample(CmsResourceCategoryExample example);

    int deleteByPrimaryKey(Integer resourceCategoryId);

    int insert(CmsResourceCategory record);

    int insertSelective(CmsResourceCategory record);

    List<CmsResourceCategory> selectByExample(CmsResourceCategoryExample example);

    CmsResourceCategory selectByPrimaryKey(Integer resourceCategoryId);

    int updateByExampleSelective(@Param("record") CmsResourceCategory record, @Param("example") CmsResourceCategoryExample example);

    int updateByExample(@Param("record") CmsResourceCategory record, @Param("example") CmsResourceCategoryExample example);

    int updateByPrimaryKeySelective(CmsResourceCategory record);

    int updateByPrimaryKey(CmsResourceCategory record);
}