package com.example.btc.services.ThreadingSocketService;

import com.example.btc.services.CustomMultiThreadingService.CustomMultiThreadingService;
import com.example.btc.services.http.hb.HttpHbGetCurrencys;
import com.example.btc.services.http.hb.HttpHbGetSymbols;
import com.example.btc.services.http.kb.HttpKbGetSymbols;
import com.example.btc.services.http.kb.HttpKbGetToken;
import com.example.btc.services.http.mocha.HttpMcGetSymbols;
import com.example.btc.services.http.zb.HttpZbGetSymbols;
import com.example.btc.services.ws.handler.*;
import com.example.btc.services.ws.util.LimitQueue;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class CustomerMultiThreadingSocket implements Serializable {
    private Logger logger = LoggerFactory.getLogger(CustomerMultiThreadingSocket.class);
    int i=0;
     private static final int sockettime=120000;
    static List<String> hbreqparams=new ArrayList<>();
    private static List<String> mcreqparams=new ArrayList<>();
    private static List<String> zbreqparams=new ArrayList<>();
    private static List<String> kbreqparams=new ArrayList<>();
    private static List<String> nor_reqparams=new ArrayList<>();
    private static String token="";
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    @Autowired
    public void setHbsymbols(HttpHbGetSymbols hbsymbols,HttpMcGetSymbols httpMcGetSymbols,HttpHbGetCurrencys hbcurrencys
    ,HttpZbGetSymbols httpZbGetSymbols,HttpKbGetSymbols httpKbGetSymbols,HttpKbGetToken httpKbGetToken) throws MalformedURLException {

        token=httpKbGetToken.getkbToken();
        kburl="wss://push-socketio.kucoin.top:6443/socket.io/?token="+token+"&format=json&acceptUserMessage=false&connectId=connect_welcome&EIO=3&transport=websocket";

        hbreqparams=hbsymbols.gethbSymbols();
        mcreqparams=httpMcGetSymbols.getmcSymbols();
        //OK的币种用火币代替
        nor_reqparams=hbcurrencys.gethbCurrencys();
        zbreqparams=httpZbGetSymbols.getZbSymbols();
        kbreqparams=httpKbGetSymbols.getkbSymbols();


    }
    @Autowired
    public  void   setkbtoken(HttpKbGetToken httpKbGetToken) throws MalformedURLException {
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                token=httpKbGetToken.getkbToken();
                kburl="wss://push-socketio.kucoin.top:6443/socket.io/?token="+token+"&format=json&acceptUserMessage=false&connectId=connect_welcome&EIO=3&transport=websocket";
            }
        }, 0, 120000, TimeUnit.MILLISECONDS);
    }

    public static final int limit=50;
    //public static String  hbresponse;
    public  static LimitQueue<String> hblqueue = new LimitQueue<String>(limit);

    public static LimitQueue<String> mclqueue = new LimitQueue<String>(limit);

    //public static String mcresponse;
//    public static String okresponse1;
//    public static String okresponse2;
//    public static String okresponse3;
    public static LimitQueue<String> oklqueue1 = new LimitQueue<String>(limit);
    public static LimitQueue<String> oklqueue2 = new LimitQueue<String>(limit);
    public static LimitQueue<String> oklqueue3 = new LimitQueue<String>(limit);

//    public static String btresponse1;
//    public static String btresponse2;
//    public static String btresponse3;
    public static LimitQueue<String> btlqueue1 = new LimitQueue<String>(limit);
    public static LimitQueue<String> btlqueue2 = new LimitQueue<String>(limit);
    public static LimitQueue<String> btlqueue3 = new LimitQueue<String>(limit);


//    public static String bnresponse1;
//    public static String bnresponse2;
//    public static String bnresponse3;
    public static LimitQueue<String> bnlqueue1 = new LimitQueue<String>(limit);
    public static LimitQueue<String> bnlqueue2 = new LimitQueue<String>(limit);
    public static LimitQueue<String> bnlqueue3 = new LimitQueue<String>(limit);

    //public static String zbresponse;
    public static LimitQueue<String> zblqueue = new LimitQueue<String>(limit);


