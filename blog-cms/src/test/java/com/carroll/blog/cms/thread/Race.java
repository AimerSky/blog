package com.carroll.blog.cms.thread;

import cn.hutool.core.util.ObjectUtil;

/**
 * 小龟赛跑
 */
public class Race implements Runnable {
    private String winnder;

    @Override
    public void run() {
        for (int i = 0; i <= 100; i++) {

            if (Thread.currentThread().getName().equals("兔子") && i % 10 == 0 && i != 0) {
                try {
                    System.out.println("兔子睡着了!-!");
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    System.out.println("Race--休眠异常");

                }
            }

            if (isGameOver(i)) {
                break;
            }
            System.out.println(Thread.currentThread().getName() + "--->跑了" + i + "步");

        }
    }


    public static void main(String[] args) {
        Race race = new Race();
        new Thread(race, "兔子").start();
        new Thread(race, "乌龟").start();

    }

    private boolean isGameOver(int steps) {
        if (!ObjectUtil.isEmpty(winnder)) {
            return true;
        }
        if (steps == 100) {
            winnder = Thread.currentThread().getName();
            System.out.println(Thread.currentThread().getName() + "赢得了胜利！！！");
            return true;
        }
        return false;

    }
}
