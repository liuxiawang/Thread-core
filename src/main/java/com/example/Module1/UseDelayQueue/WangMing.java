package com.example.Module1.UseDelayQueue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/11/26 0026.
 */
public class WangMing implements Delayed {
    private Integer id;
    private String name;
    private Long endTime;
    private static final TimeUnit timeUnit = TimeUnit.SECONDS;

    public WangMing(Integer id, String name, Long endTime) {
        this.id = id;
        this.name = name;
        this.endTime = endTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    // 用来判断是否下机时间
    @Override
    public long getDelay(TimeUnit unit) {
        return endTime - System.currentTimeMillis();
    }

    @Override
    public int compareTo(Delayed delayed) {
        WangMing wangMing = (WangMing) delayed;
        return this.getDelay(timeUnit) - wangMing.getDelay(timeUnit) > 0 ? 1 :
                (this.getDelay(timeUnit) - wangMing.getDelay(timeUnit) == 0 ? 0 : -1);
    }
}
