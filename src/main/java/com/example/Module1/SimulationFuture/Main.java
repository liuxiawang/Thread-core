package com.example.Module1.SimulationFuture;

/**
 * 模拟请求入口
 *
 * Created by Administrator on 2018/12/9 0009.
 */
public class Main {

    public static void main(String[] args) {

        String queryStr = "一堆的请求参数";

        System.err.println("主线程拆分查询业务给模拟的future模式");
        FutureClient fc = new FutureClient();
        Data data = fc.request(queryStr);

        System.err.println("主线程继续处理其他不怎么相关的业务逻辑");
        String result = data.getRequest();
        System.err.println("拿到模拟的future返回的结果：" + result);

    }

}
