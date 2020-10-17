package com.carroll.blog.mbg.mapper;

import com.carroll.blog.mbg.model.CmsManager;
import com.carroll.blog.mbg.model.CmsManagerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface CmsManagerMapper {
    long countByExample(CmsManagerExample example);

    int deleteByExample(CmsManagerExample example);

    int deleteByPrimaryKey(Integer managerId);

    int insert(CmsManager record);

    int insertSelective(CmsManager record);

    List<CmsManager> selectByExample(CmsManagerExample example);

    CmsManager selectByPrimaryKey(Integer managerId);

    int updateByExampleSelective(@Param("record") CmsManager record, @Param("example") CmsManagerExample example);

    int updateByExample(@Param("record") CmsManager record, @Param("example") CmsManagerExample example);

    int updateByPrimaryKeySelective(CmsManager record);

    int updateByPrimaryKey(CmsManager record);
}