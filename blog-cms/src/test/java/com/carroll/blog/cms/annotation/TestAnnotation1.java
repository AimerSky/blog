package com.carroll.blog.cms.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@MyAnnotation1(name = "aimu")
public class TestAnnotation1 {

    @MyAnnotation1(name = "aimu")
    public void test() {

    }

}


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@interface MyAnnotation1 {
    String name();
}
