package com.example.demo.SimulationMasterWorker;

/**
 * 实际要处理的任务
 *
 * Created by Administrator on 2018/12/11 0011.
 */
public class Task {

    private Integer id;

    private Integer count;

    public Task(Integer id, Integer count) {
        this.id = id;
        this.count = count;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
