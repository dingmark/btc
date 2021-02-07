package com.example.btc.services.ws.handler;

import com.alibaba.fastjson.JSONObject;
import com.example.btc.services.ws.SubscriptionListener;
import com.example.btc.services.ws.util.ZipUtil;
import lombok.SneakyThrows;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class BtWssMarketHandle implements Cloneable{
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);

    private WebSocketClient webSocketClient;
    private String pushUrl = "";//合约站行情请求以及订阅地址
    AtomicLong pong = new AtomicLong(0);
    private Long lastPingTime = System.currentTimeMillis();


    public BtWssMarketHandle() {

    }

    public BtWssMarketHandle(String pushUrl) {
        this.pushUrl = pushUrl;
    }

    public void sub(Object[] channel, SubscriptionListener<String> callback) throws URISyntaxException {
        doConnect(channel, callback);
    }


    private void doConnect(Object[] channel, SubscriptionListener<String> callback) throws URISyntaxException {
        webSocketClient = new WebSocketClient(new URI(pushUrl)) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                logger.debug("onOpen Success");
                doSub(channel);
                dealReconnect();
                final Runnable runnable = new Runnable() {
                    //String time = new Date().toString();
                    @Override
                    public void  run()
                    {}
                };
                final ScheduledExecutorService service = Executors
                        .newSingleThreadScheduledExecutor();
               // service.scheduleAtFixedRate(runnable, 1, 10, TimeUnit.SECONDS);

            }


            @SneakyThrows
            @Override
            public void onMessage(String s) {
                //webSocketClient.close();
                JSONObject js=JSONObject.parseObject(s);
                if(s.indexOf("pong")==-1) {
                   // logger.info("onMessage:{}", s);
                    callback.onReceive(s);
                }
            }
            @Override
            public void onMessage(ByteBuffer bytes) {
                fixedThreadPool.execute(() -> {
                    try {
                        lastPingTime = System.currentTimeMillis();
                        String message = new String(ZipUtil.decompress(bytes.array()), "UTF-8");
                        JSONObject JSONMessage = JSONObject.parseObject(message);
                        Object ch = JSONMessage.get("ch");
                        Object ping = JSONMessage.get("ping");
                        if (ch != null) {
                            callback.onReceive(message);
                        }
                        if (ping != null) {
                            dealPing();
                        }
                    } catch (Exception e ) {
                        logger.error("比特儿交易onMessage异常", e);
                    }
                });
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                logger.error("onClose i:{},s:{},b:{}", i, s, b);
            }

            @Override
            public void onError(Exception e) {

               // logger.error("onError:", e);
            }
        };

        webSocketClient.connect();

    }


    public void close() throws InterruptedException {
        //webSocketClient.connect();
        webSocketClient.close();
        scheduledExecutorService.shutdown();
        scheduledExecutorService.shutdownNow();
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
    private void doSub(Object[] channel) {
                JSONObject sub = new JSONObject();
                sub.put("id",6689915);
                sub.put("method","depth.subscribe");
                sub.put("params",channel);
                webSocketClient.send(sub.toString());
    }


    private void dealPing() {
        try {
            JSONObject jsonMessage = new JSONObject();
            jsonMessage.put("pong", pong.incrementAndGet());
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
                    // task to run goes here
                    Object[] channel = new Object[0];
                    JSONObject subjs = new JSONObject();
                    subjs.put("id",5644440);
                    subjs.put("method","server.ping");
                    subjs.put("params",channel);
                    webSocketClient.send(subjs.toString());
                }
            }, 10, 10, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("dealReconnect scheduledExecutorService异常", e);
        }

    }


}
