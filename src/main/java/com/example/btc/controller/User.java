package com.example.btc.controller;

/**
 * Created by Administrator on 2021-03-15.
 */
public class User  {
    private String uname;
    private String upwd;
    String start;
    String end;
    public String getStart() {
        return start;
    }
    public void setStart(String start) {
        this.start = start;
    }
    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }


    public User(String uname, String upwd) {
        super();
        this.uname = uname;
        this.upwd = upwd;
    }
    public String getUname() {
        return uname;
    }
    public void setUname(String uname) {
        this.uname = uname;
    }
    public String getUpwd() {
        return upwd;
    }
    public void setUpwd(String upwd) {
        this.upwd = upwd;
    }

}
