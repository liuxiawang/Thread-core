package com.example.Module1.juc;

import java.util.concurrent.CountDownLatch;

/**
 * 使用CountDownLatch
 *
 * Created by Administrator on 2018/12/5 0005.
 */
public class UseCountDownLatch {

    public static void main(String[] args) {

        // new CountDownLatch要设置一个值，表示唤醒该对象需要几次唤醒，即几次调用countDownLatch.countDown()方法
        CountDownLatch countDownLatch = new CountDownLatch(2);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.err.println("进入T1线程~");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.err.println("T1等待3秒，通知T3线程~");
                countDownLatch.countDown();
            }
        }, "t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.err.println("进入T2线程~");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.err.println("T2也等待3秒，通知T3线程~");
                countDownLatch.countDown();
            }
        }, "t2");

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.err.println("进入T3线程并等待通知~");
                try {
                    // 线程等待
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.err.println("T3线程继续执行~");

            }
        }, "t3");

        t1.start();
        t2.start();
        t3.start();
    }
}
