package com.example.btc.services.ws.handler;

import com.alibaba.fastjson.JSONObject;
import com.example.btc.services.ws.SubscriptionListener;
import com.example.btc.services.ws.util.DealDepth;
import com.example.btc.services.ws.util.JsToNew;
import lombok.SneakyThrows;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class KbBtcWssMarketHandle implements Cloneable{
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
    //scheduledExecutorService.setKeepAliveTime(10, TimeUnit.SECONDS);
             //scheduledExecutorService.allowCoreThreadTimeOut(true);
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
    private WebSocketClient webSocketClient;
    private String pushUrl = "";//合约站行情请求以及订阅地址
    AtomicLong pong = new AtomicLong(0);
    private Long lastPingTime = System.currentTimeMillis();
    private int trytime=0;

    public KbBtcWssMarketHandle() {

    }

    public KbBtcWssMarketHandle(String pushUrl) {
        this.pushUrl = pushUrl;
    }

    public void sub(List<String> channels, SubscriptionListener<String> callback) throws URISyntaxException {
        doConnect(channels, callback);
    }


    private void doConnect(List<String> channels, SubscriptionListener<String> callback) throws URISyntaxException {
        webSocketClient = new WebSocketClient(new URI(pushUrl)) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                logger.debug("onOpen Success");
                doSub(channels);
                //禁止火币交易重连3次退出
                dealReconnect();
                dealPing();
                doClose();
            }
            @SneakyThrows
            @Override
            public void onMessage(String s) {
                //logger.info("onMessage:{}", s);
                fixedThreadPool.execute(()->{
                    try {
                        if(JSONObject.parseObject(s).getString("type").equals("message"))
                        {
                            JSONObject js=DealDepth.getKbDepth(s);
                            if(js.get("asks")!=null&&js.get("bids")!=null)
                            callback.onReceive(js.toJSONString());
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });

            }

            @SneakyThrows
            @Override
            public void onMessage(ByteBuffer bytes) {

                callback.onReceive(bytes.toString());
            }

            @SneakyThrows
            @Override
            public void onClose(int i, String s, boolean b)
            {
                closechannel();
                logger.error("onClose i:{},s:{},b:{}", i, s, b);
            }

            @Override
            public void onError(Exception e) {
                logger.error("onError:", e);
            }
        };

        webSocketClient.connect();

    }


    public void closechannel() throws InterruptedException {
        //webSocketClient.connect();
        fixedThreadPool.shutdownNow();
        scheduledExecutorService.shutdownNow();
        webSocketClient.close();
        logger.info("库币BTC关闭线程");
    }

    private void doSub(List<String> channels) {
        //42["bullet","{\"id\":\"_event__subscribe_z2jmuey8f_1612922846807\",\"type\":\"subscribe\",\"topic\":\"/market/level2web:BTC-USDT,ETH-USDT\",\"privateChannel\":false,\"response\":true}"]
       // List<String> params=new ArrayList<>();
        String str="";
        for (String e:channels)
        {
             str +=e.toUpperCase()+"-BTC";
        }
        str=str.substring(0,str.length()-1);
        JSONObject sub = new JSONObject();
        sub.put("id","_event__subscribe_z2jmuey8f_1612922846807");
        sub.put("type","subscribe");
        sub.put("topic", "/market/level2:"+str);
        sub.put("privateChannel",false);
        sub.put("response",true);
       // String[] finalestr=new String[2];
        List<String> finalestr=new ArrayList<>();
        finalestr.add("bullet");
        finalestr.add(sub.toString());
            //sub.put("id","id7");
            webSocketClient.send("42"+ JsToNew.listToJson(finalestr));
    }
    private void dealPing() {
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    webSocketClient.send("2");
                } catch (Throwable t) {
                    logger.error("dealPing出现了异常");
                }
            }
        },0,40,TimeUnit.SECONDS);

    }


    private void dealReconnect() {
        try {
            scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    try {
                        if ((webSocketClient.isClosed() && !webSocketClient.isClosing()) ) {
                            logger.error("isClosed:{},isClosing:{}，准备重连", webSocketClient.isClosed(), webSocketClient.isClosing());
                            Boolean reconnectResult = webSocketClient.reconnectBlocking();
                            logger.error("重连的结果为：{}", reconnectResult);
                            if (!reconnectResult) {
                                webSocketClient.closeBlocking();
                                logger.error("closeBlocking");
                            }
                        }
                    } catch (Throwable e) {
                        logger.error("dealReconnect异常", e);
                    }

                }
            }, 30, 10, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("dealReconnect scheduledExecutorService异常", e);
        }

    }

    private void doClose() {
        try {
            scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    //每隔35秒销毁
                    closechannel();
                }
            }, 60, 60, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("dealReconnect scheduledExecutorService异常", e);
        }
    }

}
