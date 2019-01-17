package com.example.Module1.SimulationMasterWorker;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

/**
 * Master类，用于任务的收集、worker的调度、结果的收集
 *
 * Created by Administrator on 2018/12/11 0011.
 */
public class Master {

    // 用于接收收集任务，要使用高性能无阻塞的队列
    private ConcurrentLinkedQueue<Task> taskQueue = new ConcurrentLinkedQueue<>();

    // 用于存放worker，使用普通的haspMap即可
    private HashMap<String, Thread> workers = new HashMap<>();

    // 用于接收多worker执行之后的处理结果，收集多线程的结果所以要高性能的ConcurrentHashMap
    private ConcurrentHashMap<String, Object> resultMap = new ConcurrentHashMap<>();

    // 构造函数，在创建Master时就创建好相应数量的worker数
    public Master(CountDownLatch countDownLatch, Worker worker, Integer workerNumber) {
        worker.setCountDownLatch(countDownLatch);

        // 在worker中需要能从任务队列中拿到任务
        worker.setTaskQueue(this.taskQueue);

        // 在worker中需要把处理结果存到结果集中
        worker.setResultMap(this.resultMap);

        // 生成相应数量的worker存到存放worker的hashmap中
        for (int i = 0; i < workerNumber; i++) {
            workers.put(Integer.toString(i), new Thread(worker));
        }
    }

    // 将任务存放到任务队列中
    public void submit(Task task) {
        this.taskQueue.add(task);
    }

    // 将worker真的运行起来
    public void execute() {
        for (HashMap.Entry<String, Thread> he : workers.entrySet()) {
            he.getValue().start();
        }
    }

    // 拿到处理结果
    public Integer getResult() {
        Integer sum = 0;
        for (ConcurrentHashMap.Entry<String, Object> ce : resultMap.entrySet()) {
            sum += (Integer) ce.getValue();
        }
        return sum;
    }

    public boolean isFinish() {
        for (HashMap.Entry<String, Thread> he : workers.entrySet()) {
            if (he.getValue().getState() != Thread.State.TERMINATED) {
                return false;
            }
        }
        return true;
    }
}
