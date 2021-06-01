package com.example.btc.services.webSocket;

import com.example.btc.baseDao.UrlPara;
import com.example.btc.services.CustomMultiThreadingService.CustomMultiThreadingService;
import com.example.btc.services.ThreadingSocketService.CustomerMultiThreadingSocket;
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
import com.example.btc.services.ws.util.LimitQueue;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;


@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "SpringJavaAutowiringInspection"})
@Slf4j
@Service
@ServerEndpoint("/test/{name}")//("/websocket/{name}")
public class OnWebSocket {

    private  static  CustomMultiThreadingService customMultiThreadingService;
    private  static  CustomerMultiThreadingSocket customerMultiThreadingSocket;
    /*private  static String sockettime;
    private static List<String> reqparams=new ArrayList<>();
    private static List<String> hbreqparams=new ArrayList<>();
    private static List<String> kbreqparams=new ArrayList<>();
    private static List<String> mcreqparams=new ArrayList<>();
    private static List<String> zbreqparams=new ArrayList<>();
    private static String token="";*/

    /*
    @Autowired
    public void setSockettime(SocketTime sockettime){
        //OnWebSocket.sockettime=sockettime.sockettime;
    }
    @Autowired
    public void setRepository(HttpHbGetCurrencys hbcurrencys) throws MalformedURLException {
        reqparams=hbcurrencys.gethbCurrencys();
    }
    @Autowired
    public void setZbsymbols(HttpZbGetSymbols httpZbGetSymbols) throws MalformedURLException {

        zbreqparams=httpZbGetSymbols.getZbSymbols();
    }
    @Autowired
    public void setHbsymbols(HttpHbGetSymbols hbsymbols) throws MalformedURLException {
        hbreqparams=hbsymbols.gethbSymbols();
    }

    @Autowired
    public  void  setkbtoken(HttpKbGetToken httpKbGetToken) throws MalformedURLException {
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                token=httpKbGetToken.getkbToken();
            }
        }, 0, 120000, TimeUnit.MILLISECONDS);
    }
    @Autowired
    public  void setkbsymbols(HttpKbGetSymbols httpKbGetSymbols) throws MalformedURLException {
        kbreqparams=httpKbGetSymbols.getkbSymbols();
    }
    @Autowired
    public  void setHttpMcGetSymbols(HttpMcGetSymbols httpMcGetSymbols)throws MalformedURLException
    {
        mcreqparams=httpMcGetSymbols.getmcSymbols();
    }*/
    //各站点实时价格获取开关
//    @Autowired
//    public  void  setCustomMultiThreadingService(CustomMultiThreadingService customMultiThreadingService){OnWebSocket.customMultiThreadingService=customMultiThreadingService;}

    //8个站点socket开关
//    @Autowired
//    public void setCustomSocket(CustomerMultiThreadingSocket ct) throws URISyntaxException, InterruptedException {
//        OnWebSocket.CustomerMultiThreadingSocket=ct;
//    }
    private Logger logger = LoggerFactory.getLogger(OnWebSocket.class);
    private Session session;
    /**
     * 标识当前连接客户端的用户名
     */
    public String name;
    /**
     *  用于存所有的连接服务的客户端，这个对象存储是安全的
     */
    private static ConcurrentHashMap<String, OnWebSocket> webSocketSet = new ConcurrentHashMap<>();
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(20);

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

