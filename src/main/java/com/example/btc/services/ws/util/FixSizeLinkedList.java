package com.example.btc.services.ws.util;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Iterator;
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
        if (this.size() >= capacity) {
            Iterator<T> it_b=this.iterator();
            while(it_b.hasNext()){
               //this.remove(0);
               //it_b.remove();
                it_b.next();
                it_b.remove();
                break;
            }
        }
        return super.add(e);
    }
    public static void main(String[] args) {
        FixSizeLinkedList<String> list = new FixSizeLinkedList<String>(3);
        list.add("1234");
        list.add("234");
        list.add("34");
        list.add("4");
        list.add("5");
        list.add("6");
        System.out.println(JSON.toJSONString(list));
    }

}
