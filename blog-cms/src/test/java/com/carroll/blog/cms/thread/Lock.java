package com.carroll.blog.cms.thread;

/**
 * 线程锁
 */
public class Lock {
    public static void main(String[] args) {
        Markup m1 = new Markup(1, "萧红");
        Markup m2 = new Markup(2, "白雪");
        m1.start();
        m2.start();

    }

}

//口红
class Lipstick {
}

//镜子
class Mirror {
}

class Markup extends Thread {
    static Mirror mirror = new Mirror();
    static Lipstick lipstick = new Lipstick();


    //选择
    private int choice;

    //名字
    private String girlName;

    public Markup(int choice, String girlName) {
        this.choice = choice;
        this.girlName = girlName;
    }


    @Override
    public void run() {
        try {
            markup();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void markup() throws InterruptedException {
        if (choice == 1) {
            synchronized (mirror) {
                System.out.println(girlName + "获得了镜子！");
                //Thread.sleep(1000);
                synchronized (lipstick) {
                    System.out.println(girlName + "获得了口红！");

                }
            }
        } else {
            synchronized (lipstick) {
                System.out.println(girlName + "获得了口红！");
                //Thread.sleep(2000);
                synchronized (mirror) {
                    System.out.println(girlName + "获得了镜子！");

                }
            }
        }

    }

    public int getChoice() {
        return choice;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }

    public String getGirlName() {
        return girlName;
    }

    public void setGirlName(String girlName) {
        this.girlName = girlName;
    }
}
