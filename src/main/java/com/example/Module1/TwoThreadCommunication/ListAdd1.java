package com.example.Module1.TwoThreadCommunication;

import java.util.ArrayList;
import java.util.List;

/**
 * A线程添加是个元素到List中，当添加到第五个之后，B线程要执行一段业务逻辑
 * 涉及到两个线程之间通信唤醒
 * <p>
 * 使用volatile关键字，是被该关键字修饰的变量在多个线程之间可见。
 * 即：A线程改变变量之后B线程使用该变量时可以得到最新最实时的变量里的内容。
 * <p>
 * Created by Administrator on 2018/11/28 0028.
 */
public class ListAdd1 {

    private volatile static List<String> list = new ArrayList<>();

    public void add() {
        list.add("ABC");
    }

    public int size() {
        return list.size();
    }

    public static void main(String[] args) throws InterruptedException {

        ListAdd1 listAdd = new ListAdd1();

        Thread A = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    listAdd.add();
                    System.err.println(Thread.currentThread().getName() + "线程往List中添加了一个元素");
                    try {
                        Thread.sleep(500l);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "A");

        Thread B = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (listAdd.size() == 5) {
                        System.err.println(Thread.currentThread().getName() + "启动~");
                        throw new RuntimeException("抛出RuntimeException了！！");
                    }
                }
            }
        }, "B");

        B.start();
        Thread.sleep(500);
        A.start();

    }
}
