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
public class biAnListprice {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${bianlisturl}")
    private String bianlisturl;
    public JSONObject getBiAnListPrice(String para) throws MalformedURLException {
        long startTime=System.currentTimeMillis();
        JSONObject jsbianresult=new JSONObject();
        URL url=new URL(bianlisturl+para+"USDT&limit=5");
        HttpURLConnection urlConnection=null;
        try {
            //Thread.sleep(500);
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
            logger.info("币安数据加载完成用时{}----------->", (endTime - startTime) + "ms");
            JSONObject jsbianlist=JSONObject.parseObject(charinfo.get(0));
            Object bids=jsbianlist.get("bids");
            Object bid=((JSONArray)bids).get(0);
            Object asks=jsbianlist.get("asks");
            Object ask=((JSONArray)asks).get(0);

            jsbianresult.put("name","bian");
            jsbianresult.put("bianbidprice",((JSONArray)bid).get(0));
            jsbianresult.put("bianbidmount",((JSONArray)bid).get(1));
            jsbianresult.put("bianaskprice",((JSONArray)ask).get(0));
            jsbianresult.put("bianaskmount",((JSONArray)ask).get(1));
            //price = js.getFloatValue("price");
        }
        catch (IOException  e)
        {
            jsbianresult.put("name","bian");
            jsbianresult.put("bianbidprice",0);
            jsbianresult.put("bianbidmount",0);
            jsbianresult.put("bianaskprice",0);
            jsbianresult.put("bianaskmount",0);
            return jsbianresult;
        }
        finally {
            if (urlConnection!=null) {
                urlConnection.disconnect();
            }
        }
        return jsbianresult;
    }
}
