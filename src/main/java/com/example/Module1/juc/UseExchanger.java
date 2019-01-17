package com.example.demo.juc;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用Exchanger，Exchanger是在两个线程之间交换数据用的。只能是两个线程之间。
 * 通过同一个Exchanger对象来识别两个线程。
 *
 * Created by Administrator on 2018/12/10 0010.
 */
public class UseExchanger {

    private static Exchanger<String> exchanger = new Exchanger<>();

    private static ExecutorService executorService = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String A = "A小孩的银行流水";
                try {
                    String B = exchanger.exchange(A);
                    System.err.println("线程A： " + B);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String B = "B小孩的银行流水";
                try {
                    String A = exchanger.exchange(B);
                    System.err.println("B线程： " + A);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executorService.shutdown();
    }

}
