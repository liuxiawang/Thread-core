package com.example.Module1.juc;

import java.util.concurrent.*;

/**
 * 使用future模式，典型的设计模式之一。使用空间换时间的策略
 *
 * future模式，可以将一段复杂的业务逻辑代码（假如有三步，每步之间相对独立，分别耗时3s+3s+2s），拆分成三步
 * 分别将其中的两步分装成一个对象，该对象实现Callable接口，重写call方法，
 * 把拆分的业务逻辑写在call方法中，在new futureTask时会去执行该方法，
 * 拆分后我们可以使用futureTask并开线程去异步执行，
 * 这样总的耗时从3s+3s+2s的8s变成了三个线程中耗时最好的那个时间，在这里就是缩短成了3S.
 *
 * Created by Administrator on 2018/12/5 0005.
 */
public class UseFuture implements Callable<String> {

    private String param;

    public UseFuture(String param) {
        this.param = param;
    }

    @Override
    public String call() throws Exception {
        // 模拟执行其他操作的耗时
        Thread.sleep(3000);
        return this.param + "处理完成!";
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        String paramStr1 = "Query1";
        String paramStr2 = "Query2";

        FutureTask<String> futureTask1 = new FutureTask<>(new UseFuture(paramStr1));
        FutureTask<String> futureTask2 = new FutureTask<>(new UseFuture(paramStr2));

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(futureTask1);
        executorService.submit(futureTask2);

        // 模拟主线程继续执行其他业务逻辑的耗时
        System.err.println("主线程继续处理其他业务...");
        Thread.sleep(2000);
        System.err.println("主线程处理完其他业务，等待其他线程处理...");

        // 有两种get方法，一种是无限制的一直等待的，直到你处理完成返回结果
        // 一种是可以设置超时时间，时间超过则不再等待
        String result1 = futureTask1.get();
        String result2 = futureTask2.get();

        System.err.println(result1);
        System.err.println(result2);

        executorService.shutdown();
    }
}
