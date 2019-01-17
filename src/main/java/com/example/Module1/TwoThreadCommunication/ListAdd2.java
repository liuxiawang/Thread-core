package com.example.Module1.TwoThreadCommunication;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * A线程添加是个元素到List中，当添加到第五个之后，B线程要执行一段业务逻辑
 * 涉及到两个线程之间通信唤醒
 * <p>
 * 使用CountDownLatch类，可以在A线程达到add5次之后，不需等待A线程处理完，才释放Object锁，之后B线程才能执行
 * 这是一个异步线程通知的类
 * <p>
 * Created by Administrator on 2018/11/28 0028.
 */
public class ListAdd2 {

    private static List<String> list = new ArrayList<>();

    public void add() {
        list.add("ABC");
    }

    public int size() {
        return list.size();
    }

    public static void main(String[] args) throws InterruptedException {

        ListAdd2 listAdd = new ListAdd2();

        // 此处传入的数字，可以使用于多一次C线程处理之后才能被B线程调用的场景。A、C两个线程都调用了countDown()方法
        CountDownLatch countDownLatch = new CountDownLatch(2); // 数字代表需要发几次通知才能唤醒await()方法

        Thread A = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    listAdd.add();
                    System.err.println(Thread.currentThread().getName() + "线程往List中添加了一个元素");
                    if (listAdd.size() == 5) {
                        // 此处执行了2次countDown()方法，是因为在new对象时传入了2次countDown()方法才能唤醒await()
                        countDownLatch.countDown();
                        countDownLatch.countDown();
                    }
                    try {
                        Thread.sleep(500l);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "A");

        Thread B = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (listAdd.size() != 5) {
                            // await()方法表示等待，一直等待到有足够数量的countDown()方法唤醒为止
                            countDownLatch.await();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    throw new RuntimeException("实现业务过程中抛出了RuntimeException！！");
                }
            }
        }, "B");

        B.start();
        Thread.sleep(500);
        A.start();

    }
}
