package com.example.btc.services.ws.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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

public class McWssMarketHandle implements Cloneable{
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

    private WebSocketClient webSocketClient;
    private String pushUrl = "";//合约站行情请求以及订阅地址
//    AtomicLong pong = new AtomicLong(0);
//    private Long lastPingTime = System.currentTimeMillis();
//    private int trytime=0;
    public McWssMarketHandle() {

    }
    private String sockettime;
    public McWssMarketHandle(String pushUrl,String sockettime) {
        this.pushUrl = pushUrl;
        this.sockettime=sockettime;
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
                //心跳
                dealPing();
                //定时关闭
                doClose();
            }
            @SneakyThrows
            @Override
            public void onMessage(String s) {
                fixedThreadPool.execute(()->{
                    try {
                        if (s.indexOf("push")!=-1)
                        {
                            JSONObject js=DealDepth.getMcDetpth(s);
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
        webSocketClient.close();
        scheduledExecutorService.shutdown();
        scheduledExecutorService.shutdownNow();
        logger.info("抹茶关闭线程");
    }


    private void doSub(List<String> channels) {
        //{  "method":"sub.depth.full","param":{"symbol":"ETH_USDT","limit":5}} 抹茶格式
         channels.stream().forEach(e ->{
                 //String[] st={"_USDT","_BTC","_ETH"};
                 JSONObject sub = new JSONObject();
                 JSONObject params=new JSONObject();
                 params.put("symbol",e.toUpperCase());
                 params.put("limit",5);
                 sub.put("param",params);
                 sub.put("method", "sub.depth.full");
                 webSocketClient.send(sub.toString());
            });

    }


    private void dealPing() {
        try {
            scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    JSONObject sub=new JSONObject();
                    sub.put("method","ping");
                    webSocketClient.send(sub.toString());
                }
            }, 30, 30, TimeUnit.SECONDS);//抹茶每隔60秒心跳测试这里设置30
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
                        if ((webSocketClient.isClosed() && !webSocketClient.isClosing())) {
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
            }, 10, 10, TimeUnit.SECONDS);
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
                    //每隔60秒销毁
                    closechannel();
                }
            }, Integer.parseInt(sockettime)/1000, 60, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("close scheduledExecutorService异常", e);
        }
    }

}
