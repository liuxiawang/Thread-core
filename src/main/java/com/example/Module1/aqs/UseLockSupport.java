package com.example.Module1.aqs;

import java.util.concurrent.locks.LockSupport;

/**
 * 使用LockSupport，
 * 作用是在线程之间进行阻塞与唤醒
 * park是阻塞线程
 * unpark是唤醒线程
 * 优点1 不需要配合synchronized使用，优点2 不需要考虑线程之间的执行先后顺序，只需要使用park与unpark方法
 *
 * Created by Administrator on 2018/12/23 0023.
 */
public class UseLockSupport {

    public static void main(String[] args) {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int sum = 0;
                for (int i = 0; i < 10; i++) {
                    sum += i;
                }
                try {
                    // 休眠三秒，造成unpark先执行，park后执行的现象，但是这并不影响最终结果。这正是优点2，也是最牛逼的地方
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 线程阻塞
                LockSupport.park();
                System.err.println("Sum: " + sum);
            }
        }, "t1");
        t1.start();
        // unpark唤醒指定的t1线程
        LockSupport.unpark(t1);

    }

}
