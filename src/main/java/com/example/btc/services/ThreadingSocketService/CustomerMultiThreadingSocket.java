package com.example.btc.services.ThreadingSocketService;

import com.example.btc.services.CustomMultiThreadingService.CustomMultiThreadingService;
import com.example.btc.services.http.hb.HttpHbGetCurrencys;
import com.example.btc.services.http.hb.HttpHbGetSymbols;
import com.example.btc.services.http.kb.HttpKbGetSymbols;
import com.example.btc.services.http.kb.HttpKbGetToken;
import com.example.btc.services.http.mocha.HttpMcGetSymbols;
import com.example.btc.services.http.zb.HttpZbGetSymbols;
import com.example.btc.services.ws.handler.*;
import com.example.btc.services.ws.util.FixSizeLinkedList;
import com.example.btc.services.ws.util.FixSizeLinkedList;
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
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
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

    public static final int limit=30;
    //public static String  hbresponse;
    public  static FixSizeLinkedList<String> hblqueue = new FixSizeLinkedList<String>(limit);

    public static FixSizeLinkedList<String> mclqueue = new FixSizeLinkedList<String>(limit);

    //public static String mcresponse;
//    public static String okresponse1;
//    public static String okresponse2;
//    public static String okresponse3;
    public static FixSizeLinkedList<String> oklqueue1 = new FixSizeLinkedList<String>(limit);
    public static FixSizeLinkedList<String> oklqueue2 = new FixSizeLinkedList<String>(limit);
    public static FixSizeLinkedList<String> oklqueue3 = new FixSizeLinkedList<String>(limit);

//    public static String btresponse1;
//    public static String btresponse2;
//    public static String btresponse3;
    public static FixSizeLinkedList<String> btlqueue1 = new FixSizeLinkedList<String>(limit);
    public static FixSizeLinkedList<String> btlqueue2 = new FixSizeLinkedList<String>(limit);
    public static FixSizeLinkedList<String> btlqueue3 = new FixSizeLinkedList<String>(limit);


//    public static String bnresponse1;
//    public static String bnresponse2;
//    public static String bnresponse3;
    public static FixSizeLinkedList<String> bnlqueue1 = new FixSizeLinkedList<String>(limit);
    public static FixSizeLinkedList<String> bnlqueue2 = new FixSizeLinkedList<String>(limit);
    public static FixSizeLinkedList<String> bnlqueue3 = new FixSizeLinkedList<String>(limit);

    //public static String zbresponse;
    public static FixSizeLinkedList<String> zblqueue = new FixSizeLinkedList<String>(limit);


//    public static String bsresponse1;
//    public static String bsresponse2;
//    public static String bsresponse3;
    public static FixSizeLinkedList<String> bslqueue1 = new FixSizeLinkedList<String>(limit);
    public static FixSizeLinkedList<String> bslqueue2 = new FixSizeLinkedList<String>(limit);
    public static FixSizeLinkedList<String> bslqueue3 = new FixSizeLinkedList<String>(limit);



