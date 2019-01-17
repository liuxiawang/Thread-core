package com.example.Module1.ThreadSafely;

/**
 * ThreadLocal，为每个独立的线程开辟独立的存储空间
 *
 * Created by Administrator on 2018/11/27 0027.
 */
public class UseThreadLocal {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public void setThreadLocal(String value) {
        threadLocal.set(value);
    }

    public String getThreadLocal() {
        return threadLocal.get();
    }

    public static void main(String[] args) throws InterruptedException {

        UseThreadLocal utl = new UseThreadLocal();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                utl.setThreadLocal("张三");
                System.err.println("t1: " + utl.getThreadLocal());
            }
        }, "t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                utl.setThreadLocal("李四");
                System.err.println("t2: " + utl.getThreadLocal());
            }
        }, "t2");


        Thread.sleep(1000);
        t1.start();
        t2.start();
        utl.setThreadLocal("王五");
        System.err.println("main: " + utl.getThreadLocal());

    }


}
