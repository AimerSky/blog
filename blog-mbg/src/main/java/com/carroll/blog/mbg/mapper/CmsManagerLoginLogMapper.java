package com.carroll.blog.mbg.mapper;

import com.carroll.blog.mbg.model.CmsManagerLoginLog;
import com.carroll.blog.mbg.model.CmsManagerLoginLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface CmsManagerLoginLogMapper {
    long countByExample(CmsManagerLoginLogExample example);

    int deleteByExample(CmsManagerLoginLogExample example);

    int deleteByPrimaryKey(Long managerLoginLogId);

    int insert(CmsManagerLoginLog record);

    int insertSelective(CmsManagerLoginLog record);

    List<CmsManagerLoginLog> selectByExample(CmsManagerLoginLogExample example);

    CmsManagerLoginLog selectByPrimaryKey(Long managerLoginLogId);

    int updateByExampleSelective(@Param("record") CmsManagerLoginLog record, @Param("example") CmsManagerLoginLogExample example);

    int updateByExample(@Param("record") CmsManagerLoginLog record, @Param("example") CmsManagerLoginLogExample example);

    int updateByPrimaryKeySelective(CmsManagerLoginLog record);

    int updateByPrimaryKey(CmsManagerLoginLog record);
}