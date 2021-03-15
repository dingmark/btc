package com.example.btc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2021-03-15.
 */
@Controller
public class OnLogin {
    @RequestMapping("/getLogin.do")
    @ResponseBody
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
        return  "redirect:/NewSocket.html";
    }
}
