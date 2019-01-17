package com.example.demo.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 使用Executors，线程工厂类
 *
 * Created by Administrator on 2018/12/12 0012.
 */
public class UseExecutors {

    public static void main(String[] args) {

        // 创建一个有固定数量的线程池，传入的数字为线程池中线程数量的上线
        ExecutorService executorService1 = Executors.newFixedThreadPool(5);

        // 创建一个只有一个线程的线程池，如果这个线程异常了，那么线程池会自主创建一个线程替换抛异常的线程
        ExecutorService executorService2 = Executors.newSingleThreadExecutor();

        // 创建一个不限制数量的线程池，只要有任务来，并且已存在的线程都在执行就新建一个线程去执行这个任务,超过60S空闲线程就会被回收
        ExecutorService executorService3 = Executors.newCachedThreadPool();

        // 创建一个带有定时机制的固定线程数量的线程池
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        // Task任务在初始化5s之后第一次执行，并在此之后Task完成之后开始计时，每隔2s被调用执行，单位TimeUnit.SECONDS
        scheduledExecutorService.scheduleWithFixedDelay(new Task(1), 5, 2, TimeUnit.SECONDS);

    }
}
