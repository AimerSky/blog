package com.carroll.blog.mbg;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;

import java.util.List;

/**
 * @author aixl
 * @date 2020/1/13 15:40
 */
public class MyBatisPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> list) {
        return true;
    }


    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
        //添加Mapper的import
        interfaze.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Component"));
        //添加Mapper的注解
        interfaze.addAnnotation("@Component");
        return true;
    }


}
