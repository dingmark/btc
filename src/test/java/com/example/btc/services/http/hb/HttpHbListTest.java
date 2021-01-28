package com.example.btc.services.http.hb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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



/**
 * Created by Administrator on 2021-01-28.
 */
public  class HttpHbListTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Test
    public void  gethblist() throws MalformedURLException {
        long startTime=System.currentTimeMillis();
        float price=0;
        //String mcurl=mourl+mckey+"&symbol="+para+"_USDT&limit=10";
        String hblist="https://api.huobi.be/market/depth?"+"symbol="+"btc"+"usdt&type=step0&depth=5";
        URL url=new URL(hblist);
        HttpURLConnection urlConnection=null;
        try {
            Thread.sleep(500);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
            urlConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
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
            logger.info("火币List数据加载完成用时{}----------->", (endTime - startTime) + "ms");
            System.out.println(charinfo.toString());
//            //第一组数据为最新交易数据
//            JSONObject js = JSON.parseObject(charinfo.get(0));
//            String data = js.getString("data");
//            data = data.replaceAll("},", "}#");
//            List<String> datalist = Arrays.asList(data.split("#"));
//            //此处头有一个[
//            JSONObject datajs = JSONObject.parseObject(datalist.get(0).substring(1));
//            price = datajs.getFloat("trade_price");
        }
        catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
            logger.info(e.getMessage());
        }
        finally {
            if (urlConnection!=null) {
                urlConnection.disconnect();
            }
        }
    }

}