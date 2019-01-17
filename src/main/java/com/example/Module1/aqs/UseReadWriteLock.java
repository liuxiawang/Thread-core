package com.example.Module1.aqs;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 使用读写锁，其实是有两把锁，一把读锁一把写锁
 * 口诀：读读共享，写写互斥，读写互斥
 * 读锁与写锁不能共存，如下代码：t1 t2都是读方法，当只有t1 t2时，可能并发的去读；但只要存在写方法t3，那就必须先执行读操作或者执行写操作
 *
 * Created by Administrator on 2018/12/23 0023.
 */
public class UseReadWriteLock {

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
    private ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

    public void read() {
        readLock.lock();
        try {
            System.err.println("当前线程" + Thread.currentThread().getName() + "进入读方法。。。");
            Thread.sleep(2000);
            System.err.println("当前线程" + Thread.currentThread().getName() + "读取数据完成。。。");
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            readLock.unlock();
        }
    }

    public void write() {
        writeLock.lock();
        try {
            System.err.println("当前线程" + Thread.currentThread().getName() + "进入写方法。。。");
            Thread.sleep(3000);
            System.err.println("当前线程" + Thread.currentThread().getName() + "写入数据完成。。。");
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
        UseReadWriteLock useReadWriteLock = new UseReadWriteLock();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                useReadWriteLock.read();
            }
        }, "t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                useReadWriteLock.read();
            }
        }, "t2");

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                useReadWriteLock.write();
            }
        }, "t3");

        t1.start();
        t2.start();
        t3.start();

    }
}
