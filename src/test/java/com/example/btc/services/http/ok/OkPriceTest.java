package com.example.btc.services.http.ok;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.btc.services.ws.util.JsToNew;
import org.junit.Test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;


public class OkPriceTest {
    @Test
    public void getOKprice() throws IOException {
        URL url=new URL("https://www.okexcn.com/api/spot/v3/instruments/BTC-USDT/book?size=5&depth=0.1");
        float price=0;
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
        System.out.println(charinfo.toString());
        //数据转JSON并取最新成交价格
        JSONObject js= JSON.parseObject(charinfo.get(0));
        Object bids=js.get("bids");
      //  Object bid=((JSONArray)bids).get(0);
        JSONObject jsokresult=new JSONObject();
        jsokresult.put("name","ok");
//        jsokresult.put("okbidprice",((JSONArray)bid).get(0));
//        jsokresult.put("okbidmount",((JSONArray)bid).get(1));

        Object asks=js.get("asks");
//        Object ask=((JSONArray)bids).get(0);
//        jsokresult.put("okbaskprice",((JSONArray)bid).get(0));
//        jsokresult.put("okaskmount",((JSONArray)bid).get(1));
        jsokresult.put("name","ok");
        JSONObject jsbids= JsToNew.jstojs("ok",(JSONArray)bids,"price","quantity","bid",0);
        JSONObject jsasks=JsToNew.jstojs("ok",(JSONArray)asks,"price","quantity","ask",0);
        jsokresult.putAll(jsbids);
        jsokresult.putAll(jsasks);

        System.out.print("111");
        //price=Float.parseFloat(js.get("last").toString());
        //System.out.println(price);
        //return price;
    }
}