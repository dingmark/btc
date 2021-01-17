package com.example.btc.services;

/**
 * @author dell
 */
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;


@Component
public class MyWebSocketClient implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {

    }

//    private static final Logger logger = LoggerFactory.getLogger(MyWebSocketClient.class);
//
//    private WebSocketClient client;
//
//    public MyWebSocketClient() { }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        createClient();
//    }
//
//    public void createClient() throws URISyntaxException, SocketException {
//        try {
//            String url="ws://api.huobiasia.vip/ws";
//            client = new WebSocketClient(new URI(url),new Draft_6455()) {
//                @Override
//                public void onOpen(ServerHandshake serverHandshake) {
//                    logger.info("连接成功");
//                    //可以向webSocket server发送消息
//                    client.send("{sub:\"market.btcusdt.trade.detail\", symbol: \"btcusdt\"}\n");
//                }
//
//                //接收消息
//                @Override
//                public void onMessage(String msg) {
//                    logger.info(msg);
//                    if(null!=msg&&!msg.trim().equals("")){
//                        if(msg.equals("over")){
//                            client.close();
//                        }
//                        try {
//                            //对消息做业务处理
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//                @Override
//                public void onClose(int i, String s, boolean b) {
//
//                    logger.info("连接已关闭");
//                }
//
//                @Override
//                public void onError(Exception e){
//                    logger.error("发生错误，关闭");
//                    try {
//                        new MyWebSocketClient().createClient();//谨慎加这句  无限循环的可能
//                    } catch (URISyntaxException | SocketException ex) {
//                        ex.printStackTrace();
//                    }
//                }
//            };
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        client.setConnectionLostTimeout(10000);
//        client.connect();
//        client.getSocket().setKeepAlive(true);
//
//        while(!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)){
//            logger.info("正在连接");
////            if(client.getReadyState().equals(WebSocket.READYSTATE.NOT_YET_CONNECTED))
////            {
////                client.connect();
////            }
//        }
//    }
}