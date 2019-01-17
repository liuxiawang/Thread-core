package com.example.Module1.UseConcurrentQueue;

/**
 * Created by Administrator on 2018/11/26 0026.
 */
public class Node implements Comparable<Node> {

    private Integer id;
    private String name;

    public Node(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public int compareTo(Node node) {
        return this.id > node.id ? 1 : (this.id < node.id ? -1 : 0);
    }

    public String toString() {
        return this.id + ":" + this.name;
    }
}
