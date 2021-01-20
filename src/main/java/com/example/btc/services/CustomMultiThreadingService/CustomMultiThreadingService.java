package com.example.btc.services.CustomMultiThreadingService;

import com.example.btc.services.baseDaoService.UrlParaService;
import com.example.btc.services.webSocket.OnWebSocket;
import com.example.btc.services.ws.hb.Hbprice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

@Service
@EnableScheduling   // 1.开启定时任务
@EnableAsync        // 2.开启多线程
public class CustomMultiThreadingService {
    private Logger logger = LoggerFactory.getLogger(CustomMultiThreadingService.class);
    @Autowired Hbprice hb;
    @Autowired UrlParaService urlParaService;
   // @Autowired OnWebSocket ws;
    int i=0;
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
    @Scheduled(fixedDelay = 2000)
    public  void excuteAsyncHbThead() throws InterruptedException, URISyntaxException, MalformedURLException {
       //List<String> hbpara=urlParaService.getUrlPara();
      // String hbpricestr ="11111";
//       for(String para : hbpara)
//       {
//           String param="market."+para+"usdt.trade.detail";
//           float hbprice=hb.getHbprice(param);
//            hbpricestr=String.valueOf(hbprice);
//        //这地方加输出要页面的参数
//       }
       // return  hbpricestr;
        i++;
       // ws.AppointSending("hb",String.valueOf(i));
        logger.info(String.valueOf(i));

    }
}
