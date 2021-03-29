package com.example.btc.services.ws.handler;

import com.alibaba.fastjson.JSONObject;
import com.example.btc.services.ws.SubscriptionListener;
import com.example.btc.services.ws.util.DealDepth;
import com.example.btc.services.ws.util.ZipUtil;
import lombok.SneakyThrows;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class ZbWssMarketHandle implements Cloneable{
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);

    private WebSocketClient webSocketClient;
    private String pushUrl = "";//合约站行情请求以及订阅地址
    AtomicLong pong = new AtomicLong(0);
    private Long lastPingTime = System.currentTimeMillis();
    private int trytime=0;

    public ZbWssMarketHandle() {

    }

    public ZbWssMarketHandle(String pushUrl) {
        this.pushUrl = pushUrl;
    }

    public  void sub(List<String> channels, SubscriptionListener<String> callback) throws URISyntaxException {
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
                fixedThreadPool.execute(()->{
                    try {
                        if(s.indexOf("channel")!=-1)
                        {
                            JSONObject js= DealDepth.getZbDepth(s);
                            if(js.get("asks")!=null&&js.get("bids")!=null)
                            callback.onReceive(js.toJSONString());
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            public void onMessage(ByteBuffer bytes) {

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
        logger.info("中币关闭线程");
    }


    private void doSub(List<String> channels) {
        //{"event":"addChannel","channel":"ltcbtc_depth","length":5}
        channels.stream().forEach(e->{
            e=e.replace("_","");
            JSONObject sub = new JSONObject();
            sub.put("length",5);
            sub.put("channel",e+"_depth");
            sub.put("event", "addChannel");
            webSocketClient.send(sub.toString());
        });
    }


    private void dealPing() {
        try {
            scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    //{"channel":"ping","event":"addChannel","binary":true,"isZip":true}
                    // task to run goes here
                    JSONObject subjs = new JSONObject();
                    subjs.put("channel","ping");
                    subjs.put("event","addChannel");
                    subjs.put("binary",true);
                    subjs.put("isZip",true);
                    webSocketClient.send(subjs.toString());
                }
            }, 10, 10, TimeUnit.SECONDS);//比特儿10秒一次心跳
        } catch (Exception e) {
            logger.error("dealReconnect scheduledExecutorService异常", e);
        }
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
