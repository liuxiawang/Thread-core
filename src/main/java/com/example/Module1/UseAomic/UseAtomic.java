package com.example.Module1.UseAomic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.*;

/**
 * 使用Atomic系列类
 *
 * Created by Administrator on 2018/12/1 0001.
 */
public class UseAtomic {

    private static AtomicInteger count = new AtomicInteger(0); //int count = 0;

    // 几个主要的Atomic类
    // AtomicInteger
    // AtomicBoolean
    // AtomicLong
    // AtomicIntegerArray
    // AtomicLongArray
    // AtomicReference 稍微与上述几个Atomic类使用有点不同，因为这个是用于原子性的对象
    /**
     * Atomic的类是具有多线程下的可见性，与线程安全的。
     * 但是只是保证单个JVM环境下的线程可见性，与安全。
     * 在实际微服务架构中，存在多台服务器架构成一个集群，如果多个相同的服务副本在不同的服务器中运行，这样是没办法简单使用Atomic类的。
     * 因为不同的服务器（JVM）环境中，多个环境内的Atomic类是无法相互通讯的。
     * 此时订单计数的话考虑zookeeper
     */

    private int add() {
        // count = count + 10;

        /**
         * 每一个Atomic的操作都是原子性的，都是线程安全的。
         * 但是假如本方法中，一次性加10（count.addAndGet(10)），能保证程序的原子性，线程也安全
         * 如果分开加，累计加10（count.addAndGet(3); count.addAndGet(1); count.addAndGet(4);count.addAndGet(2)）
         * 这样就无法保证不会在多线程情况下，多个线程同时在调用本方法（add()）。此时程序是线程不安全的；
         * 但是每一个count.addAndGet(2)的Atomic操作肯定是满足线程安全的，也是原子性的。
         */
        // count.addAndGet(3);
        // count.addAndGet(1);
        // count.addAndGet(4);
        // count.addAndGet(2);
        return count.addAndGet(10);
    }

    public static void main(String[] args) {
        UseAtomic ua = new UseAtomic();

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            threads.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    System.err.println("count: " + ua.add());
                }
            }));
        }

        for (Thread thread: threads) {
            thread.start();
        }
    }

}
