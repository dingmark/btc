package com.example.btc;

import com.example.btc.services.CustomMultiThreadingService.CustomMultiThreadingService;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONObject;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

public class WssMCMarketTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    //private String URL = "";
    public static final String ACCESS_KEY = "";
    public static final String SECRET_KEY = "";
    public static final String HOST = "https://www.mxc.me/open";

    public static final String MARKET_LIST = HOST + "/api/v1/data/markets";

    public static final String MARKET_DEPTH = HOST + "/api/v1/data/depth";
    public static final String BID_HISTORY = HOST + "/api/v1/data/history";
    public static final String MARKET_INFO = HOST + "/api/v1/data/markets_info";
    public static final String TIME = HOST + "/api/v1/data/time";
    public static final String ACCOUNT_INFO = HOST + "/api/v1/private/account/info";
    public static final String CURR_ORDER_LIST = HOST + "/api/v1/private/current/orders";
    public static final String ORDER_INFO = HOST + "/api/v1/private/order";
    public static final String CREATE_ORDER = HOST + "/api/v1/private/order";
    public static final String CANCEL_ORDER = HOST + "/api/v1/private/order";
    public static final String ORDER_LIST = HOST + "/api/v1/private/orders";
    public static final String TICKET = HOST + "/api/v1/data/ticker";
    @Test
    public void testmc(){
         String url ="wss://wbs.mxc.me/socket.io/?EIO=3&transport=websocket";//"https://www.mxc.me";
         //"https://wbs.mxc.me/socket.io/?EIO=3&transport=websocket";//
        try {
            IO.Options options = new IO.Options();
            options.transports = new String[]{"websocket","polling"};
            //失败重试次数
            options.reconnectionAttempts = 500;
            //失败重连的时间间隔
            options.reconnectionDelay = 6000;
            //连接超时时间(ms)
            options.timeout = 6000;
            URL newurl=new URL(url);
            final Socket socket = IO.socket(url, options);

            socket.on(Socket.EVENT_CONNECTING, objects -> System.out.println("client: " + "连接中"));
            socket.on(Socket.EVENT_CONNECT_TIMEOUT, objects -> System.out.println("client: " + "连接超时"));
            socket.on(Socket.EVENT_CONNECT_ERROR, objects -> System.out.println("client: " + "连接失败"));

            JSONObject jsonObject = new JSONObject();

            socket.on("message",objects -> System.out.println(objects.toString()));

            // {"op":"subscribe","args":["spot/ticker:BTC-USDT","spot/mark_price:BTC-USDT","spot/optimized_depth:BTC-USDT","spot/candle300s:BTC-USDT"]}
            //监听自定义msg事件,获取币种信息
            socket.on("rs.symbol", objects -> System.out.println("client: 收到msg->" + Arrays.toString(objects)));

            socket.emit("get.symbol", jsonObject);

            //监听交易信息
            socket.on("push.symbol", objects -> System.out.println("client: 收到msg->" + Arrays.toString(objects)));
            socket.emit("sub.symbol",jsonObject);
            socket.connect();
            Thread.sleep(Integer.MAX_VALUE);
            System.out.println("1111");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @Test
    public void getHistory() throws Exception {
        URL url = new URL(BID_HISTORY + "?market=BTC_USDT");
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.96 Safari/537.36");
        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        bufferedReader.lines().forEach(e -> System.out.print(e));
    }
    @Test
    public void testthread() throws InterruptedException, MalformedURLException, URISyntaxException {

        CustomMultiThreadingService cs=new CustomMultiThreadingService();
//        for (int i=0;i<10;i++){
//            cs.executeAysncTask1(i);
//            cs.executeAsyncTask2(i);
//        }
        //cs.excuteAsyncHbThead();
        //Thread.sleep(Integer.MAX_VALUE);

    }

}
