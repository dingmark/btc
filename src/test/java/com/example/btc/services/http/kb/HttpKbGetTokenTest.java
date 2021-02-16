package com.example.btc.services.http.kb;

import com.alibaba.fastjson.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
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

import static org.junit.jupiter.api.Assertions.*;

public class HttpKbGetTokenTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void HttpKbGetToken() throws MalformedURLException {
    long startTime=System.currentTimeMillis();
    List<String> symbols=new ArrayList<>();
    String kbtoken="https://openapi-v2.kucoin.cc/api/v1/bullet-public";
    URL url=new URL(kbtoken);
    HttpsURLConnection urlConnection=null;
    try {
        //Thread.sleep(500);
        urlConnection = (HttpsURLConnection) url.openConnection();
        urlConnection.setRequestProperty("Accept","application/json");
        urlConnection.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
        urlConnection.setRequestProperty("Content-type", "multipart/form-data; boundary=---------------------------9774207792544841460502776695");
        urlConnection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:78.0) Gecko/20100101 Firefox/78.0");
        urlConnection.setRequestMethod("POST");
        urlConnection.setConnectTimeout(6000);
        urlConnection.setReadTimeout(6000);
        InputStream in = urlConnection.getInputStream();
        //GZIPInputStream gZipS = new GZIPInputStream(in);
        InputStreamReader res = new InputStreamReader(in, "GBK");
        BufferedReader reader = new BufferedReader(res);
        String line;
        List<String> charinfo = new ArrayList<String>();
        while ((line = reader.readLine()) != null) {

            charinfo.add(line);
        }
        //System.out.println(charinfo.toString());
        long endTime = System.currentTimeMillis();
        logger.info("库币token数据加载完成用时{}----------->", (endTime - startTime) + "ms");
        JSONObject js =JSONObject.parseObject(charinfo.get(0));

        ((JSONObject) ((JSONArray)js.get("data")).get(0)).getString("token");
        ((JSONObject)js.get("data")).get("token");
        logger.info("11111");

    }
    catch (IOException | JSONException e)
    {
        e.printStackTrace();
    }
    finally {
        if (urlConnection!=null) {
            urlConnection.disconnect();
        }
    }
}
}