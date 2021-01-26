package com.example.btc.services.webSocket;

import com.alibaba.fastjson.JSONObject;
import com.example.btc.baseDao.UrlPara;
import com.example.btc.services.CustomMultiThreadingService.CustomMultiThreadingService;
import com.example.btc.services.http.bian.biAn;
import com.example.btc.services.http.bter.bter;
import com.example.btc.services.http.mocha.mocha;
import com.example.btc.services.http.ok.OkPrice;
import com.example.btc.services.ws.hb.Hbprice;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "SpringJavaAutowiringInspection"})
@Slf4j
@Service
@ServerEndpoint("/test/{name}")//("/websocket/{name}")
public class OnWebSocket {
    private static Hbprice hb;
    private  static UrlPara urlPara;
    private  static  OkPrice okPrice;
    private  static bter mbter;
    @Autowired
    public void setRepository(Hbprice hb) {
        OnWebSocket.hb = hb;
    }
    @Autowired
    public void setUrlPara(UrlPara urlPara){OnWebSocket.urlPara=urlPara;}
    @Autowired
    public  void  setOkPrice(OkPrice okPrice){OnWebSocket.okPrice=okPrice;};
    @Autowired
    public  void  setBter(bter mbter){OnWebSocket.mbter=mbter;}

    private Logger logger = LoggerFactory.getLogger(OnWebSocket.class);
    /**
     *  与某个客户端的连接对话，需要通过它来给客户端发送消息
     */
    private Session session;

    /**
     * 标识当前连接客户端的用户名
     */
    public String name;

    /**
     *  用于存所有的连接服务的客户端，这个对象存储是安全的
     */
    private static ConcurrentHashMap<String, OnWebSocket> webSocketSet = new ConcurrentHashMap<>();


    @OnOpen
    public void OnOpen(Session session, @PathParam(value = "name") String name) throws InterruptedException, URISyntaxException, MalformedURLException {
        this.session = session;
        UUID uuid = UUID.randomUUID();
        this.name = name+uuid;
        // name是用来表示唯一客户端，如果需要指定发送，需要指定发送通过name来区分
        webSocketSet.put(this.name,this);
        log.info("[WebSocket] 连接成功，当前连接人数为：={}",webSocketSet.size());

    }


    @OnClose
    public void OnClose(){
        webSocketSet.remove(this.name);
        log.info("[WebSocket] 退出成功，当前连接人数为：={}",webSocketSet.size());
    }

    @OnMessage
    public void OnMessage(String message)  {
        log.info("[WebSocket] 收到消息：{}",message);
        String channeltype=name.substring(0,2);
        JSONObject jspara=JSONObject.parseObject(message);
        try
        {
            switch (channeltype)
            {
                case "hb":
                        Thread.sleep(500);
                        JSONObject jr=new JSONObject();
                        String param="market."+jspara.getString("hb")+"usdt.trade.detail";
                        float hbprice=hb.getHbprice(param);
                        String hbpricestr=String.valueOf(hbprice);
                        jr.put(jspara.getString("hb"),hbprice);
                        AppointSending(name,jr.toString());
                    logger.info("接收火币参数执行火币获取最近火币价格");
                    break;
                case "ok":
                        JSONObject jok=new JSONObject();
                        String paraok=jspara.getString("ok");
                        float okprice=okPrice.getOKprice(paraok);
                        jok.put(paraok,okprice);
                        AppointSending(name,jok.toString());
                        Thread.sleep(1000);
                    break;
                case "bt"://比特儿
                    JSONObject jbter=new JSONObject();
                    String parabter=jspara.getString("bt");
                    float bterprice=okPrice.getOKprice(parabter.toUpperCase());//转大写
                    jbter.put(parabter,bterprice);
                    AppointSending(name,jbter.toString());
                    Thread.sleep(1000);
                    break;
                default:
                    break;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //报错之后重新连接websocket
           // webSocketSet.put(this.name,this);
        }

        //判断是否需要指定发送，具体规则自定义

//        if(message.indexOf("TOUSER") == 0){
//            String name = message.substring(message.indexOf("TOUSER")+6,message.indexOf(";"));
//            AppointSending(name,message.substring(message.indexOf(";")+1,message.length()));
//        }else{
//            //GroupSending(message);
//            GroupSending(String.valueOf(i));
//        }


    }

    /**
     * 群发
     * @param message
     */
    public void GroupSending(String message){
        for (String name : webSocketSet.keySet()){
            try {
                webSocketSet.get(name).session.getBasicRemote().sendText(message);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 指定发送
     * @param name
     * @param message
     */
    public void AppointSending(String name,String message){
        try {
            webSocketSet.get(name).session.getBasicRemote().sendText(message);
        }catch (IOException e){
            //e.printStackTrace();
            logger.info(name+"退出通信");
            OnClose();
        }
    }
}