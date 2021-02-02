package com.example.btc.services.ws.hb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.btc.services.webSocket.OnWebSocket;
import com.example.btc.services.ws.SubscriptionListener;
import com.example.btc.services.ws.event.MarketDetailSubResponse;
import com.example.btc.services.ws.handler.WssMarketHandle;
import com.example.btc.services.ws.handler.WssMarketReqHandle;
import org.java_websocket.exceptions.WebsocketNotConnectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.thymeleaf.expression.Lists;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
@PropertySource("classpath:url.properties")
@Service
public class Hbprice {
    @Value("${hburl}")
    private String hburl;
    @Value("${hbtime}")
    private String hbtime;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    OnWebSocket ws;
    public float getHbprice(String reqparam) throws URISyntaxException {
        //throws  URISyntaxException, InterruptedException
        long startTime=System.currentTimeMillis();
        AtomicReference<Float> price = new AtomicReference<>((float) 0);
        //reqparam="market.btcusdt.trade.detail";
        //SubscriptionListener response2 = null;
        //WssMarketReqHandle wssMarketReqHandle2 = new WssMarketReqHandle(hburl,response2);
        try {
            WssMarketReqHandle wssMarketReqHandle = new WssMarketReqHandle(hburl, response -> {
                //logger.info("火币接收原始数据{}",response);
                long endTime = System.currentTimeMillis();
                logger.info("火币数据加载完成,用时{}", (endTime - startTime) + "ms");
                JSONObject jsall = JSON.parseObject(response);
                String temp = jsall.get("data").toString();
                temp = temp.replaceAll("},", "}#");
                List<String> listdata = new ArrayList<String>();

                listdata = Arrays.asList(temp.substring(1, temp.length() - 1).split("#"));
                JSONObject jsdata = JSONObject.parseObject(listdata.get(0));
                price.set(jsdata.getFloatValue("price"));

                //logger.info(String.valueOf(price.get()));

                // logger.info("请求 KLine 数据用户收到的原始数据:{}", response);
                /// MarketKLineReqResponse marketKLineReqResponse = JSON.parseObject(response, MarketKLineReqResponse.class);
                // logger.info("请求 KLine 数据解析之后的数据为:{}", JSON.toJSON(marketKLineReqResponse));
            });

            Map<String, Object> param = new HashMap<>();
            param.put("req", reqparam);
            wssMarketReqHandle.doReq(JSON.toJSONString(param));
            Thread.sleep(Integer.parseInt(hbtime));
            wssMarketReqHandle.webSocketClient.close();
        }catch (InterruptedException|WebsocketNotConnectedException e)
        {
            return 0;
        }
        finally {

        }
        return price.get();
    }
    public void getHbArrayPrice(List<String> reqparams) throws MalformedURLException, URISyntaxException, InterruptedException {
        long startTime=System.currentTimeMillis();
        AtomicReference<Float> price = new AtomicReference<>((float) 0);

        WssMarketHandle wssMarketHandle = new WssMarketHandle(hburl);
        List<String> channels = new ArrayList<>();
        //reqparam="market.btcusdt.trade.detail";
        for(String para:reqparams)
        {
            String parado="market."+para+"usdt.trade.detail";
            channels.add(parado);
        }
       // channels.add("market.btcusdt.trade.detail");
        wssMarketHandle.sub(channels, response -> {
            logger.info("detailEvent用户收到的数据===============:{}", JSON.toJSON(response));
            JSONObject jsresult = JSONObject.parseObject(response.toString()); //JSON.toJSON(response)
            long endTime=System.currentTimeMillis();
            logger.info("火币数据加载完成,用时{}",(endTime-startTime)+"ms");
            MarketDetailSubResponse event = JSON.parseObject(response, MarketDetailSubResponse.class);
            //logger.info("detailEvent的ts为：{},当前的时间戳为：{},时间间隔为：{}毫秒", event.getTs(), currentTimeMillis, currentTimeMillis - event.getTs());
            //给前台发送数据
            ws.AppointSending(ws.name,response.toString());
        });
        //wssMarketHandle.
        Thread.sleep(Integer.MAX_VALUE);
        //Thread.sleep(Integer.parseInt(hbtime));
      //  return price.get();
    }
}
