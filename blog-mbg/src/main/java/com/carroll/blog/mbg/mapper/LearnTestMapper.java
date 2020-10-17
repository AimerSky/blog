package com.carroll.blog.mbg.mapper;

import com.carroll.blog.mbg.model.LearnTest;
import com.carroll.blog.mbg.model.LearnTestExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface LearnTestMapper {
    long countByExample(LearnTestExample example);

    int deleteByExample(LearnTestExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LearnTest record);

    int insertSelective(LearnTest record);

    List<LearnTest> selectByExample(LearnTestExample example);

    LearnTest selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LearnTest record, @Param("example") LearnTestExample example);

    int updateByExample(@Param("record") LearnTest record, @Param("example") LearnTestExample example);

    int updateByPrimaryKeySelective(LearnTest record);

    int updateByPrimaryKey(LearnTest record);
}