package com.example.btc.services.http.bian;

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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.GZIPInputStream;

@Service
public class BiAnNewPrice {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${bianurl}")
    private String bianurl;
    public float getBiAnPrice(String para) throws MalformedURLException {
        long startTime=System.currentTimeMillis();
        float price=0;
        URL url=new URL(bianurl+para+"&limit=1");
        HttpURLConnection urlConnection=null;
        try {
            //Thread.sleep(500);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
            urlConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);
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
            logger.info("币安"+para+"即时数据加载完成用时{}----------->", (endTime - startTime) + "ms");
            JSONArray jsonArray=JSONArray.parseArray( charinfo.get(0));
            price=((JSONObject)jsonArray.get(0)).getFloat("price");
        }
        catch (IOException  e)
        {
            return 0;
        }
        finally {
            if (urlConnection!=null) {
                urlConnection.disconnect();
            }
        }
        return price;
    }
}
