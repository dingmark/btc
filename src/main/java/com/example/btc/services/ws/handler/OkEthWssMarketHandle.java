package com.example.btc.services.ws.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.btc.services.ws.SubscriptionListener;
import com.example.btc.services.ws.util.DealDepth;
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
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class OkEthWssMarketHandle implements Cloneable{
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);

    private WebSocketClient webSocketClient;
    private String pushUrl = "";//合约站行情请求以及订阅地址
    AtomicLong pong = new AtomicLong(0);
    private Long lastPingTime = System.currentTimeMillis();


    public OkEthWssMarketHandle() {

    }

    public OkEthWssMarketHandle(String pushUrl) {
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
                dealReconnect();
                //心跳
                dealPing();
                doClose();
            }


            @Override
            public void onMessage(String s) {

                logger.debug("onMessage:{}", s);
            }

            @Override
            public void onMessage(ByteBuffer bytes) {
                fixedThreadPool.execute(() -> {
                    try {
                        lastPingTime = System.currentTimeMillis();
                        final String s = uncompress(bytes.array());
                        //logger.info(s);
                        //回调
                        if(!s.equals("pong")&&JSONObject.parseObject(s).get("data") !=null)
                        {
                            JSONObject js= DealDepth.getOkDetpth(s);
                            callback.onReceive(js.toJSONString());
                        }
                    } catch (Throwable e) {
                        logger.error("OK 接收onMessage异常", e);

                    }
                });
            }

            @SneakyThrows
            @Override
            public void onClose(int i, String s, boolean b) {
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

    public  boolean isConnect()
    {
       return webSocketClient.getSocket().isConnected();
    }
    public void closechannel() throws InterruptedException {
        //webSocketClient.connect();
        fixedThreadPool.shutdownNow();
        scheduledExecutorService.shutdownNow();
        webSocketClient.close();
        logger.info("OK关闭线程");

    }


    private void doSub(List<String> channels) {
        // List<String> reqparamok = urlPara.getHbpara();
        List<String> channelok = new ArrayList<>();
        for (String para : channels) {
            String parado = "spot/depth5:" + para.toUpperCase() + "-ETH";
            channelok.add(parado);
        }
        final String s = listToJson(channelok);
        //JSONObject jspara=new JSONObject();
        //jspara.put("op","subscribe");
        //jspara.put("args",s);
        //{"op": "subscribe", "args": ["spot/depth5:ETH-USDT","spot/depth5:BTC-USDT"]}
        final String str = "{\"op\": \"subscribe\", \"args\":" + s + "}";
        webSocketClient.send(str);
    }
    private void sub(String message)
    {
        webSocketClient.send(message);
    }


    private void dealPing() {
        try {
            scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    sub("ping");
                }
            }, 29, 29, TimeUnit.SECONDS);//ok心跳30秒
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
            }, 60, 10, TimeUnit.SECONDS);
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
    //ok解压函数
    private static String uncompress(final byte[] bytes) {
        try (final ByteArrayOutputStream out = new ByteArrayOutputStream();
             final ByteArrayInputStream in = new ByteArrayInputStream(bytes);
             final Deflate64CompressorInputStream zin = new Deflate64CompressorInputStream(in)) {
            final byte[] buffer = new byte[1024];
            int offset;
            while (-1 != (offset = zin.read(buffer))) {
                out.write(buffer, 0, offset);
            }
            return out.toString();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    //参数转化
    private String listToJson(final List<String> list) {
        final JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(list);
        return jsonArray.toJSONString();
    }
}
