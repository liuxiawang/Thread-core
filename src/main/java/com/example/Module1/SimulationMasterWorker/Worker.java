package com.example.Module1.SimulationMasterWorker;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

/**
 * worker类，多线程的去执行任务
 *
 * Created by Administrator on 2018/12/11 0011.
 */
public class Worker implements Runnable{

    private CountDownLatch countDownLatch;

    private ConcurrentLinkedQueue<Task> taskQueue;

    private ConcurrentHashMap<String, Object> resultMap;

    public void setTaskQueue(ConcurrentLinkedQueue<Task> taskQueue) {
        this.taskQueue = taskQueue;
    }

    public void setResultMap(ConcurrentHashMap<String, Object> resultMap) {
        this.resultMap = resultMap;
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    private Random r = new Random();

    @Override
    public void run() {
        while (true) {
            Task task = this.taskQueue.poll();
            if (task == null) break;
            try {
                // Thread.sleep(200 * r.nextInt(10));
                Thread.sleep(100);
                this.resultMap.put(Integer.toString(task.getId()), task.getCount());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        countDownLatch.countDown();
    }

}
