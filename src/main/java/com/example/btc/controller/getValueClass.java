package com.example.btc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.btc.services.baseDaoService.UrlParaService;
import com.example.btc.services.http.bian.biAn;
import com.example.btc.services.http.bian.BiAnListprice;
import com.example.btc.services.http.bter.BterListprice;
import com.example.btc.services.http.bter.bter;
import com.example.btc.services.http.hb.HttpHbList;
import com.example.btc.services.http.hb.HttpHbNewPrice;
import com.example.btc.services.http.mocha.MochaList;
import com.example.btc.services.http.mocha.mocha;
import com.example.btc.services.http.ok.OkListPrice;
import com.example.btc.services.http.ok.OkPrice;
import com.example.btc.services.ws.hb.Hbprice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

@Controller
public class getValueClass {
    @Autowired Hbprice hb;
    @Autowired HttpHbNewPrice httpHbNewPrice;
    @Autowired OkPrice okprice;
    @Autowired bter btr;
    @Autowired mocha mc;
    @Autowired biAn bn;
    @Autowired UrlParaService urlParaService;

    @Autowired HttpHbList hblist;
    @Autowired OkListPrice okListPrice;
    @Autowired BiAnListprice biAnListprice;
    @Autowired BterListprice bterListprice;
    @Autowired MochaList mochaList;
    @RequestMapping("/getNewValue.do")
    @ResponseBody
    public String getnewvalue(HttpServletRequest request) throws MalformedURLException {
        String param=request.getParameter("symbol");
       float price= httpHbNewPrice.gethbNewPrice(param);
        JSONObject rejs=new JSONObject();
        rejs.put("org","hb");
        rejs.put(param,price);
        return rejs.toString();
    }
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
        //String okurl="https://www.okexcn.com/api/spot/v3/instruments/BTC-USDT/ticker";

        float okpircefloat=okprice.getOKprice("HT");
        rejs.put("ok",okpircefloat);
        //比特儿
       // String bteurl="https://fx-api.gateio.ws/api/v4/futures/btc/contracts/BTC_USD";
        float btrprice=btr.getbteprice("btc".toUpperCase());
        rejs.put("btr",btrprice);
        //抹茶
        /*access key
        mx0zDODNj1zO5qTeeb
        secret key
        3c770adeab3243cea4c2b46b2de54a0a
        */
        //String mckey="mx0zDODNj1zO5qTeeb";
        //String mcurl="https://www.mxc.me/open/api/v1/data/history?api_key="+mckey+"market=BTC_USDT";
       // String mcurl="https://www.mxc.me/open/api/v2/market/deals?api_key="+mckey+"&symbol=BTC_USDT&limit=10";
        float mcprice=mc.getMcPrice("btc".toUpperCase());
        rejs.put("mc",mcprice);
        //币安交易
        String banurl="https://api.binancezh.cc/api/v3/trades?symbol=BTCUSDT&limit=5";
        float bnprice=bn.getBiAnPrice("btc".toUpperCase());
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
        JSONObject js =new JSONObject();
        js.put("res",list);
        return js.toString();
    }
    @RequestMapping("/test3.do")
    public  String test3() throws InterruptedException, URISyntaxException, MalformedURLException {
        List<String> paras=
        urlParaService.getUrlPara();
        hb.getHbArrayPrice(paras);
        return "hello";
    }
    @RequestMapping("/testlist.do")
    @ResponseBody
    public String testlist() throws MalformedURLException {
        String str="";
        String para="btc";
        JSONObject jsresutl=new JSONObject();
        JSONObject jstmp=new JSONObject();
        jstmp= biAnListprice.getBiAnListPrice(para.toUpperCase());
        jsresutl.putAll(jstmp);
        jstmp=bterListprice.getbteListprice(para);
        jsresutl.putAll(jstmp);
        jstmp=hblist.gethblist(para);
        jsresutl.putAll(jstmp);
        jstmp=mochaList.getMcListPrice(para.toUpperCase());
        jsresutl.putAll(jstmp);
        jstmp=okListPrice.getOKListprice(para.toUpperCase());
        jsresutl.putAll(jstmp);
        //JSON js=jsresutl;
        jsresutl.put("type",para.toUpperCase());
       return  jsresutl.toJSONString();
    }
}
