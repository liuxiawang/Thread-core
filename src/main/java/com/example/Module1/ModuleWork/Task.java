package com.example.Module1.ModuleWork;

public class Task implements Runnable {

    public Task(Long number) {
        this.number = number;
    }

    private Long number;

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    @Override
    public void run() {
        // System.err.println("任务运行" + this.number + "秒~");
        try {
            Thread.sleep(number * 100);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Number: " + this.number;
    }
}
