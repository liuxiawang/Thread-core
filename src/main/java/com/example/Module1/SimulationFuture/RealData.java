package com.example.demo.SimulationFuture;

/**
 * 真实去处理业务逻辑的类
 *
 *
 * Created by Administrator on 2018/12/9 0009.
 */
public class RealData implements Data {

    private String result;

    public RealData(String queryStr) {
        System.err.println("根据查询参数：" + queryStr + "进行查询数据库操作，大概耗时5S左右");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.result = "查询出来了101条数据";
    }

    @Override
    public String getRequest() {
        return this.result;
    }
}
