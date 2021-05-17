package com.example.btc.services.webSocket;

import com.example.btc.services.ThreadingSocketService.CustomerMultiThreadingSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;

/**
 * Created by Administrator on 2021-03-29.
 */
@Service
public class SocketTime {
    @Value("${sockettime}")
    public String sockettime;
//    @Autowired
//    public void startmuilti(CustomerMultiThreadingSocket ct) throws URISyntaxException, InterruptedException {
//       // ct.McSocket();
//        //ct.HbSocket();
//    }
}
