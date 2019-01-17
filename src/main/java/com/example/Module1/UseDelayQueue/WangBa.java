package com.example.Module1.UseDelayQueue;

import java.util.concurrent.DelayQueue;

/**
 * Created by Administrator on 2018/11/26 0026.
 */
public class WangBa implements Runnable {

    private static final boolean BUSINESS = true;

    private DelayQueue<WangMing> dq = new DelayQueue<>();

    public void startMachine(Integer id, String name, Long money) {
        WangMing wangMing = new WangMing(id, name, System.currentTimeMillis() + money * 1000);
        dq.offer(wangMing);
        System.err.println(id + " " + name + " 上机" + money + "秒开始！");
    }

    public void overMachine(WangMing wangMing) {
        System.err.println(wangMing.getId() + " " + wangMing.getName() + " 下机了~~！");
    }

    @Override
    public void run() {
        while (BUSINESS) {
            try {
                WangMing wm = dq.take();
                overMachine(wm);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        WangBa wb = new WangBa();
        System.err.println("网吧开始营业：");
        Thread yingye  = new Thread(wb);
        yingye.start();

        wb.startMachine(3, "王五", 14l);
        wb.startMachine(2, "李四", 4l);
        wb.startMachine(1, "张三", 10l);


    }
}
