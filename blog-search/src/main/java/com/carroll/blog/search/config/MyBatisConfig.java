package com.carroll.blog.search.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatis配置类
 * Created by carroll on 2020/5/30.
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.carroll.blog.mbg.mapper", "com.carroll.blog.search.dao"})
public class MyBatisConfig {

}
