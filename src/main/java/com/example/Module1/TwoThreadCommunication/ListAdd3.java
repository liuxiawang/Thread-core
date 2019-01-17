package com.example.Module1.TwoThreadCommunication;

import java.util.ArrayList;
import java.util.List;

/**
 * A线程添加是个元素到List中，当添加到第五个之后，B线程要执行一段业务逻辑
 * 涉及到两个线程之间通信唤醒
 * <p>
 * 使用Object锁，与notify方法与wait方法，配合synchronize关键字
 * 但是有一个弊端就是要等调用了notify方法的线程执行完之后，Object锁才被释放。调用了wait()方法的线程才能启动运行
 * 本质就是notify方法不释放锁，而wait方法释放锁。而代码又使用了synchronize修饰（notify、wait方法必须配合synchronize关键字使用）
 * <p>
 * Created by Administrator on 2018/11/28 0028.
 */
public class ListAdd3 {

    private static List<String> list = new ArrayList<>();

    private static Object lock = new Object();

    public void add() {
        list.add("ABC");
    }

    public int size() {
        return list.size();
    }

    public static void main(String[] args) throws InterruptedException {

        ListAdd3 listAdd = new ListAdd3();

        Thread A = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    for (int i = 0; i < 10; i++) {
                        listAdd.add();
                        System.err.println(Thread.currentThread().getName() + "线程往List中添加了一个元素");
                        if (listAdd.size() == 5) {
                            lock.notify();
                        }
                        try {
                            Thread.sleep(500l);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, "A");

        Thread B = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (lock) {
                        if (listAdd.size() != 5) {
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
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
