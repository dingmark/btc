package com.example.btc.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.btc.services.baseDaoService.UrlParaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2021-03-15.
 */
@Controller
public class OnLogin {
    @Autowired
    UrlParaService urlParaService;
    @RequestMapping("/login")
    public String getLogin(@RequestParam(required = false, defaultValue = "")String target,
                           HttpServletRequest request,HttpSession session, @CookieValue(required = false, value = "TOKEN")Cookie cookie)
    {
        String username=request.getParameter("uname");
        String pwd=request.getParameter("upwd");
        if(cookie != null) {
            String value = cookie.getValue();
            User user = LoginCacheUtil.loginUser.get(value);
            if(user != null) {
                return "redirect:" + target;
            }
        }
        //验证账号信息

        // 用于登录成功后重定向地址
        target="NewSocket.html";
        session.setAttribute("target", target);
        return  "redirect:/";
    }

    @PostMapping("/login")
    @ResponseBody
    public String doLogin(User user, HttpSession session, HttpServletResponse response
    ) throws IOException{
        Map<String,Object> map=new HashMap<>();
        // 校验用户名密码

        //if(user.getUname().equals("xiao")&&user.getUpwd().equals("123"))
       User usercheck= urlParaService.getuser(user);
        if(usercheck!=null)
        {
            usercheck.end;
            //设置域名，实现数据共享
            // cookie.setDomain("sso.com");
            if(LoginCacheUtil.loginSession.get(user.getUname())!=null)
            {
                //使之前session失效
                //LoginCacheUtil.loginSession.get(user.getUname()).invalidate();
                //登录列表移除用户
                //LoginCacheUtil.loginSession.remove(user.getUname());
                //找到用户对应的token
                String token="";
                for(String skey:LoginCacheUtil.loginUser.keySet())
                {
                    User us=LoginCacheUtil.loginUser.get(skey);
                    if(us.getUname().equals(user.getUname()))
                    {
                        token=skey;
                        break;
                    }
                }
                //移除用户列表里的数据
                LoginCacheUtil.loginUser.remove(token);
                LoginCacheUtil.loginTime.remove(token);
                //return "";
            }
                // 保存用户登录信息
                String token = UUID.randomUUID().toString();
                System.out.println("login.token===" + token);
                Cookie cookie = new Cookie("TOKEN", token);
                // 把cookie写到客户端
                response.addCookie(cookie);
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = dateformat.format(System.currentTimeMillis());
                LoginCacheUtil.loginUser.put(token, user);
                LoginCacheUtil.loginTime.put(token,dateStr);
                LoginCacheUtil.loginSession.put(user.getUname(),session);
                map.put("code","0");
                map.put("msg","登录成功");
                map.put("url","/NewSocket.html");
                //return "redirect:/NewSocket.html";
        }else {
            //session.setAttribute("msg", "用户名或密码错误");
            //PrintWriter out=response.getWriter();
            //out.print("<script language='javascript'>alert('UserName Wrong!!');self.location='\\';</script>");
            map.put("code","1");
            map.put("msg","用户名或者密码错误");
            // return "redirect:/";
        }
        JSONObject jsonObj = new JSONObject(map);
        return  jsonObj.toString();
        //登录信息校验成功，重定向到原来的系统
//        String targetUrl = (String) session.getAttribute("target");
//        //如果是直接从登录系统登录的，校验成功后默认跳转到主系统 sys1 的首页
//        if(StringUtils.isEmpty(targetUrl)) {
//            targetUrl = "NewSocket.html";
//        }
//        return "redirect:" + targetUrl;
    }
    @RequestMapping("/check")
    @ResponseBody
    public String checksession(@CookieValue(required = false, value = "TOKEN")Cookie cookie)
    {
        HashMap<String,Object> map=new HashMap<>();
        JSONObject js=new JSONObject();
        if(cookie!=null)
        {
            String value=cookie.getValue();
            //cookie的token 与user匹配
            User user = LoginCacheUtil.loginUser.get(value);
            if(user==null)
            {
                map.put("code","0");//有token 没找到用户
            }
            else
            {
                map.put("code","1");//有token 找到用户
            }
        }
        else
        {
            map.put("code","2");//空白页面没有cookie
        }

        return new JSONObject(map).toJSONString();
    }
    @RequestMapping("/GetUser")
    @ResponseBody
    public String getuser()
    {
         String str="";
         Map<String,User> map=LoginCacheUtil.loginUser;
         Map<String,String> maptime=LoginCacheUtil.loginTime;
        str=JSONObject.toJSONString(map)+JSONObject.toJSONString(maptime);
         return str;
    }
    @RequestMapping("/deleteUser")
    @ResponseBody
    public  String deleteUser(@RequestParam(value ="token") String token)
    {
        //String key=cookie.getValue();
        //LoginCacheUtil.loginUser.remove(token);
        JSONObject js=new JSONObject();
        if(LoginCacheUtil.loginUser.remove(token)!=null) {
            LoginCacheUtil.loginTime.remove(token);
            js.put("code", "0");
            js.put("msg", "删除成功");
        }else
        {
            js.put("code", "1");
            js.put("msg", "删除失败");
        }
        return js.toJSONString();
    }
}
