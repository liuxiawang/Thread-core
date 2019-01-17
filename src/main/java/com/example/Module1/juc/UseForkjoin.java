package com.example.demo.juc;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * ForkJoin将一个大任务根据你自己给定的算法拆分（fork）成无数个小任务，再把小任务执行的接口合并（join）起来返回
 * 可以继承两个类：一个是有返回结果的RecursiveTask，一个是不需要返回结果的RecursiveAction
 * 继承之后重写compute方法，之后用特有的pool提交forkjoin类，拿到返回结果。
 *
 * Created by Administrator on 2018/12/10 0010.
 */
public class UseForkjoin extends RecursiveTask<Integer> {

    // 定义拆分的阈值
    private static final int THRESHOLD = 2;

    private int start;

    private int end;

    public UseForkjoin(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        // 判断是否需要计算，小值减去大值小于或者等于阈值的话就计算。
        Boolean isCompute = (end - start) <= THRESHOLD;
        if (isCompute) {
            for (int i = start; i < end; i++) {
                sum += i;
            }
        } else {
            // 自定义拆分方式，使用二分法的形式
            int medium = (start + end) / 2;
            // 使用递归的方式重复创建forkjoin对象
            UseForkjoin left = new UseForkjoin(start, medium);
            UseForkjoin right = new UseForkjoin(medium + 1, end);

            // 调用fork方法实行拆分
            left.fork();
            right.fork();

            // 调用join方法，将拆分的结果合并为一个结果
            int leftSum = left.join();
            int rightSum = right.join();

            // 将两边结果加起来作为总的返回值返回
            sum = leftSum + rightSum;
        }
        return sum;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 创建一个ForkJoin特定了pool
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        UseForkjoin useForkjoin = new UseForkjoin(1, 10000);

        // 使用pool提交需要拆分的任务forkjoin，submit的返回值是一个ForkJoinTask对象，其实也是设计模式之中的Future相关的对象
        Future<Integer> aa = forkJoinPool.submit(useForkjoin);
        System.err.println("处理结果为： " + aa.get());
    }
}
