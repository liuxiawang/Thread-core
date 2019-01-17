package com.example.demo.juc;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用CyclicBarrier.
 *
 * 模拟小场景，有三个运动员比赛，每个运动员入场后要随机准备0-10S的时间，等三个运动员都准备好了，马上开始比赛。
 *
 * CyclicBarrier可以理解为是一个栅栏，new CyclicBarrier的时候要传递一个数量值，这个数量值表示栅栏有几个位置，
 * 当这个几个位置都await之后，程序可以开始往下执行
 *
 *
 * 与CountDownLatch的区别：
 * CountDownLatch是一个线程在阻塞，多个线程去唤醒阻塞的线程
 * CyclicBarrier是可以多个线程阻塞，等符合条件之后多个线程同时唤醒执行
 *
 * Created by Administrator on 2018/12/5 0005.
 */
public class UseCyclicBarrier {

    static class Runner implements Runnable {
        private String name;
        private CyclicBarrier cyclicBarrier;

        public Runner(String name, CyclicBarrier cyclicBarrier) {
            this.name = name;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            System.err.println("运动员" + this.name + "入场，开始准备~");
            try {
                Thread.sleep(1000 * new Random().nextInt(10));

                System.err.println("运动员" + this.name + "准备完成，可以开始比赛~");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.err.println("运动员" + this.name + "，GO!GO!GO!~");
        }
    }

    public static void main(String[] args) {
        // new CyclicBarrier(3)中的数字表示要几个达到条件时才继续执行
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

        // 创建一个数量为3的线程池，在这个场景中，线程池的大小与CyclicBarrier的大小应该一致
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(new Runner("张三", cyclicBarrier));
        executorService.submit(new Runner("李四", cyclicBarrier));
        executorService.submit(new Runner("王五", cyclicBarrier));

        // 最后把线程池关闭
        executorService.shutdown();
    }

}
