package com.example.Module1.ModuleWork;

import java.util.concurrent.*;

// 继承Callable接口，使之可以使用future模式的call方法
public class MyThreadPool extends ThreadPoolExecutor implements Callable<Boolean> {
    @Override
    public Boolean call() throws Exception {
        for (;;) {
            // 自循环判断池内活跃线程数的数量，为0时说明没有任务在池内执行，可关闭
            Thread.sleep(500);
            if (this.getActiveCount() == 0) {
                break;
            }
        }
        return true;
    }

    // 使用最多参数的自定义线程池构造方法，更灵活
    public MyThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                        BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        // 线程池任务执行之前调用的函数，可做具体的需求日志记录
        // System.err.println("任务" + r.toString() + "执行之前");
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        // 线程池任务执行之后调用的函数，可做具体的需求日志提示
        System.err.println("任务" + r.toString() + "执行之后");
    }
}
