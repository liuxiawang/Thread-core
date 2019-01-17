package com.example.Module1.MyQueue;

/**
 * 模拟阻塞队列测试
 *
 * Created by xiawang.liu on 2018/11/20 0020.
 */
public class MyQueueTest {

    public static void main(String[] args) throws Exception {
        MyQueue mq = new MyQueue(5);

        mq.put("a");
        mq.put("b");
        mq.put("c");
        mq.put("d");
        mq.put("e");

        System.err.println("队列长度为：" + mq.size());

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                mq.put("f");
                mq.put("g");
            }
        }, "t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    mq.take();
                    Thread.sleep(1000);
                    mq.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t2");
        t1.start();
        t2.start();
        Thread.sleep(5000);
        System.err.println(mq.getMyQueueList().toString());
    }
}
