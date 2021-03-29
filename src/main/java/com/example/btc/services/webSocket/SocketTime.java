package com.example.btc.services.webSocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2021-03-29.
 */
@Service
public class SocketTime {
    @Value("${sockettime}")
    public String sockettime;
}
