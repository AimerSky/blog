package com.carroll.blog.cms.thread;

/**
 * 线程抢票
 */
public class TestThread3 implements Runnable {
    private int total = 20;

    @Override
    public void run() {

        while (true) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                System.out.println("TestThread3--休眠异常");
            }
            //抢票
            asd();
        }
    }


    private synchronized void asd() {
        if (total <= 0) {
            return;
        }
        System.out.println(Thread.currentThread().getName() + "--->抢到了地" + total-- + "张票！");

    }

    public static void main(String[] args) {
        TestThread3 t = new TestThread3();
        new Thread(t, "小明").start();
        new Thread(t, "黄牛党").start();
        new Thread(t, "打工人").start();

    }
}