//    public static String bsresponse1;
//    public static String bsresponse2;
//    public static String bsresponse3;
    public static LimitQueue<String> bslqueue1 = new LimitQueue<String>(limit);
    public static LimitQueue<String> bslqueue2 = new LimitQueue<String>(limit);
    public static LimitQueue<String> bslqueue3 = new LimitQueue<String>(limit);



//    public  static String kbresponse1;
//    public  static String kbresponse2;
//    public  static String kbresponse3;
//    public  static String kbresponse4;
    public static LimitQueue<String> kblqueue1 = new LimitQueue<String>(limit);
    public static LimitQueue<String> kblqueue2 = new LimitQueue<String>(limit);
    public static  LimitQueue<String> kblqueue3 = new LimitQueue<String>(limit);
    public static LimitQueue<String> kblqueue4 = new LimitQueue<String>(limit);

    OkWssMarketHandle okwssMarketHandle;
    OkBtcWssMarketHandle okBtcwssMarketHandle;
    OkEthWssMarketHandle okEthwssMarketHandle;

    BtWssMarketHandle btWssMarketHandle;//=new BtWssMarketHandle(bturl,sockettime);
    BtBtcWssMarketHandle btBtcWssMarketHandle;//=new BtBtcWssMarketHandle(bturl,sockettime);
    BtEthWssMarketHandle btEthWssMarketHandle;

    BnWssMarketHandle bnWssMarketHandle;//=new BnWssMarketHandle(bnurl,sockettime);
    BnBtcWssMarketHandle bnBtcWssMarketHandle;//=new BnBtcWssMarketHandle(bnurl,sockettime);
    BnEthWssMarketHandle bnEthWssMarketHandle;

    private ZbWssMarketHandle zbWssMarketHandle;

    BsWssMarketHandle bsWssMarketHandle;//=new BsWssMarketHandle(bsurl,sockettime);
    BsBtcWssMarketHandle bsBtcWssMarketHandle;///=new BsBtcWssMarketHandle(bsurl,sockettime);
    BsCncWssMarketHandle bsCncWssMarketHandle;

    KbWssMarketHandle kbWssMarketHandle;//=new KbWssMarketHandle(kburl,sockettime);
    KbWssMarketHandle kb2WssMarketHandle;//=new KbWssMarketHandle(kburl,sockettime);
    KbWssMarketHandle kb3WssMarketHandle;//=new KbWssMarketHandle(kburl,sockettime);
    KbWssMarketHandle kb4WssMarketHandle;//=

    WssMarketHandle wssMarketHandle;
    McWssMarketHandle mcWssMarketHandle;
    private  String okurl="wss://real.coinall.ltd:8443/ws/v3";
    private  String bturl="wss://ws.gateio.ws/v3/";
    private  String bnurl="wss://stream.yshyqxx.com/stream";
    private  String zburl="wss://api.zb.today/websocket/";
    private  String bsurl="wss://api.aex.zone/wsv3";
    //在获取token处拼接
    private  String kburl="";

    @Async
    @Scheduled(initialDelay=1000*1,fixedRate = sockettime)
    public void HbSocket() throws URISyntaxException, InterruptedException {
        String hburl="wss://www.huobi.ge/-/s/pro/ws";//"wss://api.huobiasia.vip/ws";
        logger.info("火币启动------");
        wssMarketHandle = new WssMarketHandle(hburl,String.valueOf(sockettime));
        wssMarketHandle.sub(hbreqparams, response -> {
            hblqueue.offer(response.toString());
        });
        Thread.sleep(sockettime);
    }

    @Async
    @Scheduled(initialDelay=1000*2,fixedRate = sockettime)
    public void McSocket() throws URISyntaxException, InterruptedException {
          String mcurl="wss://contract.mxc.la/ws";
        logger.info("抹茶启动------");

        mcWssMarketHandle=new McWssMarketHandle(mcurl,String.valueOf(sockettime));
        mcWssMarketHandle.sub(mcreqparams,response->{
            mclqueue.offer(response.toString());
        });
        Thread.sleep(sockettime);
    }

    @Async
    @Scheduled(initialDelay=1000*4,fixedRate = sockettime)
    public void OkSocket() throws URISyntaxException, InterruptedException {
        logger.info("OK_USDT启动------");
        okwssMarketHandle = new OkWssMarketHandle(okurl,String.valueOf(sockettime));
        okwssMarketHandle.sub(nor_reqparams, response -> {
            oklqueue1.offer(response.toString());
        });
        Thread.sleep(sockettime);
    }

    @Async
    @Scheduled(initialDelay=1000*6,fixedRate = sockettime)
    public void OkBtcSocket() throws URISyntaxException, InterruptedException {
        logger.info("OK_BTC启动------");
        okBtcwssMarketHandle = new OkBtcWssMarketHandle(okurl,String.valueOf(sockettime));
        okBtcwssMarketHandle.sub(nor_reqparams, response -> {
            oklqueue2.offer(response.toString());
        });
        Thread.sleep(sockettime);
    }
    @Async
    @Scheduled(initialDelay=1000*8,fixedRate = sockettime)
    public void OkEthSocket() throws URISyntaxException, InterruptedException {
        logger.info("OK_ETH启动------");
        okEthwssMarketHandle = new OkEthWssMarketHandle(okurl,String.valueOf(sockettime));
        okEthwssMarketHandle.sub(nor_reqparams, response -> {
            oklqueue3.offer(response.toString());
        });
        Thread.sleep(sockettime);
    }

    @Async
    @Scheduled(initialDelay=1000*10,fixedRate = sockettime)
    public void BtSocket() throws URISyntaxException, InterruptedException {
        logger.info("比特儿_USDT启动------");
        btWssMarketHandle=new BtWssMarketHandle(bturl,String.valueOf(sockettime));
        btWssMarketHandle.sub(nor_reqparams,response ->
        {
            btlqueue1.offer(response.toString());
        });
        Thread.sleep(sockettime);

    }
    @Async
    @Scheduled(initialDelay=1000*12,fixedRate = sockettime)
    public void BtBtcSocket() throws URISyntaxException, InterruptedException {
        logger.info("比特儿_BTC启动------");
         btBtcWssMarketHandle=new BtBtcWssMarketHandle(bturl,String.valueOf(sockettime));
        btBtcWssMarketHandle.sub(nor_reqparams,response ->
        {
            btlqueue2.offer(response.toString());
        });
        Thread.sleep(sockettime);
    }
    @Async
    @Scheduled(initialDelay=1000*14,fixedRate = sockettime)
    public void BtEthSocket() throws URISyntaxException, InterruptedException {
        logger.info("比特儿_ETH启动------");
        btEthWssMarketHandle=new BtEthWssMarketHandle(bturl,String.valueOf(sockettime));
        btEthWssMarketHandle.sub(nor_reqparams,response ->
        {
            btlqueue3.offer(response.toString());
        });
        Thread.sleep(sockettime);
    }

    @Async
    @Scheduled(initialDelay=1000*16,fixedRate = sockettime)
    public void BnSocket() throws URISyntaxException, InterruptedException {
        logger.info("币安_USDT启动------");
        bnWssMarketHandle=new BnWssMarketHandle(bnurl,String.valueOf(sockettime));
        bnWssMarketHandle.sub(nor_reqparams,response ->{

            bnlqueue1.offer(response.toString());
        });
        Thread.sleep(sockettime);
    }
    @Async
    @Scheduled(initialDelay=1000*18,fixedRate = sockettime)
    public void BnBtcSocket() throws URISyntaxException, InterruptedException {
        logger.info("币安_BTC启动------");
        bnBtcWssMarketHandle=new BnBtcWssMarketHandle(bnurl,String.valueOf(sockettime));
        bnBtcWssMarketHandle.sub(nor_reqparams,response ->{
            bnlqueue2.offer(response.toString());
        });
        Thread.sleep(sockettime);
    }
    @Async
    @Scheduled(initialDelay=1000*20,fixedRate = sockettime)
    public void BnEthSocket() throws URISyntaxException, InterruptedException {
        logger.info("币安_ETH启动------");
        bnEthWssMarketHandle=new BnEthWssMarketHandle(bnurl,String.valueOf(sockettime));
        bnEthWssMarketHandle.sub(nor_reqparams,response ->{
            bnlqueue3.offer(response.toString());
        });
        Thread.sleep(sockettime);
    }
    @Async
    @Scheduled(initialDelay=1000*22,fixedRate = sockettime)
    public void ZbSocket() throws URISyntaxException ,InterruptedException{
        logger.info("中币启动------");
        zbWssMarketHandle=new ZbWssMarketHandle(zburl,String.valueOf(sockettime));
        zbWssMarketHandle.sub(zbreqparams,response->{
            zblqueue.offer(response.toString());
        });
        Thread.sleep(sockettime);
    }
    @Async
    @Scheduled(initialDelay=1000*24,fixedRate = sockettime)
    public void BsSocket()throws URISyntaxException ,InterruptedException
    {
        logger.info("比特时代_USDT启动------");
        bsWssMarketHandle=new BsWssMarketHandle(bsurl,String.valueOf(sockettime));
        bsWssMarketHandle.sub(nor_reqparams,response->{
            bslqueue1.offer(response.toString());
        });
        Thread.sleep(sockettime);
    }
    @Async
    @Scheduled(initialDelay=1000*26,fixedRate = sockettime)
    public void BsBtcSocket()throws URISyntaxException ,InterruptedException
    {
        logger.info("比特时代_BTC启动------");
        bsBtcWssMarketHandle=new BsBtcWssMarketHandle(bsurl,String.valueOf(sockettime));
        bsBtcWssMarketHandle.sub(nor_reqparams,response->{
            bslqueue2.offer(response.toString());
        });
        Thread.sleep(sockettime);
    }
    @Async
    @Scheduled(initialDelay=1000*28,fixedRate = sockettime)
    public void BsCncSocket()throws URISyntaxException ,InterruptedException
    {
        logger.info("比特时代_BTC启动------");
        bsCncWssMarketHandle=new BsCncWssMarketHandle(bsurl,String.valueOf(sockettime));
        bsCncWssMarketHandle.sub(nor_reqparams,response->{
            bslqueue3.offer(response.toString());
        });
        Thread.sleep(sockettime);
    }
    @Async
    @Scheduled(initialDelay=1000*30,fixedRate = sockettime)
    public void KbSocket() throws  URISyntaxException ,InterruptedException
    {
        logger.info("库币_组1启动------");
        kbWssMarketHandle=new KbWssMarketHandle(kburl,String.valueOf(sockettime));
        List<String> kb1=kbreqparams.subList(0,99);
        kbWssMarketHandle.sub(kb1,response->{
            //logger.info("1"+response.toString());
            kblqueue1.offer(response.toString());
        });
        Thread.sleep(sockettime);
    }
    @Async
    @Scheduled(initialDelay=1000*32,fixedRate = sockettime)
    public void Kb2Socket() throws  URISyntaxException ,InterruptedException
    {
        logger.info("库币_组2启动------");
        kb2WssMarketHandle=new KbWssMarketHandle(kburl,String.valueOf(sockettime));
        List<String> kb2=kbreqparams.subList(100,199);
        kb2WssMarketHandle.sub(kb2,response->{
            kblqueue2.offer(response.toString());
        });
        Thread.sleep(sockettime);
    }

    @Async
    @Scheduled(initialDelay=1000*34,fixedRate = sockettime)
    public void Kb3Socket() throws  URISyntaxException ,InterruptedException
    {
        logger.info("库币_组3启动------");
        kb3WssMarketHandle=new KbWssMarketHandle(kburl,String.valueOf(sockettime));
        List<String> kb3=kbreqparams.subList(200,299);
        kb3WssMarketHandle.sub(kb3,response->{
            kblqueue3.offer(response.toString());
        });
        Thread.sleep(sockettime);
    }
    @Async
    @Scheduled(initialDelay=1000*36,fixedRate = sockettime)
    public void Kb4Socket() throws  URISyntaxException ,InterruptedException
    {
        logger.info("库币_组4启动------");
        kb4WssMarketHandle=new KbWssMarketHandle(kburl,String.valueOf(sockettime));
        List<String> kb4=kbreqparams.subList(300,399);
        kb4WssMarketHandle.sub(kb4,response->{
            kblqueue4.offer(response.toString());
        });
        Thread.sleep(sockettime);
    }
}
