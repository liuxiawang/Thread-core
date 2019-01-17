package com.example.Module1.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 使用Semaphore，信号量
 * 作用是限流
 *
 * Created by Administrator on 2018/12/12 0012.
 */
public class UseSemaphore {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(20);

        // new Semaphore时传入的数字，表示限流的阈值，最多只能有5个许可
        Semaphore semaphore = new Semaphore(5);

        for (int index = 1; index <= 20; index++) {
            final int i = index;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        // 获取许可，当获取不到许可时，阻塞
                        semaphore.acquire();
                        System.err.println(i + "获得许可，开始执行操作....");
                        Thread.sleep((long) (Math.random() * 10000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        // 释放许可
                        semaphore.release();
                    }
                }
            };
            executorService.submit(runnable);
        }

        // hasQueuedThreads() 是判断有没有线程在等待这个许可
        Thread.sleep(3000);
        System.err.println("-----还剩几个线程正在等待许可？ " + semaphore.getQueueLength());
        Thread.sleep(3000);
        System.err.println("-----还剩几个线程正在等待许可？ " + semaphore.getQueueLength());
        Thread.sleep(3000);
        System.err.println("-----还剩几个线程正在等待许可？ " + semaphore.getQueueLength());
        Thread.sleep(3000);
        System.err.println("-----还剩几个线程正在等待许可？ " + semaphore.getQueueLength());
        executorService.shutdown();
    }
}
