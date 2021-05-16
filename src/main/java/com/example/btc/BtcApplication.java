package com.example.btc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
@SpringBootApplication(scanBasePackages={"com.example.btc"})
public class BtcApplication {

	public static void main(String[] args) {
		SpringApplication.run(BtcApplication.class, args);
	}

	//@Autowired MyWebSocketClient myweb1;
	//myweb1.createClient()
}
