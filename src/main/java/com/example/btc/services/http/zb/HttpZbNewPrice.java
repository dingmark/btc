package com.example.btc.services.http.zb;

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
import java.util.List;
import java.util.zip.GZIPInputStream;

@Service
public class HttpZbNewPrice {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${zbnewprice}")
    private String zbnewprice;
    public float getZbNewPrice(String symbol) throws MalformedURLException {
        long startTime=System.currentTimeMillis();
        float price=0;
        //String mcurl=mourl+mckey+"&symbol="+para+"_USDT&limit=10";
        //String hblist=hblisturl+"symbol="+para+"usdt&type=step0&depth=5";
        URL url=new URL(zbnewprice+symbol);
        HttpURLConnection urlConnection=null;
        try {
            //Thread.sleep(500);
             urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
            urlConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:78.0) Gecko/20100101 Firefox/78.0");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
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
            //logger.info("中币"+symbol+"即时数据加载完成用时{}----------->", (endTime - startTime) + "ms");
            JSONObject js =JSONObject.parseObject(charinfo.get(0));
            price=((JSONObject)js.get("ticker")).getFloat("last");

        }
        catch (IOException  e)
        {
            //e.printStackTrace();
            logger.info("中币获取实时价格超时");
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
