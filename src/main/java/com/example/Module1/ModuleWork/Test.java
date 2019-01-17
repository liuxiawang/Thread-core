package com.example.Module1.ModuleWork;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class Test {

    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Work work = Work.getInstance();
                for (int i = 0; i < 10; i++) {
                    Task task = new Task((long) i);
                    work.setTask(task);
                }
                System.err.println("循环投递任务完成");
                if (work.isFinish()) {
                    work.setClose();
                }

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Work work1 = Work.getInstance();
                for (int i = 0; i < 10; i++) {
                    Task task = new Task((long) i);
                    work1.setTask(task);
                }
                System.err.println("第二轮循环投递任务完成");
                if (work1.isFinish()) {
                    work1.setClose();
                }
            }
        }, "thread");

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Work work = Work.getInstance();
                for (int i = 0; i < 10; i++) {
                    Task task = new Task((long) i);
                    work.setTask(task);
                }
                System.err.println("第三轮循环投递任务完成");
                if (work.isFinish()) {
                    work.setClose();
                }
            }
        }, "thread1");
        //thread.start();
        // thread1.start();

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String temp = iterator.next();
            if ("3".equals(temp)) {
                list.remove(temp);
            }
        }

        Iterator<String> iterator1 = list.iterator();
        while (iterator1.hasNext()) {
            String item = iterator1.next();
            if ("3".equals(item)) {
                iterator1.remove();
            }
        }
        System.err.println(list.toString());
        ArrayBlockingQueue a = new ArrayBlockingQueue(5);
        LinkedBlockingQueue b = new LinkedBlockingQueue();
        ReentrantLock c = new ReentrantLock();
    }
}
