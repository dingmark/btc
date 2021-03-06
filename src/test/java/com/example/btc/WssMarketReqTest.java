package com.example.btc;

import com.alibaba.fastjson.JSON;
import com.example.btc.services.ws.SubscriptionListener;
import com.example.btc.services.ws.event.MarketKLineReqResponse;
import com.example.btc.services.ws.event.MarketTradeDetailReqResponse;
import com.example.btc.services.ws.handler.WssMarketReqHandle;
import com.example.btc.services.ws.request.KLineSubRequest;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WssMarketReqTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String URL = "wss://api.huobiasia.vip/ws";//"wss://www.btcgateway.pro/ws";//合约站行情请求以及订阅地址


    /**
     * 请求 KLine 数据
     * "req": "market.$symbol.kline.$period",
     * "from": " type: long, 2017-07-28T00:00:00+08:00 至2050-01-01T00:00:00+08:00 之间的时间点，单位：秒",
     * "to": "type: long, 2017-07-28T00:00:00+08:00 至2050-01-01T00:00:00+08:00 之间的时间点，单位：秒，必须比 from 大"
     *
     * @throws URISyntaxException
     * @throws InterruptedException
     * @throws ParseException
     */
    @Test
    public void test1() throws URISyntaxException, InterruptedException {

        WssMarketReqHandle wssMarketReqHandle = new WssMarketReqHandle(URL, response -> {
            logger.info("请求 KLine 数据用户收到的原始数据:{}", response);
            MarketKLineReqResponse marketKLineReqResponse = JSON.parseObject(response, MarketKLineReqResponse.class);
            logger.info("请求 KLine 数据解析之后的数据为:{}", JSON.toJSON(marketKLineReqResponse));
        });

        while (true) {
            try {
                Date fromDate = DateUtils.parseDate("2019-11-01T00:00:00", "yyyy-MM-dd'T'HH:mm:ss");
                Date toDate = DateUtils.parseDate("2019-11-01T00:00:01", "yyyy-MM-dd'T'HH:mm:ss");

                KLineSubRequest kLineSubRequest = KLineSubRequest.builder()
                        .req("market.BTC_CQ.kline.1min")
                        .from(fromDate.getTime() / 1000)
                        .to(toDate.getTime() / 1000)
                        .build();
                wssMarketReqHandle.doReq(JSON.toJSONString(kLineSubRequest));

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Thread.sleep(1000);
            }

        }
    }


    /**
     * 请求 Trade Detail 数据
     *
     * @throws URISyntaxException
     * @throws InterruptedException
     */
    @Test
    public void test2() throws URISyntaxException, InterruptedException {

        WssMarketReqHandle wssMarketReqHandle = new WssMarketReqHandle(URL, response -> {
            logger.info("请求 Trade Detail 数据用户收到的原始数据:{}", response);
            //MarketTradeDetailReqResponse marketTradeDetailReqResponse = JSON.parseObject(response, MarketTradeDetailReqResponse.class);
            //logger.info("请求 Trade Detail 数据解析之后的数据为:{}", JSON.toJSON(marketTradeDetailReqResponse));
        });

        while (true) {
            try {
                Map<String, Object> param = new HashMap<>();
                param.put("req", "market.btcusdt.trade.detail");
                param.put("req","market.ethusdt.trade.detail");
                wssMarketReqHandle.doReq(JSON.toJSONString(param));
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    @Test
    public void test3() throws URISyntaxException, InterruptedException {

//        SubscriptionListener response2 = new SubscriptionListener() {
//
//            @Override
//            public void onReceive(Object data) throws InterruptedException {
//                logger.info(data.toString());
//
//            }
//        };
        WssMarketReqHandle wssMarketReqHandle2 = new WssMarketReqHandle(URL,response2->{
            logger.info("请求 Trade Detail 数据用户收到的原始数据:{}", response2);

        });
        Map<String, Object> param = new HashMap<>();
       // param.put("req", "market.btcusdt.depth.step0");
        param.put("req","market.symbols");
        wssMarketReqHandle2.doReq(JSON.toJSONString(param));
        Thread.sleep(Integer.MAX_VALUE);



    }


}
