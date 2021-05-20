package com.example.btc.services.ThreadingSocketService;

import com.example.btc.services.CustomMultiThreadingService.CustomMultiThreadingService;
import com.example.btc.services.http.hb.HttpHbGetCurrencys;
import com.example.btc.services.http.hb.HttpHbGetSymbols;
import com.example.btc.services.http.kb.HttpKbGetSymbols;
import com.example.btc.services.http.kb.HttpKbGetToken;
import com.example.btc.services.http.mocha.HttpMcGetSymbols;
import com.example.btc.services.http.zb.HttpZbGetSymbols;
import com.example.btc.services.ws.handler.*;
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
     private static final int sockettime=300000;
    static List<String> hbreqparams=new ArrayList<>();
    private static List<String> mcreqparams=new ArrayList<>();
    private static List<String> zbreqparams=new ArrayList<>();
    private static List<String> kbreqparams=new ArrayList<>();
    private static List<String> nor_reqparams=new ArrayList<>();
    private static String token="";
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
    @Autowired
    public void setHbsymbols(HttpHbGetSymbols hbsymbols,HttpMcGetSymbols httpMcGetSymbols,HttpHbGetCurrencys hbcurrencys
    ,HttpZbGetSymbols httpZbGetSymbols,HttpKbGetSymbols httpKbGetSymbols) throws MalformedURLException {
        hbreqparams=hbsymbols.gethbSymbols();
        mcreqparams=httpMcGetSymbols.getmcSymbols();
        //OK的币种用火币代替
        nor_reqparams=hbcurrencys.gethbCurrencys();
        zbreqparams=httpZbGetSymbols.getZbSymbols();
        kbreqparams=httpKbGetSymbols.getkbSymbols();
    }
    @Autowired
    public  void  setkbtoken(HttpKbGetToken httpKbGetToken) throws MalformedURLException {
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                token=httpKbGetToken.getkbToken();
                kburl="wss://push-socketio.kucoin.top:6443/socket.io/?token="+token+"&format=json&acceptUserMessage=false&connectId=connect_welcome&EIO=3&transport=websocket";

            }
        }, 0, 120000, TimeUnit.MILLISECONDS);
    }

    public static String  hbresponse;
    public static String mcresponse;
    public static String okresponse1;
    public static String okresponse2;
    public static String okresponse3;

    public static String btresponse1;
    public static String btresponse2;
    public static String btresponse3;

    public static String bnresponse1;
    public static String bnresponse2;
    public static String bnresponse3;

    public static String zbresponse;

    public static String bsresponse1;
    public static String bsresponse2;
    public static String bsresponse3;

    public  static String kbresponse1;
    public  static String kbresponse2;
    public  static String kbresponse3;
    public  static String kbresponse4;

    OkWssMarketHandle okwssMarketHandle;
    OkBtcWssMarketHandle okBtcwssMarketHandle;
    OkEthWssMarketHandle okEthwssMarketHandle;

    BtWssMarketHandle btWssMarketHandle;//=new BtWssMarketHandle(bturl,sockettime);
    BtBtcWssMarketHandle btBtcWssMarketHandle;//=new BtBtcWssMarketHandle(bturl,sockettime);
    BtEthWssMarketHandle btEthWssMarketHandle;

    BnWssMarketHandle bnWssMarketHandle;//=new BnWssMarketHandle(bnurl,sockettime);
    BnBtcWssMarketHandle bnBtcWssMarketHandle;//=new BnBtcWssMarketHandle(bnurl,sockettime);
    BnEthWssMarketHandle bnEthWssMarketHandle;

    ZbWssMarketHandle zbWssMarketHandle;

    BsWssMarketHandle bsWssMarketHandle;//=new BsWssMarketHandle(bsurl,sockettime);
    BsBtcWssMarketHandle bsBtcWssMarketHandle;///=new BsBtcWssMarketHandle(bsurl,sockettime);
    BsCncWssMarketHandle bsCncWssMarketHandle;

    KbWssMarketHandle kbWssMarketHandle;//=new KbWssMarketHandle(kburl,sockettime);
    KbWssMarketHandle kb2WssMarketHandle;//=new KbWssMarketHandle(kburl,sockettime);
    KbWssMarketHandle kb3WssMarketHandle;//=new KbWssMarketHandle(kburl,sockettime);
    KbWssMarketHandle kb4WssMarketHandle;//=

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
        WssMarketHandle wssMarketHandle;
        logger.info("火币启动------");
        wssMarketHandle = new WssMarketHandle(hburl,String.valueOf(sockettime));
        wssMarketHandle.sub(hbreqparams, response -> {
              //  AppointSending(name, response.toString());
           // logger.info("火币交易"+response.toString());
            hbresponse=response.toString();
        });
        Thread.sleep(sockettime-1000);
    }
    @Async
    @Scheduled(fixedRate = sockettime)
    public void McSocket() throws URISyntaxException, InterruptedException {
          String mcurl="wss://contract.mxc.la/ws";
        logger.info("抹茶启动------");
        McWssMarketHandle mcWssMarketHandle;
        mcWssMarketHandle=new McWssMarketHandle(mcurl,String.valueOf(sockettime));
        mcWssMarketHandle.sub(mcreqparams,response->{
            mcresponse=response.toString();
        });
        Thread.sleep(sockettime-1000);
    }
    @Async
    @Scheduled(fixedRate = sockettime)
    public void OkSocket() throws URISyntaxException, InterruptedException {
        logger.info("OK启动------");
        okwssMarketHandle = new OkWssMarketHandle(okurl,String.valueOf(sockettime));
        okBtcwssMarketHandle = new OkBtcWssMarketHandle(okurl,String.valueOf(sockettime));
        okEthwssMarketHandle = new OkEthWssMarketHandle(okurl,String.valueOf(sockettime));
        okwssMarketHandle.sub(nor_reqparams, response -> {
            okresponse1=response.toString();
        });
        okBtcwssMarketHandle.sub(nor_reqparams, response -> {
            okresponse2=response.toString();
        });
        okEthwssMarketHandle.sub(nor_reqparams, response -> {
            okresponse3=response.toString();
        });
        Thread.sleep(sockettime-1000);
    }
    @Async
    @Scheduled(fixedRate = sockettime)
    public void BtSocket() throws URISyntaxException, InterruptedException {
        logger.info("比特儿启动------");
        btWssMarketHandle=new BtWssMarketHandle(bturl,String.valueOf(sockettime));
        btBtcWssMarketHandle=new BtBtcWssMarketHandle(bturl,String.valueOf(sockettime));
        btEthWssMarketHandle=new BtEthWssMarketHandle(bturl,String.valueOf(sockettime));
        btWssMarketHandle.sub(nor_reqparams,response ->
        {
            //logger.info(response.toString());
            btresponse1=response.toString();
        });
        btBtcWssMarketHandle.sub(nor_reqparams,response ->
        {
           // logger.info(response.toString());
            btresponse2=response.toString();
        });
        btEthWssMarketHandle.sub(nor_reqparams,response ->
        {
            //logger.info(response.toString());
            btresponse3=response.toString();
        });
        Thread.sleep(sockettime-1000);
    }

    @Async
    @Scheduled(fixedRate = sockettime)
    public void BnSocket() throws URISyntaxException, InterruptedException {
        logger.info("币安启动------");
        bnWssMarketHandle=new BnWssMarketHandle(bnurl,String.valueOf(sockettime));
        bnBtcWssMarketHandle=new BnBtcWssMarketHandle(bnurl,String.valueOf(sockettime));
        bnEthWssMarketHandle=new BnEthWssMarketHandle(bnurl,String.valueOf(sockettime));
        bnWssMarketHandle.sub(nor_reqparams,response ->{
            bnresponse1=response.toString();
        });
        bnBtcWssMarketHandle.sub(nor_reqparams,response ->{
            bnresponse2=response.toString();
        });
        bnEthWssMarketHandle.sub(nor_reqparams,response ->{
            bnresponse3=response.toString();
        });
        Thread.sleep(sockettime-1000);
    }
    @Async
    @Scheduled(fixedRate = sockettime)
    public void ZbSocket() throws URISyntaxException ,InterruptedException{
        logger.info("中币启动------");
        zbWssMarketHandle=new ZbWssMarketHandle(zburl,String.valueOf(sockettime));
        zbWssMarketHandle.sub(zbreqparams,response->{
            zbresponse=response.toString();
        });
        Thread.sleep(sockettime-1000);
    }
    @Async
    @Scheduled(fixedRate = sockettime)
    public void BsSocket()throws URISyntaxException ,InterruptedException
    {
        logger.info("比特时代启动------");
        bsWssMarketHandle=new BsWssMarketHandle(bsurl,String.valueOf(sockettime));
        bsBtcWssMarketHandle=new BsBtcWssMarketHandle(bsurl,String.valueOf(sockettime));
        bsCncWssMarketHandle=new BsCncWssMarketHandle(bsurl,String.valueOf(sockettime));

        bsWssMarketHandle.sub(nor_reqparams,response->{
            bsresponse1=response.toString();
        });
        bsBtcWssMarketHandle.sub(nor_reqparams,response->{
            bsresponse2=response.toString();
        });
        bsCncWssMarketHandle.sub(nor_reqparams,response->{
            bsresponse3=response.toString();
        });
        Thread.sleep(sockettime-1000);
    }
    @Async
    @Scheduled(fixedRate = sockettime)
    public void KbSocket() throws  URISyntaxException ,InterruptedException
    {
        logger.info("库币启动------");
        kbWssMarketHandle=new KbWssMarketHandle(kburl,String.valueOf(sockettime));
        kb2WssMarketHandle=new KbWssMarketHandle(kburl,String.valueOf(sockettime));
        kb3WssMarketHandle=new KbWssMarketHandle(kburl,String.valueOf(sockettime));
        kb4WssMarketHandle=new KbWssMarketHandle(kburl,String.valueOf(sockettime));
        List<String> kb1=kbreqparams.subList(0,99);
        kbWssMarketHandle.sub(kb1,response->{
            //logger.info("1"+response.toString());
            kbresponse1=response.toString();
        });
        List<String> kb2=kbreqparams.subList(100,199);
        kb2WssMarketHandle.sub(kb2,response->{
            //logger.info("2"+response.toString());
            kbresponse2=response.toString();
        });
        List<String> kb3=kbreqparams.subList(200,299);
        kb3WssMarketHandle.sub(kb3,response->{
            //logger.info("3"+response.toString());
            kbresponse3=response.toString();
        });
        List<String> kb4=kbreqparams.subList(300,399);
        kb4WssMarketHandle.sub(kb4,response->{
            //logger.info("4"+response.toString());
            kbresponse4=response.toString();
        });
        Thread.sleep(sockettime-1000);
    }
}
