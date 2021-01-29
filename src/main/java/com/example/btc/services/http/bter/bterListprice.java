package com.example.btc.services.http.bter;

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
public class bterListprice {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${berlisturl}")
    private String berlisturl;
    public JSONObject getbteListprice(String para) throws MalformedURLException {
        long startTime=System.currentTimeMillis();
        JSONObject jsOKresult=new JSONObject();
        HttpURLConnection urlConnection=null;
        try {
            Thread.sleep(500);
            URL url=new URL(berlisturl+para+"_usdt?depth=0.1");
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
            logger.info("比特儿加载完成{}-------->", (endTime - startTime) + "ms");
            JSONObject js=JSONObject.parseObject(charinfo.get(0));
            Object bids=js.get("bids");
            Object bid=((JSONArray)bids).get(0);
            jsOKresult.put("name","bter");
            jsOKresult.put("bterbidprice",((JSONArray) bid).get(0));
            jsOKresult.put("bterbidmount",((JSONArray) bid).get(1));

            Object asks=js.get("asks");
            Object ask=((JSONArray) asks).get(49);
            jsOKresult.put("bteraskprice",((JSONArray) ask).get(0));
            jsOKresult.put("bteraskmount",((JSONArray) ask).get(1));
        }
        catch (IOException | InterruptedException e)
        {
            jsOKresult.put("name","bter");
            jsOKresult.put("bterbidprice",0);
            jsOKresult.put("bterbidmount",0);
            jsOKresult.put("bteraskprice",0);
            jsOKresult.put("bteraskmount",0);
            return jsOKresult;
        }
        finally {
            if (urlConnection!=null) {
                urlConnection.disconnect();
            }
        }
        return jsOKresult;
    }
}
