package com.example.Module1.aqs;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用重入锁.
 *
 * Created by Administrator on 2018/12/23 0023.
 */
public class UseReentrantLock {

    /**
     * AQS, abstract queue synchronized, 抽象队列同容器
     *
     * AQS中有一个由volatile 修饰的int类型的state，用来存放共享资源。
     * 还有一个先进先出的队列容器FIFO
     */

    // 重入锁，可以设置为公平重入锁，与不公平重入锁，就是在new时传入true\false
    private ReentrantLock reentrantLock = new ReentrantLock();

    public void exec() {
        reentrantLock.lock();
        try {
            System.err.println("当前线程" + Thread.currentThread().getName() + "进入。。。");
            Thread.sleep(2000);
            System.err.println("当前线程"+ Thread.currentThread().getName() +"逻辑处理完成，退出。。。");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

    public static void main(String[] args) {

        UseReentrantLock useReentrantLock = new UseReentrantLock();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                useReentrantLock.exec();
            }
        }, "t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                useReentrantLock.exec();
            }
        }, "t2");

        t1.start();
        t2.start();

    }

}
