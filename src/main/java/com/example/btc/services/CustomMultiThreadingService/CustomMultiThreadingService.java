package com.example.btc.services.CustomMultiThreadingService;
import com.alibaba.fastjson.JSONObject;
import com.example.btc.services.http.bian.BiAnNewPrice;
import com.example.btc.services.http.bter.BterNewPrice;
import com.example.btc.services.http.hb.HttpHbNewPrice;
import com.example.btc.services.http.mocha.McNewPrice;
import com.example.btc.services.http.ok.OkNewPrice;
import com.example.btc.services.ws.hb.Hbprice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

@Service
@EnableScheduling
public class CustomMultiThreadingService {
    private Logger logger = LoggerFactory.getLogger(CustomMultiThreadingService.class);
    @Autowired
    HttpHbNewPrice httpHbNewPrice;
    @Autowired
    OkNewPrice okNewPrice;
    @Autowired
    BterNewPrice bterNewPrice;
    @Autowired
    McNewPrice mcNewPrice;
    @Autowired
    BiAnNewPrice biAnNewPrice;
    public JSONObject hbrealjs=new JSONObject();

    /**
     * @Description:通过@Async注解表明该方法是一个异步方法，
     * 如果注解在类级别上，则表明该类所有的方法都是异步方法，而这里的方法自动被注入使用ThreadPoolTaskExecutor作为TaskExecutor
     * @Title: executeAysncTask1
     * @Date: 2018年9月21日 下午2:54:32
     * @Author: OnlyMate
     * @Throws
     * @param i
     */
    @Async
    public void executeAysncTask1(Integer i){
        logger.info("CustomMultiThreadingService ==> executeAysncTask1 method: 执行异步任务{} ", i);
    }

    /**
     * @Description:通过@Async注解表明该方法是一个异步方法，
     * 如果注解在类级别上，则表明该类所有的方法都是异步方法，而这里的方法自动被注入使用ThreadPoolTaskExecutor作为TaskExecutor
     * @Title: executeAsyncTask2
     * @Date: 2018年9月21日 下午2:55:04
     * @Author: OnlyMate
     * @Throws
     * @param i
     */
    @Async
    public void executeAsyncTask2(Integer i){
        logger.info("CustomMultiThreadingService ==> executeAsyncTask2 method: 执行异步任务{} ", i);
    }
    @Async
    @Scheduled(fixedRate = 500)
    public void excuteAsyncHbBtcprice() throws InterruptedException, URISyntaxException, MalformedURLException {
        float hbbtcprice=httpHbNewPrice.gethbNewPrice("btcusdt");
        if(hbbtcprice!=0)
        {
            hbrealjs.put("hbbtcustd",hbbtcprice);
        }

    }
    @Async
    @Scheduled(fixedRate = 500)
    public void excuteAsyncHbEthprice() throws InterruptedException, URISyntaxException, MalformedURLException {
        float hbethprice=httpHbNewPrice.gethbNewPrice("ethusdt");
        if(hbethprice!=0)
        {
            hbrealjs.put("hbethusdt",hbethprice);
        }

    }
    @Async
    @Scheduled(fixedRate = 500)
    public void excuteAsyncOkBtcprice() throws InterruptedException, URISyntaxException, MalformedURLException {
        float okbtcprice=okNewPrice.getOKprice("BTC-USDT");
        if(okbtcprice!=0)
        {
            hbrealjs.put("okbtcusdt",okbtcprice);
        }

    }
    @Async
    @Scheduled(fixedRate = 500)
    public void excuteAsyncOkEthprice() throws InterruptedException, URISyntaxException, MalformedURLException {
        float okethprice=okNewPrice.getOKprice("ETH-USDT");
        if(okethprice!=0)
        {
            hbrealjs.put("okethusdt",okethprice);
        }
    }
    @Async
    @Scheduled(fixedRate = 500)
    public void excuteAsyncBterBtcprice() throws InterruptedException, URISyntaxException, MalformedURLException {
        float okethprice=bterNewPrice.getbterprice("btc_usdt");
        if(okethprice!=0)
        {
            hbrealjs.put("bterbtcusdt",okethprice);
        }
    }
    @Async
    @Scheduled(fixedRate = 500)
    public void excuteAsyncBterEthprice() throws InterruptedException, URISyntaxException, MalformedURLException {
        float okethprice=bterNewPrice.getbterprice("eth_usdt");
        if(okethprice!=0)
        {
            hbrealjs.put("bterethusdt",okethprice);
        }
    }

    @Async
    @Scheduled(fixedRate = 500)
    public void excuteAsyncMcBtcprice() throws InterruptedException, URISyntaxException, MalformedURLException {
        float mcbtcprice=mcNewPrice.getMcPrice("BTC_USDT");
        if(mcbtcprice!=0)
        {
            hbrealjs.put("mcbtcusdt",mcbtcprice);
        }
    }
    @Async
    @Scheduled(fixedRate = 500)
    public void excuteAsyncMcEthprice() throws InterruptedException, URISyntaxException, MalformedURLException {
        float mcethprice=mcNewPrice.getMcPrice("ETH_USDT");
        if(mcethprice!=0)
        {
            hbrealjs.put("mcethusdt",mcethprice);
        }
    }
    @Async
    @Scheduled(fixedRate = 500)
    public void excuteAsyncBnEthprice() throws InterruptedException, URISyntaxException, MalformedURLException {
        float bnethprice=biAnNewPrice.getBiAnPrice("ETHUSDT");
        if(bnethprice!=0)
        {
            hbrealjs.put("bnethusdt",bnethprice);
        }
    }
    @Async
    @Scheduled(fixedRate = 500)
    public void excuteAsyncBnBtcprice() throws InterruptedException, URISyntaxException, MalformedURLException {
        float bnbtcprice=biAnNewPrice.getBiAnPrice("BTCUSDT");
        if(bnbtcprice!=0)
        {
            hbrealjs.put("bnbtcusdt",bnbtcprice);
        }
    }
}
