package com.example.btc.services.ws.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.btc.services.ws.SubscriptionListener;
import com.example.btc.services.ws.util.DealDepth;
import com.example.btc.services.ws.util.ZipUtil;
import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BEncoderStream;
import lombok.SneakyThrows;
import org.apache.commons.compress.compressors.deflate64.Deflate64CompressorInputStream;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class WssMarketHandle implements Cloneable{
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);

    private WebSocketClient webSocketClient;
    private String pushUrl = "";//合约站行情请求以及订阅地址
    AtomicLong pong = new AtomicLong(0);
    private Long lastPingTime = System.currentTimeMillis();
    private int trytime=0;
    private String sockettime;
    public WssMarketHandle() {

    }

    public WssMarketHandle(String pushUrl,String sockettime) {
        this.pushUrl = pushUrl;
        this.sockettime=sockettime;
    }

    public void sub(List<String> channels, SubscriptionListener<String> callback) throws URISyntaxException {
        doConnect(channels, callback);
    }


    private void doConnect(List<String> channels, SubscriptionListener<String> callback) throws URISyntaxException {

        HashMap<String,String> httpheader=new HashMap<>();
        httpheader.put("Sec-Websocket-Protocol","TLSv1,TLSv1.1,TLSv1.2,SSLv3");
        webSocketClient = new WebSocketClient(new URI(pushUrl),httpheader) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                logger.debug("onOpen Success");
                doSub(channels);
                //禁止火币交易重连3次退出
                dealReconnect();

                doClose();
            }
            @Override
            public void onMessage(String s) {
                logger.debug("onMessage:{}", s);
            }

            @Override
            public void onMessage(ByteBuffer bytes) {
                if(!fixedThreadPool.isShutdown()) {
                    fixedThreadPool.execute(() -> {
                        try {
                            lastPingTime = System.currentTimeMillis();
                            String message = new String(ZipUtil.decompress(bytes.array()), "UTF-8");
                            JSONObject JSONMessage = JSONObject.parseObject(message);
                            Object ch = JSONMessage.get("ch");
                            Object ping = JSONMessage.get("ping");
                            if (ch != null) {
                                JSONObject js = DealDepth.getHbDetpth(message);
                                if (js.get("asks") != null && js.get("bids") != null)
                                    callback.onReceive(js.toString());
                            }
                            if (ping != null) {
                                dealPing(JSONMessage.getLong("ping"));
                            }
                        } catch (Throwable e) {
                            logger.error("onMessage异常", e);
                        }
                    });
                }
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
        webSocketClient.close();
        fixedThreadPool.shutdownNow();
        scheduledExecutorService.shutdownNow();
        logger.info("火币关闭线程");
    }


    private void doSub(List<String> channels) {
        channels.stream().forEach(e -> {
            JSONObject sub = new JSONObject();
            sub.put("sub", "market." + e + ".depth.step0");
            webSocketClient.send(sub.toString());
        });
        /*channels.stream().forEach(e -> {
            JSONObject sub = new JSONObject();
            sub.put("sub", "market." + e + "usdt.depth.step0");
            webSocketClient.send(sub.toString());
        });
        channels.stream().forEach(e -> {
            JSONObject sub = new JSONObject();
            sub.put("sub", "market." + e + "btc.depth.step0");
            webSocketClient.send(sub.toString());
        });
        channels.stream().forEach(e -> {
            JSONObject sub = new JSONObject();
            sub.put("sub", "market." + e + "eth.depth.step0");
            webSocketClient.send(sub.toString());
        });*/

    }


    private void dealPing(Long pongs) {
                try {
                    //long lSysTime2 = System.currentTimeMillis();
                    JSONObject jsonMessage = new JSONObject();
                    jsonMessage.put("pong", System.currentTimeMillis());
                    logger.debug("发送pong:{}", jsonMessage.toString());
                    webSocketClient.send(jsonMessage.toString());
                } catch (Throwable t) {
                    logger.error("dealPing出现了异常");
                }
    }


    private void dealReconnect() {
        try {
            scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    try {
                        if ((webSocketClient.isClosed() && !webSocketClient.isClosing()) || System.currentTimeMillis() - lastPingTime > 10 * 1000) {
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
                    //每隔35秒销毁
                    closechannel();
                }
            }, Integer.parseInt(sockettime)/1000,1, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("dealReconnect scheduledExecutorService异常", e);
        }
    }

}
