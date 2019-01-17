package com.example.demo.aqs;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2018/12/23 0023.
 */
public class UseCondition {

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    public void m1() {
        lock.lock();
        try {
            System.err.println("当前线程" + Thread.currentThread().getName() + "进入m1方法。。。");
            Thread.sleep(2000);
            System.err.println("当前线程" + Thread.currentThread().getName() + "处理完成，等待唤醒。。。");
            // condition的await方法进行等待，可以使用signal方法唤醒
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
          lock.unlock();
        }
    }

    public void m2() {
        lock.lock();
        try {
            System.err.println("当前线程" + Thread.currentThread().getName() + "进入m2方法。。。");
            Thread.sleep(1000);
            System.err.println("当前线程" + Thread.currentThread().getName() + "处理完成，唤醒m1。。。");
            // condition的signal方法可以唤醒await方法
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        UseCondition useCondition = new UseCondition();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                useCondition.m1();
            }
        }, "t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                useCondition.m2();
            }
        }, "t2");

        t1.start();
        Thread.sleep(100);
        t2.start();
    }
}
