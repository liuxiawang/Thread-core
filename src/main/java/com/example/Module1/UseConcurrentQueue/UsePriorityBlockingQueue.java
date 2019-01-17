package com.example.Module1.UseConcurrentQueue;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * 使用PriorityBlockingQueue.
 */
public class UsePriorityBlockingQueue {

    public static void main(String[] args) throws InterruptedException {

        /**
         * PriorityBlockingQueue 是一个已优先级有顺序的队列，不遵从先进先出，已优先级为准；
         * new一个该队列时，需要传入一个自定义的对象，该对象实现Comparable接口
         * 重写compareTo方法
         */
        PriorityBlockingQueue<Node> pbq = new PriorityBlockingQueue<>();

        Node n1 = new Node(1, "node 1");
        Node n2 = new Node(2, "node 2");
        Node n3 = new Node(3, "node 3");
        Node n4 = new Node(4, "node 4");
        Node n5 = new Node(5, "node 5");

        pbq.offer(n2);
        pbq.offer(n4);
        pbq.offer(n1);
        pbq.offer(n3);
        pbq.offer(n5);

        System.err.println("1 容器中：" + pbq.toString());
        System.err.println("1 取出一个：" + pbq.take());
        System.err.println("2 容器中：" + pbq.toString());
        System.err.println("2 取出一个：" + pbq.take());
        System.err.println("3 容器中：" + pbq.toString());
        System.err.println("3 取出一个：" + pbq.take());
        System.err.println("4 容器中：" + pbq.toString());
        System.err.println("4 取出一个：" + pbq.take());
        System.err.println("5 容器中：" + pbq.toString());
        System.err.println("5 取出一个：" + pbq.take());
    }
}
