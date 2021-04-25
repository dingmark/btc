package com.example.btc.controller;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2021-03-15.
 */
public class User  {
    private String uname;
    private String upwd;
    Timestamp start;
    Timestamp end;
    int modifynum;
    String telno;
    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public int getModifynum() {
        return modifynum;
    }

    public void setModifynum(int modifynum) {
        this.modifynum = modifynum;
    }

    public String getTelno() {
        return telno;
    }

    public void setTelno(String telno) {
        this.telno = telno;
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
