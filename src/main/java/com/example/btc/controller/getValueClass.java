package com.example.btc.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.btc.services.http.bter.bter;
import com.example.btc.services.http.mocha.mocha;
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
    @Autowired bter btr;
    @Autowired mocha mc;
    @RequestMapping("/getvalue.do")
    public String getValueHb () throws InterruptedException, URISyntaxException, IOException {
        JSONObject rejs=new JSONObject();
        long startTime=System.currentTimeMillis();   //获取开始时间

        //火币
        String param="market.btcusdt.trade.detail";
        float hbprice=hb.getHbprice(param);
        String hbpricestr=String.valueOf(hbprice);
        rejs.put("hb",hbprice);
        //OK
        String okurl="https://www.okexcn.com/api/spot/v3/instruments/BTC-USDT/ticker";
        float okpircefloat=okprice.getOKprice(new URL(okurl));
        rejs.put("ok",okpircefloat);
        //比特儿
        String bteurl="https://fx-api.gateio.ws/api/v4/futures/btc/contracts/BTC_USD";
        float btrprice=btr.getbteprice(new URL(bteurl));
        rejs.put("btr",btrprice);
        //抹茶
        String mcurl="https://www.mxc.me/open/api/v1/data/history?market=BTC_USDT";
        float mcprice=mc.getMcPrice(new URL(mcurl));
        rejs.put("mc",mcprice);

        long endTime=System.currentTimeMillis(); //获取结束时间
        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
        return  rejs.toString();

    }
}
