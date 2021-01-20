package com.carroll.blog.search.dao;

import com.carroll.blog.search.model.EsLearnTestHundred;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EsLearnTestDao {
    List<EsLearnTestHundred> getAllEsTestList(@Param("id") Long id);

}
