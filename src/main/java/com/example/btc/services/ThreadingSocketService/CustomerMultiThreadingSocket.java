package com.example.btc.services.ThreadingSocketService;

import com.example.btc.services.CustomMultiThreadingService.CustomMultiThreadingService;
import com.example.btc.services.http.hb.HttpHbGetCurrencys;
import com.example.btc.services.http.hb.HttpHbGetSymbols;
import com.example.btc.services.http.mocha.HttpMcGetSymbols;
import com.example.btc.services.webSocket.OnWebSocket;
import com.example.btc.services.ws.handler.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerMultiThreadingSocket {
    private Logger logger = LoggerFactory.getLogger(CustomMultiThreadingService.class);
    int i=0;
     private static final int sockettime=300000;
    static List<String> hbreqparams=new ArrayList<>();
    private static List<String> mcreqparams=new ArrayList<>();
    private static List<String> nor_reqparams=new ArrayList<>();
    @Autowired
    public void setHbsymbols(HttpHbGetSymbols hbsymbols,HttpMcGetSymbols httpMcGetSymbols,HttpHbGetCurrencys hbcurrencys) throws MalformedURLException {
        hbreqparams=hbsymbols.gethbSymbols();
        mcreqparams=httpMcGetSymbols.getmcSymbols();
        //OK的币种用火币代替
        nor_reqparams=hbcurrencys.gethbCurrencys();
    }
    public String hbresponse;
    public String mcresponse;
    public String okresponse1;
    public String okresponse2;
    public String okresponse3;

    public String btresponse1;
    public String btresponse2;
    public String btresponse3;

    OkWssMarketHandle okwssMarketHandle;
    OkBtcWssMarketHandle okBtcwssMarketHandle;
    OkEthWssMarketHandle okEthwssMarketHandle;

    BtWssMarketHandle btWssMarketHandle;//=new BtWssMarketHandle(bturl,sockettime);
    BtBtcWssMarketHandle btBtcWssMarketHandle;//=new BtBtcWssMarketHandle(bturl,sockettime);
    BtEthWssMarketHandle btEthWssMarketHandle;

    private  String okurl="wss://real.coinall.ltd:8443/ws/v3";
    private  String bturl="wss://ws.gateio.ws/v3/";
    @Async
    @Scheduled(fixedRate = sockettime)
    public void HbSocket() throws URISyntaxException, InterruptedException {
        String hburl="wss://api.huobiasia.vip/ws";
        WssMarketHandle wssMarketHandle;
        logger.info("火币启动------"+String.valueOf(i));
        wssMarketHandle = new WssMarketHandle(hburl,"300000");
        wssMarketHandle.sub(hbreqparams, response -> {
              //  AppointSending(name, response.toString());
            //logger.info("火币交易"+response.toString());
            hbresponse=response.toString();
        });
        Thread.sleep(sockettime);
    }
    @Async
    @Scheduled(fixedRate = sockettime)
    public void McSocket() throws URISyntaxException, InterruptedException {
          String mcurl="wss://contract.mxc.la/ws";
        logger.info("抹茶启动------"+String.valueOf(i));
        McWssMarketHandle mcWssMarketHandle;
        mcWssMarketHandle=new McWssMarketHandle(mcurl,String.valueOf(sockettime));
        mcWssMarketHandle.sub(mcreqparams,response->{
            //logger.info("抹茶交易"+response.toString());
            mcresponse=response.toString();
        });
        Thread.sleep(sockettime);
    }
    @Async
    @Scheduled(fixedRate = sockettime)
    public void OkSocket() throws URISyntaxException, InterruptedException {
        okwssMarketHandle = new OkWssMarketHandle(okurl,String.valueOf(sockettime));
        okBtcwssMarketHandle = new OkBtcWssMarketHandle(okurl,String.valueOf(sockettime));
        okEthwssMarketHandle = new OkEthWssMarketHandle(okurl,String.valueOf(sockettime));
        okwssMarketHandle.sub(nor_reqparams, response -> {
            //logger.info(response.toString());
            okresponse1=response.toString();
        });
        okBtcwssMarketHandle.sub(nor_reqparams, response -> {
            //logger.info(response.toString());
            okresponse2=response.toString();
        });
        okEthwssMarketHandle.sub(nor_reqparams, response -> {
            //logger.info(response.toString());
            okresponse3=response.toString();
        });
        Thread.sleep(sockettime);
    }
    @Async
    @Scheduled(fixedRate = sockettime)
    public void BtSocket() throws URISyntaxException, InterruptedException {
        btWssMarketHandle=new BtWssMarketHandle(bturl,String.valueOf(sockettime));
        btBtcWssMarketHandle=new BtBtcWssMarketHandle(bturl,String.valueOf(sockettime));
        btEthWssMarketHandle=new BtEthWssMarketHandle(bturl,String.valueOf(sockettime));
        btWssMarketHandle.sub(nor_reqparams,response ->
        {
            logger.info(response.toString());
            btresponse1=response.toString();
        });
        btBtcWssMarketHandle.sub(nor_reqparams,response ->
        {
            logger.info(response.toString());
            btresponse2=response.toString();
        });
        btEthWssMarketHandle.sub(nor_reqparams,response ->
        {
            logger.info(response.toString());
            btresponse3=response.toString();
        });
        Thread.sleep(sockettime);
    }

}
