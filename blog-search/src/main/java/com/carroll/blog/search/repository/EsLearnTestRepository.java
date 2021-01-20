package com.carroll.blog.search.repository;

import com.carroll.blog.search.model.EsLearnTestHundred;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * ElasticsearchRepository 封装了大量的基础操作，通过它可以很方便的操作ElasticSearch的数据
 */
public interface EsLearnTestRepository extends ElasticsearchRepository<EsLearnTestHundred, Long> {


    /**
     * 搜索查询
     *
     * @param name              名称
     * @param sex               性别
     * @param page              分页信息
     */
    Page<EsLearnTestHundred> findByNameOrSex(String name, Boolean sex, Pageable page);

}
