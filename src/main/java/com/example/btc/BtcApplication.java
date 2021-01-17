package com.example.btc;

import com.example.btc.services.MyWebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BtcApplication {

	public static void main(String[] args) {

		SpringApplication.run(BtcApplication.class, args);
	}

	//@Autowired MyWebSocketClient myweb1;
	//myweb1.createClient()
}
