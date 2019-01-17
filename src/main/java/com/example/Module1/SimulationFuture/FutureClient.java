package com.example.Module1.SimulationFuture;

/**
 * 请求new的对象
 *
 * Created by Administrator on 2018/12/9 0009.
 */
public class FutureClient {

    // 主线程创建了一个对象之后，调用request方法可以得到一个包装类，这个包装类里面全是空的。为了能最快的返回。
    public Data request(String queryStr) {

        FutureData futureData = new FutureData();

        new Thread(new Runnable() {
            @Override
            public void run() {
                RealData realData = new RealData(queryStr);
                futureData.setRealData(realData);
            }
        }).start();

        return futureData;
    }
}
