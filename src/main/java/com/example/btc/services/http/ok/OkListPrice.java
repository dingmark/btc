package com.example.btc.services.http.ok;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

//import java.util.SubList;

@Service
public class OkListPrice {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${okurl}")
    private String okurl;
    public JSONObject getOKListprice(String para)  {
        long startTime = System.currentTimeMillis();
        float price = 0;
        HttpURLConnection urlConnection=null;
        JSONObject jsokresult=new JSONObject();
        try {
            Thread.sleep(500);
            URL url = new URL(okurl + para + "-USDT/book?size=5&depth=0.1");
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

       // System.out.println(charinfo.toString());
        long endTime=System.currentTimeMillis();
        logger.info("OK数据加载完成{}------->",(endTime-startTime)+"ms");
        //数据转JSON并取最新成交价格
            JSONObject js= JSON.parseObject(charinfo.get(0));
            Object bids=js.get("bids");
            Object bid=((JSONArray)bids).get(0);

            jsokresult.put("name","ok");
            jsokresult.put("okbidprice",((JSONArray)bid).get(0));
            jsokresult.put("okbidmount",((JSONArray)bid).get(1));

            Object asks=js.get("asks");
            Object ask=((JSONArray)bids).get(0);
            jsokresult.put("okbaskprice",((JSONArray)bid).get(0));
            jsokresult.put("okaskmount",((JSONArray)bid).get(1));
        }
        catch (IOException | InterruptedException e)
        {
            jsokresult.put("name","ok");
            jsokresult.put("okbidprice",0);
            jsokresult.put("okbidmount",0);
            jsokresult.put("okbaskprice",0);
            jsokresult.put("okaskmount",0);
            return jsokresult;
        }
        finally {
            if (urlConnection!=null) {
                urlConnection.disconnect();
            }
        }
        //System.out.println(price);
        return jsokresult;
    }
}
