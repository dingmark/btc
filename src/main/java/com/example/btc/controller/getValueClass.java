package com.example.btc.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.btc.services.http.ok.OkPrice;
import com.example.btc.services.ws.hb.Hbprice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

@Controller
@ResponseBody
public class getValueClass {
    @Autowired Hbprice hb;
    @Autowired OkPrice okprice;
    @RequestMapping("/getvalue.do")
    public String getValueHb () throws InterruptedException, URISyntaxException, IOException {
        JSONObject rejs=new JSONObject();
        String param="market.btcusdt.trade.detail";
        float hbprice=hb.getHbprice(param);
        String hbpricestr=String.valueOf(hbprice);
        rejs.put("hb",hbprice);
        String okurl="https://www.okexcn.com/api/spot/v3/instruments/BTC-USDT/ticker";
        float okpircefloat=okprice.getOKprice(new URL(okurl));
        rejs.put("ok",okpircefloat);
        return  rejs.toString();

    }
}
