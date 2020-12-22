package com.carroll.blog.cms.thread;

/**
 * 继承Thread类 实现线程
 */
public class TestThread1 extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 30; i++) {
            System.out.println("thread在打印！！！" + i);
        }
    }

    public static void main(String[] args) {

        TestThread1 t = new TestThread1();
        t.start();

        for (int i = 0; i < 1000; i++) {
            System.out.println("main在打印！！！" + i);
        }
    }
}
