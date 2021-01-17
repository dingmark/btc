package com.example.btc.services.ws.hb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.btc.services.ws.event.MarketKLineReqResponse;
import com.example.btc.services.ws.handler.WssMarketReqHandle;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class HbpriceTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    void getHbprice() throws URISyntaxException, InterruptedException {
         String hburl="wss://api.huobiasia.vip/ws";
        WssMarketReqHandle wssMarketReqHandle = new WssMarketReqHandle(hburl, response -> {
            logger.info("火币接受原始数据{}",response);
            JSONObject jsall=JSON.parseObject(response);
            String temp=jsall.get("data").toString();
            temp=temp.replaceAll("},","}#");
            List<String> listdata=new ArrayList<String>();

            listdata= Arrays.asList(temp.substring(1, temp.length() - 1).split("#"));
            JSONObject jsdata=JSONObject.parseObject( listdata.get(0));
             float price =jsdata.getFloat("price");
            logger.info(String.valueOf(price));
           // logger.info("请求 KLine 数据用户收到的原始数据:{}", response);
           /// MarketKLineReqResponse marketKLineReqResponse = JSON.parseObject(response, MarketKLineReqResponse.class);
           // logger.info("请求 KLine 数据解析之后的数据为:{}", JSON.toJSON(marketKLineReqResponse));
        });
        Map<String, Object> param = new HashMap<>();
        param.put("req", "market.btcusdt.trade.detail");
        wssMarketReqHandle.doReq(JSON.toJSONString(param));
        Thread.sleep(1000);
    }
}