package com.example.demo.SimulationMasterWorker;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 测试类
 *
 * Created by Administrator on 2018/12/11 0011.
 */
public class Main {

    private static final CountDownLatch countDownLatch = new CountDownLatch(Runtime.getRuntime().availableProcessors());

    public static void main(String[] args) throws InterruptedException {
        System.err.println("当前机器核心线程数： " + Runtime.getRuntime().availableProcessors());
        Master master = new Master(countDownLatch, new Worker(), Runtime.getRuntime().availableProcessors());

        Random r = new Random();
        for (int i = 0; i < 100; i++) {
            master.submit(new Task(i, r.nextInt(1000)));
        }

        master.execute();
        Long startTime = System.currentTimeMillis();

        countDownLatch.await();
        Long endTime = System.currentTimeMillis();
        System.err.println("处理结果为：" + master.getResult() + "；总耗时为：" + (endTime - startTime));

//        while (true) {
//            if (master.isFinish()) {
//                Long endTime = System.currentTimeMillis();
//                System.err.println("处理结果为：" + master.getResult() + "；总耗时为：" + (endTime - startTime));
//                break;
//            }
//        }

    }
}
