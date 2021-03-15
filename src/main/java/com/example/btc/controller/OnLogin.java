package com.example.btc.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    public String doLogin(User user, HttpSession session, HttpServletResponse response) {
        // 校验用户名密码
        if(user.getUname().equals("xiao")&&user.getUpwd().equals("123")) {
            // 保存用户登录信息
            String token = UUID.randomUUID().toString();
            System.out.println("login.token===" + token);
            Cookie cookie = new Cookie("TOKEN", token);
            //设置域名，实现数据共享
            cookie.setDomain("sso.com");
            // 把cookie写到客户端
            response.addCookie(cookie);
            LoginCacheUtil.loginUser.put(token, user);
        }else {
            session.setAttribute("msg", "用户名或密码错误");
            return "login";
        }
        //登录信息校验成功，重定向到原来的系统
        String targetUrl = (String) session.getAttribute("target");
        //如果是直接从登录系统登录的，校验成功后默认跳转到主系统 sys1 的首页
        if(StringUtils.isEmpty(targetUrl)) {
            targetUrl = "NewSocket.html";
        }
        return "redirect:" + targetUrl;
    }

}
