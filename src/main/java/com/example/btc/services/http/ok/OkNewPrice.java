package com.example.btc.services.http.ok;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
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
public class OkNewPrice {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${oknewprice}")
    private String oknewprice;
    public float getOKprice(String para)  {
        long startTime = System.currentTimeMillis();
        float price = 0;
        HttpsURLConnection urlConnection=null;
        try {
           // Thread.sleep(500);
            URL url = new URL(oknewprice + para + "/trades?limit=1");
             urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
            urlConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            urlConnection.setConnectTimeout(60000);
            urlConnection.setReadTimeout(60000);
            InputStream in = urlConnection.getInputStream();
            //GZIPInputStream gZipS = new GZIPInputStream(in);
            InputStreamReader res = new InputStreamReader(in, "GBK");
            BufferedReader reader = new BufferedReader(res);
            String line;
            List<String> charinfo = new ArrayList<String>();
            while ((line = reader.readLine()) != null) {

                charinfo.add(line);
            }

       // System.out.println(charinfo.toString());
        long endTime=System.currentTimeMillis();
        logger.info("OK交易所"+para+"即时数据加载完成{}------->",(endTime-startTime)+"ms");
        //数据转JSON并取最新成交价格
        //JSONObject js= JSON.parseObject(charinfo.toString().substring(1,charinfo.toString().length()-1));
          JSONArray jsonArray=JSONArray.parseArray(charinfo.get(0));
           price= ((JSONObject)jsonArray.get(0)).getFloat("price");
        }
        catch (IOException  e)
        {
            e.printStackTrace();
            logger.info("OK获取实时价格超时");
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
