package com.example.btc.services.http.mocha;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.btc.services.ws.util.JsToNew;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

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
import org.junit.Test;
import org.springframework.messaging.converter.JsonbMessageConverter;

public class mochaListTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String molisturl="https://www.mxc.me/open/api/v2/market/depth?symbol=";
    @Test
    public void getMcListPrice() throws MalformedURLException {
        long startTime = System.currentTimeMillis();
        float price = 0;
        String mcurl = molisturl + "BTC" + "_USDT&depth=5";
        URL url = new URL(mcurl);
        JSONObject jsmcresult=new JSONObject();
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
            urlConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            urlConnection.setConnectTimeout(1000);
            urlConnection.setReadTimeout(1000);
            InputStream in = urlConnection.getInputStream();
            GZIPInputStream gZipS = new GZIPInputStream(in);
            InputStreamReader res = new InputStreamReader(gZipS, "GBK");
            BufferedReader reader = new BufferedReader(res);
            String line;
            List<String> charinfo = new ArrayList<String>();
            while ((line = reader.readLine()) != null) {

                charinfo.add(line);
            }
            //System.out.println(charinfo.toString());
            long endTime = System.currentTimeMillis();
            logger.info("抹茶数据加载完成用时{}----------->", (endTime - startTime) + "ms");

            JSONObject jsmclist=JSONObject.parseObject(charinfo.get(0));
            Object bids=jsmclist.getJSONObject("data").getJSONArray("bids");
            Object asks=jsmclist.getJSONObject("data").getJSONArray("asks");
            jsmcresult.put("name","mocha");
            JSONObject jsbids=JsToNew.jstojs("mocha",(JSONArray)bids,"price","quantity","bid",0);
            JSONObject jsasks=JsToNew.jstojs("mocha",(JSONArray)asks,"price","quantity","ask",0);
            jsmcresult.putAll(jsbids);
            jsmcresult.putAll(jsasks);

        }//第一组数据为最新交易数据
            catch(IOException  e)
            {
                jsmcresult.put("name","mocha");
                JSONObject jsbids=JsToNew.jstojs("mocha",null,"price","quantity","bid",1);
                JSONObject jsasks=JsToNew.jstojs("mocha",null,"price","quantity","ask",1);
                jsmcresult.putAll(jsbids);
                jsmcresult.putAll(jsasks);
                System.out.println(jsmcresult.toJSONString());
            }
            finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

    }
}