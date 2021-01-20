package com.example.btc.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.btc.services.baseDaoService.UrlParaService;
import com.example.btc.services.http.bian.biAn;
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
import java.util.List;

@Controller

public class getValueClass {
    @Autowired Hbprice hb;
    @Autowired OkPrice okprice;
    @Autowired bter btr;
    @Autowired mocha mc;
    @Autowired biAn bn;
    @Autowired
    UrlParaService urlParaService;
    @RequestMapping("/getvalue.do")
    @ResponseBody
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
        /*access key
        mx0zDODNj1zO5qTeeb
        secret key
        3c770adeab3243cea4c2b46b2de54a0a
        */
        String mckey="mx0zDODNj1zO5qTeeb";
        //String mcurl="https://www.mxc.me/open/api/v1/data/history?api_key="+mckey+"market=BTC_USDT";
        String mcurl="https://www.mxc.me/open/api/v2/market/deals?api_key="+mckey+"&symbol=BTC_USDT&limit=10";
        float mcprice=mc.getMcPrice(new URL(mcurl));
        rejs.put("mc",mcprice);
        //币安交易
        String banurl="https://api.binancezh.cc/api/v3/trades?symbol=BTCUSDT&limit=5";
        float bnprice=bn.getBiAnPrice(new URL(banurl));
        rejs.put("bnprice",bnprice);
        long endTime=System.currentTimeMillis(); //获取结束时间
        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
        return  rejs.toString();

    }
    @RequestMapping("/test.do")
    @ResponseBody
    public String test()
    {
        List<String> list= urlParaService.getUrlPara();
        return list.toString();
    }
    @RequestMapping("/test1.do")
    public  String test1()
    {
        return "hello";
    }
}
