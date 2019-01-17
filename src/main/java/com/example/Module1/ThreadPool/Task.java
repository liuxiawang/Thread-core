package com.example.demo.ThreadPool;

/**
 * Created by Administrator on 2018/12/12 0012.
 */
public class Task implements Runnable {

    public Task(int taskId) {
        this.taskId = taskId;
    }

    private int taskId;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void run() {
        System.err.println("任务" + this.taskId + "被执行");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return "任务ID为" + this.taskId;
    }
}