    private static int rate=5000;
    void socketdo(String type)  {
        try {
            switch (type) {
                case "hb":
                    scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                        @SneakyThrows
                        @Override
                        public void run() {
                            try {
                                AppiontSendList(CustomerMultiThreadingSocket.hblqueue);
                            } catch (Throwable t)
                            {
                                t.printStackTrace();
                            }
                        }
                    }, 0, rate, TimeUnit.MILLISECONDS);
                    break;
                case "ok":
                    scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                        @SneakyThrows
                        @Override
                        public void run() {
                            try {
                                AppiontSendList(CustomerMultiThreadingSocket.oklqueue1);
                            } catch (Throwable t)
                            {
                                t.printStackTrace();
                            }
                        }
                    }, 0, rate, TimeUnit.MILLISECONDS);
                    scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                        @SneakyThrows
                        @Override
                        public void run() {
                            try {
                                AppiontSendList(CustomerMultiThreadingSocket.oklqueue2);
                            } catch (Throwable t)
                            {
                                t.printStackTrace();
                            }
                        }
                    }, 0, rate, TimeUnit.MILLISECONDS);
                    scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                        @SneakyThrows
                        @Override
                        public void run() {
                            try {
                                AppiontSendList(CustomerMultiThreadingSocket.oklqueue3);
                            } catch (Throwable t)
                            {
                                t.printStackTrace();
                            }

                        }
                    }, 0, rate, TimeUnit.MILLISECONDS);
                    break;
                case "bt":
                    scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                        @SneakyThrows
                        @Override
                        public void run() {
                            try {
                                AppiontSendList(CustomerMultiThreadingSocket.btlqueue1);
                            } catch (Throwable t)
                            {
                                t.printStackTrace();
                            }
                        }
                    }, 0, rate, TimeUnit.MILLISECONDS);
                    scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                        @SneakyThrows
                        @Override
                        public void run() {
                            try {
                                    AppiontSendList(CustomerMultiThreadingSocket.btlqueue2);
                            } catch (Throwable t)
                            {
                                t.printStackTrace();
                            }
                        }
                    }, 0, rate, TimeUnit.MILLISECONDS);
                    scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                        @SneakyThrows
                        @Override
                        public void run() {
                            try {
                                AppiontSendList(CustomerMultiThreadingSocket.btlqueue3);
                            } catch (Throwable t)
                            {
                                t.printStackTrace();
                            }
                        }
                    }, 0, rate, TimeUnit.MILLISECONDS);
                    break;
                case "bn":
                    scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                        @SneakyThrows
                        @Override
                        public void run() {
                            try {
                                AppiontSendList(CustomerMultiThreadingSocket.bnlqueue1);
                            } catch (Throwable t)
                            {
                                t.printStackTrace();
                            }
                        }
                    }, 0, rate, TimeUnit.MILLISECONDS);
                    scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                        @SneakyThrows
                        @Override
                        public void run() {
                            try {
                                AppiontSendList(CustomerMultiThreadingSocket.bnlqueue2);
                            } catch (Throwable t)
                            {
                                t.printStackTrace();
                            }
                        }
                    }, 0, rate, TimeUnit.MILLISECONDS);
                    scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                        @SneakyThrows
                        @Override
                        public void run() {
                            try {
                                AppiontSendList(CustomerMultiThreadingSocket.bnlqueue3);
                            } catch (Throwable t)
                            {
                                t.printStackTrace();
                            }
                        }
                    }, 0, rate, TimeUnit.MILLISECONDS);
                    break;
                case "mc":
                    scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                        @SneakyThrows
                        @Override
                        public void run() {
                            try {
                                AppiontSendList(CustomerMultiThreadingSocket.mclqueue);
                            }
                            catch (Throwable t)
                            {
                                t.printStackTrace();
                            }
                        }
                    }, 0, rate, TimeUnit.MILLISECONDS);
                    break;
                case "zb":
                    scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                        @SneakyThrows
                        @Override
                        public void run() {
                            try {
                                AppiontSendList(CustomerMultiThreadingSocket.zblqueue);
                            } catch (Throwable t)
                            {
                                t.printStackTrace();
                            }

                        }
                    }, 0, rate, TimeUnit.MILLISECONDS);
                     break;
                case "bs":
                    scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                        @SneakyThrows
                        @Override
                        public void run() {
                            try {
                                AppiontSendList(CustomerMultiThreadingSocket.bslqueue1);
                            } catch (Throwable t)
                            {
                                t.printStackTrace();
                            }
                        }
                    }, 0, rate, TimeUnit.MILLISECONDS);
                    scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                        @SneakyThrows
                        @Override
                        public void run() {
                            try {
                                AppiontSendList(CustomerMultiThreadingSocket.bslqueue2);
                            } catch (Throwable t)
                            {
                                t.printStackTrace();
                            }

                        }
                    }, 0, rate, TimeUnit.MILLISECONDS);
                    scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                        @SneakyThrows
                        @Override
                        public void run() {
                            try {
                                AppiontSendList(CustomerMultiThreadingSocket.bslqueue3);
                            } catch (Throwable t)
                            {
                                t.printStackTrace();
                            }

                        }
                    }, 0, rate, TimeUnit.MILLISECONDS);
                    break;
                case "kb":
                    scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                        @SneakyThrows
                        @Override
                        public void run() {
                            try {
                                AppiontSendList(CustomerMultiThreadingSocket.kblqueue1);
                            } catch (Throwable t)
                            {
                                t.printStackTrace();
                            }

                        }
                    }, 0, rate, TimeUnit.MILLISECONDS);
                    scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                        @SneakyThrows
                        @Override
                        public void run() {
                            try {
                                AppiontSendList(CustomerMultiThreadingSocket.kblqueue2);
                            } catch (Throwable t)
                            {
                                t.printStackTrace();
                            }

                        }
                    }, 0, rate, TimeUnit.MILLISECONDS);
                    scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                        @SneakyThrows
                        @Override
                        public void run() {
                            try {
                                AppiontSendList(CustomerMultiThreadingSocket.kblqueue3);
                            } catch (Throwable t)
                            {
                                t.printStackTrace();
                            }
                        }
                    }, 0, rate, TimeUnit.MILLISECONDS);
                    scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                        @SneakyThrows
                        @Override
                        public void run() {
                            try {
                                AppiontSendList(CustomerMultiThreadingSocket.kblqueue4);
                                //Iterator<String> iterator=  CustomerMultiThreadingSocket.kblqueue4.iterator();
                                /*while (iterator.hasNext()) {
                                    String a=iterator.next();
                                    AppointSending(name, a);
                                }*/
                            } catch (Throwable t)
                            {
                                t.printStackTrace();
                            }
                        }
                    }, 0, rate, TimeUnit.MILLISECONDS);
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
        catch (Exception   e)
        {
            logger.info("尝试给前端发送消息失败！！！{}",e);
        }
    }
    @OnError
    public void OnError(Throwable throwable)
    {
        this.scheduledExecutorService.shutdownNow();
        webSocketSet.remove(name);
        throwable.printStackTrace();
        log.info("{}报错",name);
        log.info("[WebSocket] 退出成功，当前连接人数为：={}",webSocketSet.size());
    }
    @OnClose
    public void OnClose() throws InterruptedException, URISyntaxException {
        this.scheduledExecutorService.shutdownNow();
        webSocketSet.remove(name);
        log.info("[WebSocket] 退出成功，当前连接人数为：={}",webSocketSet.size());
        /*String type=name.substring(0,2);
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
        }*/
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
    private void AppiontSendList(LimitQueue queue) throws InterruptedException {
        ArrayList<String> list = new ArrayList(queue);
        for(String a:list)
        {
            if(a !=null)
                AppointSending(name,a);
        }
    }
}