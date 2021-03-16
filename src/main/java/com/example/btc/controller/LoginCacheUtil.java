package com.example.btc.controller;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2021-03-15.
 */
public class LoginCacheUtil {
    public static Map<String, User> loginUser = new HashMap<>();
    public  static  Map<String,HttpSession> loginSession=new HashMap<>();
}