//    public  static String kbresponse1;
//    public  static String kbresponse2;
//    public  static String kbresponse3;
//    public  static String kbresponse4;
    public static FixSizeLinkedList<String> kblqueue1 = new FixSizeLinkedList<String>(limit);
    public static FixSizeLinkedList<String> kblqueue2 = new FixSizeLinkedList<String>(limit);
    public static  FixSizeLinkedList<String> kblqueue3 = new FixSizeLinkedList<String>(limit);
    public static FixSizeLinkedList<String> kblqueue4 = new FixSizeLinkedList<String>(limit);

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
    @Scheduled(fixedRate = sockettime)
    public void HbSocket() throws URISyntaxException, InterruptedException {
        String hburl="wss://api.huobiasia.vip/ws";
        logger.info("火币启动------");
        wssMarketHandle = new WssMarketHandle(hburl,String.valueOf(sockettime));
        wssMarketHandle.sub(hbreqparams, response -> {
              //  AppointSending(name, response.toString());
           // logger.info("火币交易"+response.toString());
            hblqueue.add(response.toString());
        });
        Thread.sleep(sockettime-1000);
        //wssMarketHandle.closechannel();
    }
    @Async
    @Scheduled(fixedRate = sockettime)
    public void McSocket() throws URISyntaxException, InterruptedException {
          String mcurl="wss://contract.mxc.la/ws";
        logger.info("抹茶启动------");

        mcWssMarketHandle=new McWssMarketHandle(mcurl,String.valueOf(sockettime));
        mcWssMarketHandle.sub(mcreqparams,response->{
            mclqueue.add(response.toString());
        });
        Thread.sleep(sockettime-1000);
      //  mcWssMarketHandle.closechannel();
    }

    @Async
    @Scheduled(fixedRate = sockettime)
    public void OkSocket() throws URISyntaxException, InterruptedException {
        logger.info("OK_USDT启动------");
        okwssMarketHandle = new OkWssMarketHandle(okurl,String.valueOf(sockettime));
        okwssMarketHandle.sub(nor_reqparams, response -> {
            oklqueue1.add(response.toString());
        });
        Thread.sleep(sockettime-1000);
    }

    @Async
    @Scheduled(fixedRate = sockettime)
    public void OkBtcSocket() throws URISyntaxException, InterruptedException {
        logger.info("OK_BTC启动------");
        okBtcwssMarketHandle = new OkBtcWssMarketHandle(okurl,String.valueOf(sockettime));
        okBtcwssMarketHandle.sub(nor_reqparams, response -> {
            oklqueue2.add(response.toString());
        });
        Thread.sleep(sockettime-1000);
    }
    @Async
    @Scheduled(fixedRate = sockettime)
    public void OkEthSocket() throws URISyntaxException, InterruptedException {
        logger.info("OK_ETH启动------");
        okEthwssMarketHandle = new OkEthWssMarketHandle(okurl,String.valueOf(sockettime));
        okEthwssMarketHandle.sub(nor_reqparams, response -> {
            oklqueue3.add(response.toString());
        });
        Thread.sleep(sockettime-1000);
    }

    @Async
    @Scheduled(fixedRate = sockettime)
    public void BtSocket() throws URISyntaxException, InterruptedException {
        logger.info("比特儿_USDT启动------");
        btWssMarketHandle=new BtWssMarketHandle(bturl,String.valueOf(sockettime));
        btWssMarketHandle.sub(nor_reqparams,response ->
        {
            btlqueue1.add(response.toString());
        });
        Thread.sleep(sockettime-1000);

    }
    @Async
    @Scheduled(fixedRate = sockettime)
    public void BtBtcSocket() throws URISyntaxException, InterruptedException {
        logger.info("比特儿_BTC启动------");
         btBtcWssMarketHandle=new BtBtcWssMarketHandle(bturl,String.valueOf(sockettime));
        btBtcWssMarketHandle.sub(nor_reqparams,response ->
        {
            btlqueue2.add(response.toString());
        });
        Thread.sleep(sockettime-1000);
    }
    @Async
    @Scheduled(fixedRate = sockettime)
    public void BtEthSocket() throws URISyntaxException, InterruptedException {
        logger.info("比特儿_ETH启动------");
        btEthWssMarketHandle=new BtEthWssMarketHandle(bturl,String.valueOf(sockettime));
        btEthWssMarketHandle.sub(nor_reqparams,response ->
        {
            btlqueue3.add(response.toString());
        });
        Thread.sleep(sockettime-1000);
    }

    @Async
    @Scheduled(fixedRate = sockettime)
    public void BnSocket() throws URISyntaxException, InterruptedException {
        logger.info("币安_USDT启动------");
        bnWssMarketHandle=new BnWssMarketHandle(bnurl,String.valueOf(sockettime));
        bnWssMarketHandle.sub(nor_reqparams,response ->{

            bnlqueue1.add(response.toString());
        });
        Thread.sleep(sockettime-1000);
    }
    @Async
    @Scheduled(fixedRate = sockettime)
    public void BnBtcSocket() throws URISyntaxException, InterruptedException {
        logger.info("币安_ETH启动------");
        bnBtcWssMarketHandle=new BnBtcWssMarketHandle(bnurl,String.valueOf(sockettime));
        bnBtcWssMarketHandle.sub(nor_reqparams,response ->{
            Thread.sleep(1000);
            bnlqueue2.add(response.toString());
        });
        Thread.sleep(sockettime-1000);
    }
    @Async
    @Scheduled(fixedRate = sockettime)
    public void BnEthSocket() throws URISyntaxException, InterruptedException {
        logger.info("币安_ETH启动------");
        bnEthWssMarketHandle=new BnEthWssMarketHandle(bnurl,String.valueOf(sockettime));
        bnEthWssMarketHandle.sub(nor_reqparams,response ->{
            bnlqueue3.add(response.toString());
        });
        Thread.sleep(sockettime-1000);
    }
    @Async
    @Scheduled(fixedRate = sockettime)
    public void ZbSocket() throws URISyntaxException ,InterruptedException{
        logger.info("中币启动------");
        zbWssMarketHandle=new ZbWssMarketHandle(zburl,String.valueOf(sockettime));
        zbWssMarketHandle.sub(zbreqparams,response->{
            zblqueue.add(response.toString());
        });
        Thread.sleep(sockettime-1000);
    }
    @Async
    @Scheduled(fixedRate = sockettime)
    public void BsSocket()throws URISyntaxException ,InterruptedException
    {
        logger.info("比特时代_USDT启动------");
        bsWssMarketHandle=new BsWssMarketHandle(bsurl,String.valueOf(sockettime));
        bsWssMarketHandle.sub(nor_reqparams,response->{
            bslqueue1.add(response.toString());
        });
        Thread.sleep(sockettime-1000);
    }
    @Async
    @Scheduled(fixedRate = sockettime)
    public void BsBtcSocket()throws URISyntaxException ,InterruptedException
    {
        logger.info("比特时代_BTC启动------");
        bsBtcWssMarketHandle=new BsBtcWssMarketHandle(bsurl,String.valueOf(sockettime));
        bsBtcWssMarketHandle.sub(nor_reqparams,response->{
            bslqueue2.add(response.toString());
        });
        Thread.sleep(sockettime-1000);
    }
    @Async
    @Scheduled(fixedRate = sockettime)
    public void BsCncSocket()throws URISyntaxException ,InterruptedException
    {
        logger.info("比特时代_BTC启动------");
        bsCncWssMarketHandle=new BsCncWssMarketHandle(bsurl,String.valueOf(sockettime));
        bsCncWssMarketHandle.sub(nor_reqparams,response->{
            bslqueue3.add(response.toString());
        });
        Thread.sleep(sockettime-1000);
    }
    @Async
    @Scheduled(fixedRate = sockettime)
    public void KbSocket() throws  URISyntaxException ,InterruptedException
    {
        logger.info("库币_组1启动------");
        kbWssMarketHandle=new KbWssMarketHandle(kburl,String.valueOf(sockettime));
        List<String> kb1=kbreqparams.subList(0,99);
        kbWssMarketHandle.sub(kb1,response->{
            //logger.info("1"+response.toString());
            kblqueue1.add(response.toString());
        });
        Thread.sleep(sockettime-1000);
    }
    @Async
    @Scheduled(fixedRate = sockettime)
    public void Kb2Socket() throws  URISyntaxException ,InterruptedException
    {
        logger.info("库币_组2启动------");
        kb2WssMarketHandle=new KbWssMarketHandle(kburl,String.valueOf(sockettime));
        List<String> kb2=kbreqparams.subList(100,199);
        kb2WssMarketHandle.sub(kb2,response->{
            Thread.sleep(1000);
            kblqueue2.add(response.toString());
        });
        Thread.sleep(sockettime-1000);
    }

    @Async
    @Scheduled(fixedRate = sockettime)
    public void Kb3Socket() throws  URISyntaxException ,InterruptedException
    {
        logger.info("库币_组3启动------");
        kb3WssMarketHandle=new KbWssMarketHandle(kburl,String.valueOf(sockettime));
        List<String> kb3=kbreqparams.subList(200,299);
        kb3WssMarketHandle.sub(kb3,response->{
            Thread.sleep(1000);
            kblqueue3.add(response.toString());
        });
        Thread.sleep(sockettime-1000);
    }
    @Async
    @Scheduled(fixedRate = sockettime)
    public void Kb4Socket() throws  URISyntaxException ,InterruptedException
    {
        logger.info("库币_组4启动------");
        kb4WssMarketHandle=new KbWssMarketHandle(kburl,String.valueOf(sockettime));
        List<String> kb4=kbreqparams.subList(300,399);
        kb4WssMarketHandle.sub(kb4,response->{
            Thread.sleep(1000);
            kblqueue4.add(response.toString());
        });
        Thread.sleep(sockettime-1000);
    }
}
