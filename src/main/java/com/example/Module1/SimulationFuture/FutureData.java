package com.example.demo.SimulationFuture;

import java.util.concurrent.CountDownLatch;

/**
 * 包装类，其实是一个空壳
 *
 * Created by Administrator on 2018/12/9 0009.
 */
public class FutureData implements Data {

    private RealData realData;

    private Boolean isReady = false;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public void setRealData(RealData realData) {
        if (isReady) {
            return;
        }
        this.realData = realData;
        this.isReady = true;
        countDownLatch.countDown();
    }

    @Override
    public String getRequest() {
        while (!isReady) {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return this.realData.getRequest();
    }
}
