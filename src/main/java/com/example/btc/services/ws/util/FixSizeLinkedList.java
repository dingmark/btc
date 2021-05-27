package com.example.btc.services.ws.util;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.LinkedList;

public class FixSizeLinkedList<T> extends ArrayList<T> {
    int capacity;
    public FixSizeLinkedList(int capacity)
    {
        super();
        this.capacity=capacity;
    }
    @Override
    public boolean add(T e)
    {
        if (size() + 1 > capacity) {
            super.remove(0);
        }
        return super.add(e);
    }
    public static void main(String[] args) {
        FixSizeLinkedList<String> list = new FixSizeLinkedList<String>(3);
        list.add("1234");
        list.add("234");
        list.add("34");
        list.add("4");
        System.out.println(JSON.toJSONString(list));
    }

}
