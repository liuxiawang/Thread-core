package com.example.demo.UseVolatile;

/**
 * 使用volatile关键字，使得两个线程之间的参数可见.
 * 其中一个线程修改了被volatile关键字修改的变量时，另一个线程能实时的知道被修改了
 *
 * Created by Administrator on 2018/12/1 0001.
 */
public class UseVolatile extends Thread {

    private volatile boolean isRunning = true;

    private void setRunning(Boolean isRunning) {
        this.isRunning = isRunning;
    }

    @Override
    public void run() {
        System.err.println("开启线程...");
        while (isRunning) {
            //  ...
        }
        System.err.println("线程停止！");
    }

    public static void main(String[] args) throws InterruptedException {
        UseVolatile uv = new UseVolatile();
        uv.start();

        Thread.sleep(2000);
        uv.setRunning(false);
        System.err.println("isRunning 被设置为了false");
    }
}
