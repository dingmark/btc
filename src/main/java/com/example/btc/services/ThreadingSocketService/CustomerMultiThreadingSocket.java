package com.example.btc.services.ThreadingSocketService;

import com.example.btc.services.CustomMultiThreadingService.CustomMultiThreadingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class CustomerMultiThreadingSocket {
    private Logger logger = LoggerFactory.getLogger(CustomMultiThreadingService.class);
    int i=0;
    @Async
    public void HbSocket()
    {
        i++;
        logger.info("火币启动------"+String.valueOf(i));
    }
    @Async
    public void McSocket()
    {
        i++;
        logger.info("抹茶启动------"+String.valueOf(i));
    }
}
