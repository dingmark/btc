package com.example.btc.services.webSocket;

import com.example.btc.baseDao.UrlPara;
import com.example.btc.services.CustomMultiThreadingService.CustomMultiThreadingService;
import com.example.btc.services.http.bter.bter;
import com.example.btc.services.http.hb.HttpHbGetCurrencys;
import com.example.btc.services.http.hb.HttpHbGetSymbols;
import com.example.btc.services.http.kb.HttpKbGetSymbols;
import com.example.btc.services.http.kb.HttpKbGetToken;
import com.example.btc.services.http.mocha.HttpMcGetSymbols;
import com.example.btc.services.http.mocha.mocha;
import com.example.btc.services.http.ok.OkPrice;
import com.example.btc.services.http.zb.HttpZbGetSymbols;
import com.example.btc.services.ws.handler.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;


@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "SpringJavaAutowiringInspection"})
@Slf4j
@Service
@ServerEndpoint("/test/{name}")//("/websocket/{name}")
public class OnWebSocket {
    private  static String sockettime;
    //private  static BnWssMarketHandle bnWssMarketHandle;
    private  static HttpKbGetToken httpKbGetToken;
    private  static  HttpKbGetSymbols httpKbGetSymbols;
    private  static HttpMcGetSymbols httpMcGetSymbols;
    private  static HttpZbGetSymbols httpZbGetSymbols;
    private  static HttpHbGetCurrencys hbcurrencys;
    private  static HttpHbGetSymbols hbGetSymbols;
    private  static  CustomMultiThreadingService customMultiThreadingService;
    private  static UrlPara urlPara;
    private  static  OkPrice okPrice;
    private  static bter mbter;
    private static mocha mmocha;
    private static List<String> reqparams=new ArrayList<>();
    private static List<String> hbreqparams=new ArrayList<>();
    private static List<String> kbreqparams=new ArrayList<>();
    private static List<String> mcreqparams=new ArrayList<>();
    private static List<String> zbreqparams=new ArrayList<>();
    private static String token="";
    @Autowired
    public void setSockettime(SocketTime sockettime){
        OnWebSocket.sockettime=sockettime.sockettime;
    }
    @Autowired
    public void setRepository(HttpHbGetCurrencys hbcurrencys) throws MalformedURLException {
        OnWebSocket.hbcurrencys=hbcurrencys;
        reqparams=hbcurrencys.gethbCurrencys();
    }
    @Autowired
    public void setZbsymbols(HttpZbGetSymbols httpZbGetSymbols) throws MalformedURLException {
        OnWebSocket.httpZbGetSymbols=httpZbGetSymbols;
        zbreqparams=httpZbGetSymbols.getZbSymbols();
    }
    @Autowired
    public void setHbsymbols(HttpHbGetSymbols hbsymbols) throws MalformedURLException {
        OnWebSocket.hbGetSymbols=hbsymbols;
        hbreqparams=hbsymbols.gethbSymbols();
    }
    @Autowired
    public void setUrlPara(UrlPara urlPara)  {OnWebSocket.urlPara=urlPara;
    }
    @Autowired
    public  void  setOkPrice(OkPrice okPrice){OnWebSocket.okPrice=okPrice;};
    @Autowired
    public  void  setBter(bter mbter){OnWebSocket.mbter=mbter;}
    @Autowired
    public void setMocha(mocha mmocha){OnWebSocket.mmocha=mmocha;}
    @Autowired
    public  void  setkbtoken(HttpKbGetToken httpKbGetToken) throws MalformedURLException { OnWebSocket.httpKbGetToken=httpKbGetToken;
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                token=httpKbGetToken.getkbToken();
            }
        }, 0, 120000, TimeUnit.MILLISECONDS);
    }
    @Autowired
    public  void setkbsymbols(HttpKbGetSymbols httpKbGetSymbols) throws MalformedURLException { OnWebSocket.httpKbGetSymbols=httpKbGetSymbols;
        kbreqparams=httpKbGetSymbols.gethbSymbols();
    }
    @Autowired
    public  void setHttpMcGetSymbols(HttpMcGetSymbols httpMcGetSymbols)throws MalformedURLException
    {
        OnWebSocket.httpMcGetSymbols=httpMcGetSymbols;
        mcreqparams=httpMcGetSymbols.getmcSymbols();
    }
    @Autowired
    public  void  setCustomMultiThreadingService(CustomMultiThreadingService customMultiThreadingService){OnWebSocket.customMultiThreadingService=customMultiThreadingService;}
