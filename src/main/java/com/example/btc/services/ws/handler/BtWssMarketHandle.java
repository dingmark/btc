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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class BtWssMarketHandle implements Cloneable{
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);

    private WebSocketClient webSocketClient;
    private String pushUrl = "";//合约站行情请求以及订阅地址
    AtomicLong pong = new AtomicLong(0);
    private Long lastPingTime = System.currentTimeMillis();


    public BtWssMarketHandle() {

    }

    public BtWssMarketHandle(String pushUrl) {
        this.pushUrl = pushUrl;
    }

    public void sub(List<String> channel, SubscriptionListener<String> callback) throws URISyntaxException {
        doConnect(channel, callback);
    }


    private void doConnect(List<String> channel, SubscriptionListener<String> callback) throws URISyntaxException {
        //保存全量数据
        JSONObject jsall=new JSONObject();
        webSocketClient = new WebSocketClient(new URI(pushUrl)) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                logger.debug("onOpen Success");
                doSub(channel);
                dealReconnect();
                //心跳
                dealPing();
                doClose();
            }

            @Override
            public void onMessage(String s) {
                fixedThreadPool.execute(() -> {
                    if (s.indexOf("params") != -1) {
                        try {
                            JSONObject jsmess=JSONObject.parseObject(s);
                            JSONObject jsold;
                            if(jsmess.getJSONArray("params").getBoolean(0))
                            {
                                jsold =DealDepth.getBtDepth(s);
                                jsall.put(jsold.getString("symbol"),jsold);
                                callback.onReceive(jsold.toJSONString());
                            }
                            else
                            {
                                JSONObject  jsupdate=JSONObject.parseObject(s);
                                String symbol=jsupdate.getJSONArray("params").getString(2);
                                JSONObject js= jsall.getJSONObject(symbol);
                                jsupdate= DealDepth.getBtDepthUpdate(js,s);
                               // callback.onReceive("更新后数据"+jsupdate.toJSONString());
                                jsupdate=DealDepth.getBtDepth(jsupdate);
                                if(((List)jsupdate.get("asks")).size()>0&&((List)jsupdate.get("bids")).size()>0)
                                    callback.onReceive(jsupdate.toJSONString());
                                jsall.put(jsupdate.getString("symbol"),jsupdate);
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            @Override
            public void onMessage(ByteBuffer bytes) {

            }

            @SneakyThrows
            @Override
            public void onClose(int i, String s, boolean b) {
                closechannel();
                logger.error("onClose i:{},s:{},b:{}", i, s, b);
            }

            @Override
            public void onError(Exception e) {

               // logger.error("onError:", e);
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
        fixedThreadPool.shutdown();
        fixedThreadPool.shutdownNow();
        logger.info("比特儿关闭线程");
        if(!scheduledExecutorService.awaitTermination(1000, TimeUnit.MILLISECONDS)){
            // 超时的时候向线程池中所有的线程发出中断(interrupted)。
            scheduledExecutorService.shutdownNow();
            logger.info("比特儿关闭线程");
        }
    }

    public boolean isConnect()
    {
      return    webSocketClient.getSocket().isConnected();
    }
    private void doSub(List<String> channel) {
        //{"method":"depth.subscribe","id":6689915,"params":[["ADA_USDT",5,"0"],["BTC_USDT",5,"0"]]}
        //{"method":"depth.query","id":6689915,"params":["BTC_USDT",5,"0"]}
                JSONObject sub = new JSONObject();
                sub.put("id",6689915);
                sub.put("method","depth.subscribe");//depth.subscribe
                List<Object[]> channels=new ArrayList<>();
                for(String s:channel)
                {
                    Object[] ob=new Object[3];
                    ob[0]=s.toUpperCase()+"_USDT";
                    ob[1]=5;
                    ob[2]="0.0001";
                    //sub.put("params",ob);
                   // webSocketClient.send(sub.toJSONString());
                    channels.add(ob);
                }
                sub.put("params",channels);
                webSocketClient.send(sub.toString());
    }


    private void dealPing() {
        try {
            scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    // task to run goes here
                    Object[] channel = new Object[0];
                    JSONObject subjs = new JSONObject();
                    subjs.put("id",5644440);
                    subjs.put("method","server.ping");
                    subjs.put("params",channel);
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
                    //每隔35秒销毁
                    closechannel();
                }
            }, 60, 60, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("dealReconnect scheduledExecutorService异常", e);
        }
    }
}
