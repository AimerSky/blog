package com.carroll.blog.cms.thread;

/**
 * 生产者消费者模式（管程法）
 */
public class TestPC {
    public static void main(String[] args) {
        Buffer buffer = new Buffer();

        new Producer(buffer).start();
        new Consumer(buffer).start();

    }

}

/**
 * 生产者
 */
class Producer extends Thread {
    private Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 100; i++) {
            System.out.println("生产了第" + i + "只鸡！");
            buffer.push(new Chicken(i));
        }
    }
}

/**
 * 消费者
 */
class Consumer extends Thread {
    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 100; i++) {
            System.out.println("消费了第" + i + "只鸡！");
            buffer.pop();
        }
    }

}

/**
 * 缓冲区
 */
class Buffer {
    //容器
    private static Chicken[] chickens = new Chicken[10];
    //计数器
    private static int count = 0;

    /**
     * 放入产品
     */
    public synchronized void push(Chicken chicken) {
        if (count == chickens.length) {
            //容器满了停止生产，通知消费者消费
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //没有满放入产品
        chickens[count] = chicken;
        count++;
        //通知消费者，消费
        this.notifyAll();
    }

    public synchronized Chicken pop() {
        if (count == 0) {
            //不能消费，等待生产者生产
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //可以消费，
        count--;
        Chicken chicken = chickens[count];

        this.notify();
        //通知生产
        this.notifyAll();
        return chicken;
    }


}

/**
 * 鸡肉
 */
class Chicken {
    private int id;

    public Chicken(int id) {
        this.id = id;
    }
}
