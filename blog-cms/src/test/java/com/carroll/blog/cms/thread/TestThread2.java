package com.carroll.blog.cms.thread;

/**
 * 实现Runnable接口 实现线程
 */
public class TestThread2 implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 30; i++) {
            System.out.println("thread在打印！！！" + i);
        }
    }

    public static void main(String[] args) {

        TestThread2 t = new TestThread2();
        new Thread(t).start();

        for (int i = 0; i < 1000; i++) {
            System.out.println("main在打印！！！" + i);
        }
    }
}
