package com.example.demo.ThreadPool;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.util.concurrent.*;

/**
 * 使用ThreadPoolExecutor自定义线程池.
 *
 * Created by Administrator on 2018/12/13 0013.
 */
public class UseThreadPoolExecutor {

    public static void main(String[] args) {

        /**
         * 当线程池使用无界队列时
         * 1. 任务提交时，池中工作线程数不满足核心线程数时，直接调用核心线程数工作
         * 2. 当没有核心线程空闲时，将提交的任务放入无界队列中，
         *      如果任务的处理速度小于任务的产生速度，线程池会一直将新的任务放入无界队列，
         *      不会存在拒绝行为，队列中一直堆积任务直到机器资源耗尽
         */

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                1, // corePoolSize核心线程数，在线程池一创建时创建几个线程
                3, // maximumPoolSize线程池中线程最大数量， // 当使用无界阻塞队列时，该参数不起作用
                30,   // 当线程池中的线程空闲多长时间后被回收，配合下一个时间单位参数确实时长
                TimeUnit.SECONDS,  // 时间单位，与上一个参数确定线程空闲多长时间被回收

                // 使用有界阻塞队列与无界阻塞队列时，线程池的工作模式是不一样的
                // new ArrayBlockingQueue(2), // 有界阻塞队列ArrayBlockingQueue，数字代表队列长度。
                new LinkedBlockingQueue<>(), // 无界阻塞队列LinkedBlockingQueue

                //线程池工程，可以自定义新建线程的属性
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread th = new Thread(r, "order-thread");
                        if (th.getPriority() != Thread.NORM_PRIORITY) {
                            th.setPriority(Thread.NORM_PRIORITY);
                        }
                        if (th.isDaemon()) {
                            th.setDaemon(false);
                        }
                        return th;
                    }
                },

                /**
                 * JDK提供的拒绝策略有4种：但是都不太符合我们的实际应用场景
                 * 1. AbortPolicy ：直接抛出异常，会阻止程序正常工作
                 * 2. CallerRunsPolicy ：直接在 execute 方法的调用线程中运行被拒绝的任务；如果执行程序已关闭，则会丢弃该任务
                 * 3. DiscardOldestPolicy ：丢弃最老的一个任务，执行当前任务
                 * 4. DiscardPolicy ：丢弃无法拒绝的任务，不予处理
                 */
                // 当线程池满后的拒绝策略，这里是自定义的拒绝策略 // 使用无界队列时，不存在拒绝行为，会一直将新的任务存入无界队列中，直到系统资源耗尽
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        System.err.println("该任务被拒绝: " + r.toString());
                    }
                }
        );

        Task task1 = new Task(1);
        Task task2 = new Task(2);
        Task task3 = new Task(3);
        Task task4 = new Task(4);
        Task task5 = new Task(5);
        Task task6 = new Task(6);

        // 任务提交方式:
        // execute()是不返回数据的，当你的任务不需要返回值的时候可以使用
        // submit()带有Future模式的返回值，可以使用get方法拿到返回的数据


        /**
         * 当线程池使用有界队列时
         * 1. 任务提交时，池中工作线程数不满足核心线程数时，直接调用核心线程数工作
         * 2. 当没有核心线程空闲时，将提交的任务放入有界队列中
         * 3. 当有界队列满了，并且池中线程数小于最大线程数时，创建新的线程
         * 4. 当池中线程数等于最大线程数时，执行拒绝策略
         */
        // 1. 任务提交时，池中工作线程数不满足核心线程数时，直接调用核心线程数工作
        threadPoolExecutor.execute(task1); // 刚新建池，里面没有任务在执行，这个是第一个任务

        // 2. 当没有核心线程空闲时，将提交的任务放入有界队列中
        threadPoolExecutor.execute(task2); // 工作线程已经有一个线程在执行task1了，已经没有核心线程了，所以将task2、task3存入有界队列
        threadPoolExecutor.execute(task3);

        // 3. 当有界队列满了，并且池中线程数小于最大线程数时，创建新的线程
        threadPoolExecutor.execute(task4); // 没有空闲的核心线程了，有界队列也满了，但池中线程数没有达到最大线程数，所以新建一个线程去执行task4、task5
        threadPoolExecutor.execute(task5);

        // 4. 当池中线程数等于最大线程数时，执行拒绝策略
        threadPoolExecutor.execute(task6); // 核心线程都在工作，有界队列也满了，池中线程数也达到了最大线程数，为了稳定性只能拒绝任务了


        threadPoolExecutor.shutdown();
    }
}
