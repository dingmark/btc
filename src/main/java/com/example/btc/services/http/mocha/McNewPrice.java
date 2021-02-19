package com.example.btc.services.http.mocha;

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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.GZIPInputStream;

@Service
public class McNewPrice {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${monewprice}")
    private String mourl;
    @Value("${mckey}")
    private String mckey;
    public float getMcPrice(String para) throws MalformedURLException {
        long startTime=System.currentTimeMillis();
        float price=0;
        //String mcurl=mourl+mckey+"&symbol="+para+"_USDT&limit=10";
        String mcurl=mourl+para;
        URL url=new URL(mcurl);
        HttpURLConnection urlConnection=null;
        try {
           // Thread.sleep(500);
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
            long endTime = System.currentTimeMillis();
            logger.info("抹茶"+para+"实时数据加载完成用时{}----------->", (endTime - startTime) + "ms");
            JSONObject js = JSON.parseObject(charinfo.get(0));
            JSONObject jsdata=(JSONObject)((JSONArray)JSONArray.parse(js.get("data").toString())).get(0);
            price=jsdata.getFloat("last");
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
