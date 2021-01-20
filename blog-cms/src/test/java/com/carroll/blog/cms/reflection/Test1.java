package com.carroll.blog.cms.reflection;

public class Test1 {
    public static void main(String[] args) throws ClassNotFoundException {
        Class c1 = Class.forName("com.carroll.blog.cms.reflection.Student");
        System.out.println(c1);

        Class c2 = Class.forName("com.carroll.blog.cms.reflection.Teacher");
        System.out.println(c2);

        Class c3 = c1.getSuperclass();
        System.out.println(c3);


    }
}


class Person {
    String name;
}

class Student extends Person {
    public Student() {
        this.name = "学生";
    }
}

class Teacher extends Person {
    public Teacher() {
        this.name = "老师";
    }
}
