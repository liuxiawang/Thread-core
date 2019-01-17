package com.example.Module1.MyQueue;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 模拟阻塞队列
 *
 * Created by xiawang.liu on 2018/11/20 0020.
 */
public class MyQueue {

    // 队列的容器
    private final ArrayList<Object> myQueue = new ArrayList<>();

    // 队列里面存放的Obejct的计数器
    private final AtomicInteger count = new AtomicInteger();

    // 队列存放Object的最小个数
    private final int minSize = 0;

    // 队列存放的Obejct的最大个数
    private final int maxSize;

    private final Object lock = new Object();

    // 通过构造函数的方式，将需要创建队列的长队传递进来
    public MyQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    // 将Object存放进队列中
    public void put(Object object) {
        synchronized (lock) {
            // 达到队列容量上限时，一直等待到有线程拿走队列中的元素
            while (count.get() == this.maxSize) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            myQueue.add(object);
            count.getAndIncrement(); //相当于 i++
            System.err.println("元素" + object + "已存入队列中！！！");
            lock.notify();
        }
    }

    // 从队列中取出第一个Object（先进先出）
    public Object take() {
        synchronized (lock) {
            Object temp;
            while (count.get() == minSize) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            temp = myQueue.remove(0); //移除最先进入容器的元素并返回这个元素赋值给temp
            count.getAndDecrement(); //相当于 i--
            System.err.println("元素" + temp + "已从队列中取出。");
            lock.notify();
            return temp;
        }
    }

    // 获取当前队列中存在几个对象的个数
    public int size() {
        return count.get();
    }

    // 返回对象的容器List
    public ArrayList<Object> getMyQueueList() {
        return myQueue;
    }

}