//    @Autowired  //=new BnWssMarketHandle(bnurl);
//    public void setBn(BnWssMarketHandle bnWssMarketHandle){
//        bnWssMarketHandle.pushUrl=bnurl;
//        bnWssMarketHandle.socketTime=sockettime;
//        this.bnWssMarketHandle=bnWssMarketHandle;
//    }

    private Logger logger = LoggerFactory.getLogger(OnWebSocket.class);
    String hburl="wss://api.huobiasia.vip/ws";
    private  String okurl="wss://real.coinall.ltd:8443/ws/v3";
    private  String bturl="wss://ws.gateio.ws/v3/";//"wss://webws.gateio.live/v3/?v=647320";//wss://ws.gateio.ws/v3/
    private  String bnurl="wss://stream.yshyqxx.com/stream";
    private  String mcurl="wss://contract.mxc.la/ws";
    private  String zburl="wss://api.zb.today/websocket/";
    private  String bsurl="wss://api.aex.zone/wsv3";
    //库币前端socket地址
    //private  String kburl="wss://push-private.kucoin.top/endpoint?token="+token+"&format=json&acceptUserMessage=false&connectId=connect_welcome&EIO=3&transport=websocket";
    private  String kburl="wss://push-socketio.kucoin.top:6443/socket.io/?token="+token+"&format=json&acceptUserMessage=false&connectId=connect_welcome&EIO=3&transport=websocket";


    private Session session;

    /**
     * 标识当前连接客户端的用户名
     */
    public String name;

    /**
     *  用于存所有的连接服务的客户端，这个对象存储是安全的
     */
    private static ConcurrentHashMap<String, OnWebSocket> webSocketSet = new ConcurrentHashMap<>();
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
    private WssMarketHandle wssMarketHandle;

    OkWssMarketHandle okwssMarketHandle;
    OkBtcWssMarketHandle okBtcwssMarketHandle;
    OkEthWssMarketHandle okEthwssMarketHandle;

    BtWssMarketHandle btWssMarketHandle;//=new BtWssMarketHandle(bturl,sockettime);
    BtBtcWssMarketHandle btBtcWssMarketHandle;//=new BtBtcWssMarketHandle(bturl,sockettime);
    BtEthWssMarketHandle btEthWssMarketHandle;

    BnWssMarketHandle bnWssMarketHandle;//=new BnWssMarketHandle(bnurl,sockettime);
    BnBtcWssMarketHandle bnBtcWssMarketHandle;//=new BnBtcWssMarketHandle(bnurl,sockettime);
    BnEthWssMarketHandle bnEthWssMarketHandle;

    BsWssMarketHandle bsWssMarketHandle;//=new BsWssMarketHandle(bsurl,sockettime);
    BsBtcWssMarketHandle bsBtcWssMarketHandle;///=new BsBtcWssMarketHandle(bsurl,sockettime);
    BsCncWssMarketHandle bsCncWssMarketHandle;

    KbWssMarketHandle kbWssMarketHandle;//=new KbWssMarketHandle(kburl,sockettime);
    KbWssMarketHandle kb2WssMarketHandle;//=new KbWssMarketHandle(kburl,sockettime);
    KbWssMarketHandle kb3WssMarketHandle;//=new KbWssMarketHandle(kburl,sockettime);
    KbWssMarketHandle kb4WssMarketHandle;//=

    ZbWssMarketHandle zbWssMarketHandle;

    McWssMarketHandle mcWssMarketHandle;
    @OnOpen
    public void OnOpen(Session session, @PathParam(value = "name") String name) throws InterruptedException, URISyntaxException, MalformedURLException {
        this.session = session;
        UUID uuid = UUID.randomUUID();
        this.name = name+uuid;
        // name是用来表示唯一客户端，如果需要指定发送，需要指定发送通过name来区分
        webSocketSet.put(this.name,this);
        log.info("[WebSocket] 连接成功，当前连接人数为：={}",webSocketSet.size());
        String type=name.substring(0,2);
        //8个站点输出实时btc eth兑换美元

        //8个站点输出depth参数
        socketdo(type);
    }

    void socketdo(String type)  {
        try {
            switch (type) {
                case "hb":
                    wssMarketHandle = new WssMarketHandle(hburl,sockettime);
                    wssMarketHandle.sub(hbreqparams, response -> {
                        if(this.session.isOpen()) {
                            AppointSending(name, response.toString());
                        }
                    });
                    Thread.sleep(Integer.parseInt(sockettime));
                    break;
                case "ok":
                     okwssMarketHandle = new OkWssMarketHandle(okurl,sockettime);
                     okBtcwssMarketHandle = new OkBtcWssMarketHandle(okurl,sockettime);
                     okEthwssMarketHandle = new OkEthWssMarketHandle(okurl,sockettime);
                    okwssMarketHandle.sub(reqparams, response -> {
                        if(this.session.isOpen()) {
                            AppointSending(name, response.toString());
                        }
                    });
                    okBtcwssMarketHandle.sub(reqparams, response -> {
                        if(this.session.isOpen()) {
                            AppointSending(name, response.toString());
                        }
                    });
                    okEthwssMarketHandle.sub(reqparams, response -> {
                        if(this.session.isOpen()) {
                            AppointSending(name, response.toString());
                        }
                    });
                    Thread.sleep(Integer.parseInt(sockettime));
                    break;
                case "bt":
                     btWssMarketHandle=new BtWssMarketHandle(bturl,sockettime);
                     btBtcWssMarketHandle=new BtBtcWssMarketHandle(bturl,sockettime);
                     btEthWssMarketHandle=new BtEthWssMarketHandle(bturl,sockettime);
                    btWssMarketHandle.sub(reqparams,response ->
                        {
                            if(this.session.isOpen()) {
                                AppointSending(name, response.toString());
                            }
                        });
                    btBtcWssMarketHandle.sub(reqparams,response ->
                    {
                        if(this.session.isOpen()) {
                            AppointSending(name, response.toString());
                        }
                    });
                    btEthWssMarketHandle.sub(reqparams,response ->
                    {
                        if(this.session.isOpen()) {
                            AppointSending(name, response.toString());
                        }
                    });
                        Thread.sleep(Integer.parseInt(sockettime));
                    break;
                case "bn":
                    bnWssMarketHandle=new BnWssMarketHandle(bnurl,sockettime);
                    bnBtcWssMarketHandle=new BnBtcWssMarketHandle(bnurl,sockettime);
                    bnEthWssMarketHandle=new BnEthWssMarketHandle(bnurl,sockettime);
                    bnWssMarketHandle.sub(reqparams,response ->{
                        if(this.session.isOpen()) {
                            AppointSending(name, response.toString());
                        }
                    });
                    bnBtcWssMarketHandle.sub(reqparams,response ->{
                        if(this.session.isOpen()) {
                            AppointSending(name, response.toString());
                        }
                    });
                    bnEthWssMarketHandle.sub(reqparams,response ->{
                        if(this.session.isOpen()) {
                            AppointSending(name, response.toString());
                        }
                    });
                    Thread.sleep(Integer.parseInt(sockettime));
                    break;
                case "mc":
                     mcWssMarketHandle=new McWssMarketHandle(mcurl,sockettime);
                    mcWssMarketHandle.sub(mcreqparams,response->{
                        //logger.info(response.toString());
                        if(this.session.isOpen()) {
                            AppointSending(name, response.toString());
                        }
                    });
                    Thread.sleep(Integer.parseInt(sockettime));
                    break;
                case "zb":
                     zbWssMarketHandle=new ZbWssMarketHandle(zburl,sockettime);
                   // ZbQcWssMarketHandle zbQcWssMarketHandle=new ZbQcWssMarketHandle(zburl);
                   // ZbBtcWssMarketHandle zbBtcWssMarketHandle=new ZbBtcWssMarketHandle(zburl);
                    zbWssMarketHandle.sub(zbreqparams,response->{
                        //logger.info(response.toString());
                        if(this.session.isOpen()) {
                            AppointSending(name, response.toString());
                        }
                    });
                    Thread.sleep(Integer.parseInt(sockettime));
                case "bs":
                     bsWssMarketHandle=new BsWssMarketHandle(bsurl,sockettime);
                     bsBtcWssMarketHandle=new BsBtcWssMarketHandle(bsurl,sockettime);
                     bsCncWssMarketHandle=new BsCncWssMarketHandle(bsurl,sockettime);

                    bsWssMarketHandle.sub(reqparams,response->{
                        if(this.session.isOpen()) {
                            AppointSending(name, response.toString());
                        }
                    });
                    bsBtcWssMarketHandle.sub(reqparams,response->{
                        if(this.session.isOpen()) {
                            AppointSending(name, response.toString());
                        }
                    });
                    bsCncWssMarketHandle.sub(reqparams,response->{
                        if(this.session.isOpen()) {
                            AppointSending(name, response.toString());
                        }
                    });

                    Thread.sleep(Integer.parseInt(sockettime));
                    break;
                case "kb":

                     kbWssMarketHandle=new KbWssMarketHandle(kburl,sockettime);
                     kb2WssMarketHandle=new KbWssMarketHandle(kburl,sockettime);
                     kb3WssMarketHandle=new KbWssMarketHandle(kburl,sockettime);
                     kb4WssMarketHandle=new KbWssMarketHandle(kburl,sockettime);
                    List<String> kb1=kbreqparams.subList(0,99);

                    kbWssMarketHandle.sub(kb1,response->{
                        if(this.session.isOpen()) {
                            AppointSending(name, response.toString());
                        }
                    });
                    List<String> kb2=kbreqparams.subList(100,199);
                    kb2WssMarketHandle.sub(kb2,response->{
                        if(this.session.isOpen()) {
                            AppointSending(name, response.toString());
                        }
                    });
                    List<String> kb3=kbreqparams.subList(200,299);
                    kb3WssMarketHandle.sub(kb3,response->{
                        if(this.session.isOpen()) {
                            AppointSending(name, response.toString());
                        }
                    });
                    List<String> kb4=kbreqparams.subList(300,399);
                    kb4WssMarketHandle.sub(kb4,response->{
                        if(this.session.isOpen()) {
                            AppointSending(name, response.toString());
                        }
                    });
                    Thread.sleep(Integer.parseInt(sockettime));
                    break;
                case "re":
                    scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                        @SneakyThrows
                        @Override
                        public void run() {
                            AppointSending(name, customMultiThreadingService.hbrealjs.toString());
                        }
                    }, 0, 500, TimeUnit.MILLISECONDS);

                    break;
                default:
                    break;
            }
        }
        catch (InterruptedException | URISyntaxException  e)
        {
            logger.info("尝试给前端发送消息失败！！！{}",e);
        }
    }
    @OnClose
    public void OnClose() throws InterruptedException, URISyntaxException {
        this.
        scheduledExecutorService.shutdownNow();
        webSocketSet.remove(name);
        log.info("[WebSocket] 退出成功，当前连接人数为：={}",webSocketSet.size());
        String type=name.substring(0,2);
        switch (type)
        {
            case "hb":
                 wssMarketHandle.closechannel();
                break;
            case "bn":
                 bnWssMarketHandle.closechannel();//=new BnWssMarketHandle(bnurl,sockettime);
                 bnBtcWssMarketHandle.closechannel();//=new BnBtcWssMarketHandle(bnurl,sockettime);
                 bnEthWssMarketHandle.closechannel();
                break;
            case "bt":
                 btWssMarketHandle.closechannel();//=new BtWssMarketHandle(bturl,sockettime);
                 btBtcWssMarketHandle.closechannel();//=new BtBtcWssMarketHandle(bturl,sockettime);
                 btEthWssMarketHandle.closechannel();
                break;
            case "bs":
                 bsWssMarketHandle.closechannel();//=new BsWssMarketHandle(bsurl,sockettime);
                 bsBtcWssMarketHandle.closechannel();///=new BsBtcWssMarketHandle(bsurl,sockettime);
                 bsCncWssMarketHandle.closechannel();
                break;
            case "ok":
                 okwssMarketHandle.closechannel();
                 okBtcwssMarketHandle.closechannel();
                 okEthwssMarketHandle.closechannel();
                break;
            case "mc":
                 mcWssMarketHandle.closechannel();
                break;
            case "zb":
                 zbWssMarketHandle.closechannel();
                break;
            case "kb":
                 kbWssMarketHandle.closechannel();//=new KbWssMarketHandle(kburl,sockettime);
                 kb2WssMarketHandle.closechannel();//=new KbWssMarketHandle(kburl,sockettime);
                 kb3WssMarketHandle.closechannel();//=new KbWssMarketHandle(kburl,sockettime);
                 kb4WssMarketHandle.closechannel();
                break;
            case "re":
                scheduledExecutorService.shutdownNow();
                break;
            default:
                break;
        }
    }

    @OnMessage
    public void OnMessage(String message) throws InterruptedException {
        log.info("[WebSocket] 收到消息：{}",message);
      //接收心跳
        String type=name.substring(0,2);
        switch (type)
        {
            case"hb":
                AppointSending(name,"pong");
                break;
            case"ok":
                AppointSending(name,"pong");
                break;
            case"bt":
                AppointSending(name,"pong");
                break;
        }


       // String channeltype=name.substring(0,2);
        //JSONObject jspara=JSONObject.parseObject(message);
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
    public void AppointSending(String name,String message) throws InterruptedException {
        // if (this.session.isOpen())
        synchronized(this.session) {
            try {
                //Thread.sleep(1000);
                webSocketSet.get(name).session.getBasicRemote().sendText(message);
            } catch (IllegalStateException | IOException e) {
                // e.printStackTrace();
                logger.info("发送报错");
            }
        }
        // webSocketSet.get(name).session.getAsyncRemote().sendText(message);
    }
}