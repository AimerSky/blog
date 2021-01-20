package com.carroll.blog.cms.reflection;

import java.util.ArrayList;
import java.util.List;

public class Test2 {

    static {
        System.out.println("Main被加载");
    }

    public static void main(String[] args) {
        /*Son s = new Son();
        System.out.println(Son.m);*/

        /*System.out.println(Son.b);*/

        /*List<Son> sonList = new ArrayList<Son>();*/

        System.out.println(Son.a);

    }
}

class Father {
    static {
        System.out.println("Father被加载");
        b = 300;
    }

    static int b = 100;
}

class Son extends Father {

    static {
        System.out.println("Son被加载");
        m = 300;
    }

    static int m = 100;
    static final int a = 500;

}
