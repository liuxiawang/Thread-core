package com.example.Module1.ModuleWork;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class Work1 {

    // 任务接收队列
    private ArrayBlockingQueue<Object> taskQueue = new ArrayBlockingQueue<>(10);
    // 线程池拒绝时的任务接收队列
    private ArrayBlockingQueue<Object> failTaskQueue = new ArrayBlockingQueue<>(100);

    private static final Integer ioScene = (int) (Runtime.getRuntime().availableProcessors() / (1 - 0.8));
    private static final Integer computerScene = Runtime.getRuntime().availableProcessors() * 2;

    // 任务投递到队列中，投递成功返回true，投递失败返回false
    public Boolean setTask(Object o) {
        Boolean setResult = true;
        try {
            if (!taskQueue.offer(o)) {
                setResult = false;
                System.err.println("Task Full");
            }
        } catch (Exception e) {
            setResult = false;
            System.err.println("unknown mistake");
        } finally {
            return setResult;
        }
    }

    // 获取失败的任务集合
    public List getFailTaskQueue() {
        List list = Arrays.asList(failTaskQueue.toArray());
        failTaskQueue.clear();
        return list;
    }

    // private ExecutorService executorService = Executors.newFixedThreadPool(ioScene);
    private MyThreadPool threadPoolExecutor = new MyThreadPool(
            ioScene / 2, ioScene, 90, TimeUnit.SECONDS, new ArrayBlockingQueue(50),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r);
                }
            },
            new RejectedExecutionHandler() {
                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    // 线程池装不下时执行的拒绝策略
                    System.err.println("线程池已满，" + r + "任务被拒绝。");
                    Work1 work1 = new Work1();
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
