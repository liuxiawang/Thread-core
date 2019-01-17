package com.example.demo.ThreadSafely;

/**
 * 线程安全.
 *
 * Created by Administrator on 2018/11/27 0027.
 */
public class MyThread extends Thread {

    private Integer count = 10;

    @Override
    public synchronized void run() {
        count --;
        System.err.println(this.currentThread().getName() + ", count = " + count);
    }

    public static void main(String[] args) {

        MyThread mt = new MyThread();
        Thread t1 = new Thread(mt, "t1");
        Thread t2 = new Thread(mt, "t2");
        Thread t3 = new Thread(mt, "t3");
        Thread t4 = new Thread(mt, "t4");
        Thread t5 = new Thread(mt, "t5");
        Thread t6 = new Thread(mt, "t6");
        Thread t7 = new Thread(mt, "t7");
        Thread t8 = new Thread(mt, "t8");
        Thread t9 = new Thread(mt, "t9");
        Thread t10 = new Thread(mt, "t10");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();
        t9.start();
        t10.start();

    }
}
