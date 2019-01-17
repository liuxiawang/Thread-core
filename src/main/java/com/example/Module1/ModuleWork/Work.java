package com.example.Module1.ModuleWork;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class Work {

    // 使用future模式，判断线程池内活跃线程数是否为0，为0时说明任务执行完成
    private static volatile Boolean close = false; // 使用volatile关键字修饰，用于自定义线程池关闭标记位
    public Boolean isFinish() {
        Boolean isFinish = false;
        try {
            FutureTask<Boolean> futureTask = new FutureTask<>(this.threadPoolExecutor);
            ExecutorService executorService = Executors.newFixedThreadPool(1);
            executorService.submit(futureTask);
            isFinish = futureTask.get(); // future模式获取池内活跃线程数是否为0
            executorService.shutdown(); // 使用完线程池后关闭
        } catch (Exception e) {
            e.getMessage();
        }
        return isFinish;
    }
    public void setClose() {
        this.close = true; //改变是否关闭自定义线程池的标记位
    }

    // 引入锁，避免多线程同时投递任务
    private static ReentrantLock reentrantLock = new ReentrantLock();

    // 懒汉式单例模式在创建Work时，开始自循环投递、执行任务
    private static Work work = null;
    public static Work getInstance() {
        // 使用重入锁上锁，避免多线程调用getInstance方法产生不理想行为
        reentrantLock.lock();
        try {
            if (work == null) {
                work = new Work();
                close = false;
            }
        } catch (Exception e) {
            System.err.println("Unknown error");
        } finally {
            // 释放重入锁并返回实例对象
            reentrantLock.unlock();
            return work;
        }
    }
    private void setWork() {
        this.work = null;
        this.close = false;
    }
    private Work() {
        // 构造函数开新线程，执行从任务队列中获取任务并执行
        // 自循环关闭线程池标记位，适当时关闭自定义线程池
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Work work = Work.getInstance();
                for (;;) {
                    if (work.taskQueue.size() > 0) {
                        work.threadPoolExecutor.execute((Runnable) work.taskQueue.poll());
                    }
                    if (close) {
                        System.err.println("停止自循环");
                        work.threadPoolExecutor.shutdown();
                        work.setWork();
                        break;
                    }
                }
            }
        }, "t1");
        t1.start();
    }

    // 任务接收队列
    private volatile ArrayBlockingQueue<Object> taskQueue = new ArrayBlockingQueue<>(10);
    // 线程池拒绝时的任务接收队列
    private volatile ArrayBlockingQueue<Object> failTaskQueue = new ArrayBlockingQueue<>(100);

    //io场景自定义线程池最大线程数
    private static final Integer ioScene = (int) (Runtime.getRuntime().availableProcessors() / (1 - 0.8));
    //计算场景自定义线程池最大线程数
    private static final Integer computerScene = Runtime.getRuntime().availableProcessors() * 2 + 1;

    // 任务投递到队列中，投递成功返回true，投递失败返回false
    public Boolean setTask(Object o) {
        // reentrantLock.lock(); //队列已经继承AQS类，不用担心多线程offer任务到任务队列
        // 是否offer成功到任务队列的标志位
        Boolean setResult = true;
        try {
            if (!taskQueue.offer(o)) { // 使用offer方法将任务push到任务队列中，返回true时push成功，返回false时失败
                setResult = false;
                System.err.println("Task Full");
            }
        } catch (Exception e) {
            setResult = false;
            System.err.println("unknown mistake");
        } finally {
            // reentrantLock.unlock();
        }
        return setResult;
    }

    // 获取失败的任务集合
    public List getFailTaskQueue() {
        List list = Arrays.asList(failTaskQueue.toArray());
        failTaskQueue.clear();
        return list;
    }

    private volatile MyThreadPool threadPoolExecutor = new MyThreadPool(
            ioScene / 2, // 核心线程数为io场景最大线程数的一半，数量可根据场景做调整
            ioScene,
            90,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue(50), // 有界阻塞队列存放线程池未执行的任务
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r);
                }
            },
            new RejectedExecutionHandler() { // 自定义线程池的拒绝策略
                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    // 线程池装不下时执行的拒绝策略
                    System.err.println("线程池已满，" + r + "任务被拒绝。");
                    Work work1 = new Work();
                    if (work1.setTask(r)) {
                        System.err.println(r + "任务被放入任务队列尾部");
                    } else if (failTaskQueue.offer(r)) {
                        System.err.println(r + "任务被丢入失败任务队列");
                    } else {
                        System.err.println(r + "任务被丢弃");
                    }
                }
            }
    );
}
