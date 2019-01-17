package com.example.demo.aqs;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2018/12/23 0023.
 */
public class UseManyCondition {

    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();

    public void m1() {
        lock.lock();
        try {
            System.err.println("当前线程" + Thread.currentThread().getName() + "进入m1。。。");
            Thread.sleep(1000);
            condition1.await();
            System.err.println("当前线程" + Thread.currentThread().getName() + "，m1被唤醒。。。");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void m2() {
        lock.lock();
        try {
            System.err.println("当前线程" + Thread.currentThread().getName() + "进入m2。。。");
            condition1.await();
            System.err.println("当前线程" + Thread.currentThread().getName() + "，m2被唤醒。。。");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void m3() {
        lock.lock();
        try {
            System.err.println("当前线程" + Thread.currentThread().getName() + "进入m3。。。");
            condition2.await();
            System.err.println("当前线程" + Thread.currentThread().getName() + "，m3被唤醒。。。");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void m4() {
        lock.lock();
        try {
            System.err.println("当前线程" + Thread.currentThread().getName() + "进入m4。。。");
            condition1.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void m5() {
        lock.lock();
        try {
            System.err.println("当前线程" + Thread.currentThread().getName() + "进入m5。。。");
            condition2.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        UseManyCondition useManyCondition = new UseManyCondition();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                useManyCondition.m1();
            }
        }, "t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                useManyCondition.m2();
            }
        }, "t2");

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                useManyCondition.m3();
            }
        }, "t3");

        Thread t4 = new Thread(new Runnable() {
            @Override
            public void run() {
                useManyCondition.m4();
            }
        }, "t4");

        Thread t5 = new Thread(new Runnable() {
            @Override
            public void run() {
                useManyCondition.m5();
            }
        }, "t5");


        t1.start();
        t2.start();
        t3.start();

        Thread.sleep(2000);
        t4.start();
        Thread.sleep(2000);
        t5.start();

    }

}
