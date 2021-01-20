package com.carroll.blog.mbg.mapper;

import com.carroll.blog.mbg.model.LearnTestHundred;
import com.carroll.blog.mbg.model.LearnTestHundredExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface LearnTestHundredMapper {
    long countByExample(LearnTestHundredExample example);

    int deleteByExample(LearnTestHundredExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LearnTestHundred record);

    int insertSelective(LearnTestHundred record);

    List<LearnTestHundred> selectByExample(LearnTestHundredExample example);

    LearnTestHundred selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LearnTestHundred record, @Param("example") LearnTestHundredExample example);

    int updateByExample(@Param("record") LearnTestHundred record, @Param("example") LearnTestHundredExample example);

    int updateByPrimaryKeySelective(LearnTestHundred record);

    int updateByPrimaryKey(LearnTestHundred record);
}