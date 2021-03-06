package com.example.btc.services.ws.handler;

import com.alibaba.fastjson.JSONObject;
import com.example.btc.services.ws.SubscriptionListener;
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

public class BsNewPriceWssMarketHandle implements Cloneable{
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
    //scheduledExecutorService.setKeepAliveTime(10, TimeUnit.SECONDS);
             //scheduledExecutorService.allowCoreThreadTimeOut(true);
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

    private WebSocketClient webSocketClient;
    private String pushUrl = "";//合约站行情请求以及订阅地址
    AtomicLong pong = new AtomicLong(0);
    private Long lastPingTime = System.currentTimeMillis();
    private int trytime=0;
    public BsNewPriceWssMarketHandle() {

    }

    public BsNewPriceWssMarketHandle(String pushUrl) {
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
                if(!fixedThreadPool.isShutdown()) {
                    fixedThreadPool.execute(() -> {
                        if (s.indexOf("pong") == -1) {
                            try {
                                callback.onReceive(s);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }

            @Override
            public void onMessage(ByteBuffer bytes) {
                logger.info(bytes.toString());
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
        logger.info("比特时代获取最新价格关闭线程");
    }


    private void doSub(List<String> channels) {
        //{"cmd":3,"action":"sub","code":20000,"symbol":"btc_usdt,eth_usdt"}
        //{"cmd":1,"action":"sub","symbol":"btc_cnc,eth_cnc"}
        JSONObject sub = new JSONObject();
        String params = "";
            //params.add(e+"_usdt");
//        for(String e:channels)
//        {
//            params +=e+"_usdt,";
//        }

       // params=params.substring(0,params.length()-1);
        params="btc_usdt,eth_usdt,usdt_cnc";
        sub.put("symbol", params);
        sub.put("code",20000);
        sub.put("action", "sub");
        sub.put("cmd",1);
        webSocketClient.send(sub.toString());
    }
    private void dealPing() {
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                webSocketClient.send("ping");
            }
        },0,10,TimeUnit.SECONDS);

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
            }, 20, 20, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("dealReconnect scheduledExecutorService异常", e);
        }
    }

    private void doClose()  {
        try {
            scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    //获取实时价格每隔20秒销毁
                    closechannel();
                }
            }, 20, 1, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("dealReconnect scheduledExecutorService异常", e);
        }
    }

}
