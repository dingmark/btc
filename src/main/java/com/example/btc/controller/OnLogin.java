package com.example.btc.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Administrator on 2021-03-15.
 */
@Controller
public class OnLogin {
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
    public String doLogin(User user, HttpSession session, HttpServletResponse response
    ) {
        // 校验用户名密码
        if(user.getUname().equals("xiao")&&user.getUpwd().equals("123")) {
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
                //return "";
            }
                // 保存用户登录信息
                String token = UUID.randomUUID().toString();
                System.out.println("login.token===" + token);
                Cookie cookie = new Cookie("TOKEN", token);
                // 把cookie写到客户端
                response.addCookie(cookie);
                LoginCacheUtil.loginUser.put(token, user);
                LoginCacheUtil.loginSession.put(user.getUname(),session);
                return "redirect:/NewSocket.html";
        }else {
            session.setAttribute("msg", "用户名或密码错误");
            return "";
        }
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

}
