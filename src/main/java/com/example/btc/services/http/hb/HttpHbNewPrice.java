package com.example.btc.services.http.hb;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.btc.services.ws.util.JsToNew;
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
import java.util.zip.GZIPInputStream;

@Service
public class HttpHbNewPrice {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${hbnewprice}")
    private String hbnewprice;
    public float gethbNewPrice(String symbol) throws MalformedURLException {
        long startTime=System.currentTimeMillis();
        float price=0;
        //String mcurl=mourl+mckey+"&symbol="+para+"_USDT&limit=10";
        //String hblist=hblisturl+"symbol="+para+"usdt&type=step0&depth=5";
        URL url=new URL(hbnewprice+symbol);
        HttpURLConnection urlConnection=null;
        try {
            //Thread.sleep(500);
             urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
            urlConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:78.0) Gecko/20100101 Firefox/78.0");
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);
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
            logger.info("火币"+symbol+"即时数据加载完成用时{}----------->", (endTime - startTime) + "ms");
            JSONObject js =JSONObject.parseObject(charinfo.get(0));
           price= ((JSONObject)((JSONArray)((JSONObject)js.get("tick")).get("data")).get(0)).getFloat("price");


        }
        catch (IOException  e)
        {
            e.printStackTrace();
        }
        finally {
            if (urlConnection!=null) {
                urlConnection.disconnect();
            }
        }
        return price;
    }
}
