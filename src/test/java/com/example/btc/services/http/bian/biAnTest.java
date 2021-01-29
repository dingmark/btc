package com.example.btc.services.http.bian;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import netscape.javascript.JSObject;
import org.junit.Test;
public  class biAnTest {

    @Test
    public  void getBiAnPrice() throws IOException {
        float price=0;
        URL url=new URL("https://api.yshyqxx.com/api/v3/depth?symbol=BTCUSDT&limit=5");
                //"https://api.binancezh.cc/api/v3/trades?symbol=BTCUSDT&limit=5");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestProperty("Accept-Encoding","gzip, deflate");
        urlConnection.setRequestProperty("Content-type","application/x-www-form-urlencoded");
        InputStream in = urlConnection.getInputStream();
        GZIPInputStream gZipS=new GZIPInputStream(in);
        InputStreamReader res = new InputStreamReader(gZipS,"GBK");
        BufferedReader reader=new BufferedReader(res);
        String line;
        List<String> charinfo=new ArrayList<String>();
        while ((line = reader.readLine()) != null) {

            charinfo.add(line);
        }
        JSONObject jsbianlist=JSONObject.parseObject(charinfo.get(0));
        Object bids=jsbianlist.get("bids");
        Object bid=((JSONArray)bids).get(0);
        Object asks=jsbianlist.get("asks");
        Object ask=((JSONArray)asks).get(0);
        JSONObject jsbianresult=new JSONObject();
        jsbianresult.put("name","bian");
        jsbianresult.put("bianbidprice",((JSONArray)bid).get(0));
        jsbianresult.put("bianbidmount",((JSONArray)bid).get(1));
        jsbianresult.put("bianaskprice",((JSONArray)ask).get(0));
        jsbianresult.put("bianaskmount",((JSONArray)ask).get(1));
        System.out.println(charinfo.toString());
    }
}