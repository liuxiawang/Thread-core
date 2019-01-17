package com.example.Module1.ThreadSafely;

import java.util.ArrayList;
import java.util.List;

/**
 * ThreadLocal技巧性应用.
 *
 * 因为ThreadLocal的特性：为每个独立的线程创建一个单独的存储空间，避免多线程同时操作相同的存储空间而造成线程不安全的行为。
 * 一定程度使用ThreadLocal可以避免使用synchronize关键字为方法加锁，而造成的性能下降问题。
 * 是以空间换安全换时间的做法。
 * 可以在多线程访问时，将对单个线程独立的所需的数据存入ThreadLocal中，这样就可以减少synchronize使用，提高性能。
 * 但是这里牺牲了内存空间，是以空间换取时间换去线程安全的做法。
 *
 * Created by Administrator on 2018/11/27 0027.
 */
public class MessageHolder {

    private List<String> message = new ArrayList<>();

    private ThreadLocal<MessageHolder> tl = new ThreadLocal() {
        // 此处重写ThreadLocal的initialValue方法，可以避免在newThreadLocal对象时，如果不add具体对象会返回null的问题
        @Override
        protected Object initialValue() {
            return new MessageHolder();
        }
    };

    public void add(String message) {
        tl.get().message.add(message);
    }

    public List<String> clear() {
        List<String> result = tl.get().message;
        tl.remove();
        System.err.println("当前message容器size： " + tl.get().message.size());
        return result;
    }

    public static void main(String[] args) {
        MessageHolder mh = new MessageHolder();

        mh.add("张三");
        mh.add("李四");
        mh.add("王五");

        System.err.println(mh.clear().toString());

    }



}
