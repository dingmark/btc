package com.example.btc.services.CustomMultiThreadingService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.btc.services.http.bian.BiAnNewPrice;
import com.example.btc.services.http.bter.BterNewPrice;
import com.example.btc.services.http.hb.HttpHbNewPrice;
import com.example.btc.services.http.kb.HttpKbNewPrice;
import com.example.btc.services.http.mocha.McNewPrice;
import com.example.btc.services.http.ok.OkNewPrice;
import com.example.btc.services.http.zb.HttpZbNewPrice;
import com.example.btc.services.ws.handler.BsNewPriceWssMarketHandle;
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
import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    HttpKbNewPrice httpKbNewPrice;
    @Autowired
    HttpZbNewPrice httpZbNewPrice;

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
    @Scheduled(fixedRate = 20000)
    public void executeAsynBsNewPrice() throws URISyntaxException, InterruptedException {
        String bsurl="wss://api.aex.zone/wsv3";
        BsNewPriceWssMarketHandle bsNewPriceWssMarketHandle=new BsNewPriceWssMarketHandle(bsurl);
        List<String> para=new ArrayList<>();
        bsNewPriceWssMarketHandle.sub(para,response->{
               //response.toString();
               JSONObject js=JSONObject.parseObject(response);
               if(js.getInteger("cmd")==1)
               {
               String type=js.getString("symbol");
                   JSONArray obarray=(JSONArray)js.get("trade");
                   Object price=((JSONArray)obarray.get(0)).get(2);
                   float fprice=Float.parseFloat(price.toString());
               switch (type)
               {
                   case "btc_usdt":
                       hbrealjs.put("bsbtcusdt",fprice);
                       break;
                   case "eth_usdt":
                       hbrealjs.put("bsethusdt",fprice);
                       break;
                   case  "usdt_cnc":
                       hbrealjs.put("bsusdtcnc",fprice);
                       break;
                   default:
               }
               }
        });
        Thread.sleep(20000);
        bsNewPriceWssMarketHandle.close();
    }
    @Scheduled(fixedRate = 500)
    @Async
    public void excuteAsyncHbBtcprice() throws InterruptedException, URISyntaxException, MalformedURLException {
        float hbbtcprice=httpHbNewPrice.gethbNewPrice("btcusdt");
        if(hbbtcprice!=0)
        {
            hbrealjs.put("hbbtcusdt",hbbtcprice);
        }

    }
    @Scheduled(fixedRate = 500)
    @Async
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
    @Async
    @Scheduled(fixedRate = 500)
    public void excuteAsyncKbBtcprice() throws InterruptedException, URISyntaxException, MalformedURLException {
        float kbbtcprice=httpKbNewPrice.getKbNewPrice("BTC-USDT");
        if(kbbtcprice!=0)
        {
            hbrealjs.put("kbbtcusdt",kbbtcprice);
        }
    }
    @Async
    @Scheduled(fixedRate = 500)
    public void excuteAsyncKbEthprice() throws InterruptedException, URISyntaxException, MalformedURLException {
        float Kbethprice=httpKbNewPrice.getKbNewPrice("ETH-USDT");
        if(Kbethprice!=0)
        {
            hbrealjs.put("kbethusdt",Kbethprice);
        }
    }

    @Async
    @Scheduled(fixedRate = 500)
    public void excuteAsyncZbEthprice() throws InterruptedException, URISyntaxException, MalformedURLException {
        float Zbethprice=httpZbNewPrice.getZbNewPrice("eth_usdt");
        if(Zbethprice!=0)
        {
            hbrealjs.put("zbethusdt",Zbethprice);
        }
    }
    @Async
    @Scheduled(fixedRate = 500)
    public void excuteAsyncZbBtcprice() throws InterruptedException, URISyntaxException, MalformedURLException {
        float Zbbtcprice=httpZbNewPrice.getZbNewPrice("btc_usdt");
        if(Zbbtcprice!=0)
        {
            hbrealjs.put("zbbtcusdt",Zbbtcprice);
        }
    }
    @Async
    @Scheduled(fixedRate = 500)
    public void excuteAsyncZbQcPrice() throws InterruptedException, URISyntaxException, MalformedURLException {
        float ZbQcprice=httpZbNewPrice.getZbNewPrice("usdt_qc");
        if(ZbQcprice!=0)
        {
            hbrealjs.put("zbusdtqc",ZbQcprice);
        }
    }
}
