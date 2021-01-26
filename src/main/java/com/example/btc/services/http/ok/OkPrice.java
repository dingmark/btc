package com.example.btc.services.http.ok;

import com.alibaba.fastjson.JSON;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
//import java.util.SubList;
import java.util.zip.GZIPInputStream;
@Service
public class OkPrice {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${okurl}")
    private String okurl;
    public float getOKprice(String para)  {
        long startTime = System.currentTimeMillis();
        float price = 0;
        HttpURLConnection urlConnection=null;
        try {
            Thread.sleep(500);
            URL url = new URL(okurl + para + "-USDT/ticker");
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
        JSONObject js= JSON.parseObject(charinfo.toString().substring(1,charinfo.toString().length()-1));
        price=Float.parseFloat(js.get("last").toString());
        }
        catch (IOException | InterruptedException e)
        {
            return price=0;
        }
        finally {
            if (urlConnection!=null) {
                urlConnection.disconnect();
            }
        }
        //System.out.println(price);
        return price;
    }
}
