package com.example.btc.services.http.hb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.btc.services.ws.util.JsToNew;
import com.fasterxml.jackson.databind.type.TypeBase;
import com.fasterxml.jackson.databind.type.TypeBindings;
import org.assertj.core.util.diff.Delta;
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

import static javafx.scene.input.KeyCode.T;


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
           // Thread.sleep(500);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
            urlConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:78.0) Gecko/20100101 Firefox/78.0");
           // urlConnection.setConnectTimeout(1000);
           // urlConnection.setReadTimeout(1000);
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
            JSONObject js =JSONObject.parseObject(charinfo.get(0));
            String tick=js.getString("tick");
            JSONObject jstick=JSONObject.parseObject(tick);

            //买一
            Object bids=jstick.get("bids");
            JSONObject jshbresutl=new JSONObject();
            jshbresutl.put("name","hb");
            //jshbresutl.put("bidprice",((JSONArray) bids).get(0));
            //jshbresutl.put("bidmount",((JSONArray) bids).get(1));
            Object asks=jstick.get("asks");
            JSONObject jsbid= JsToNew.jstojs("hb",(JSONArray) bids,"","","bid",1);
            JSONObject jsask= JsToNew.jstojs("hb",(JSONArray) asks,"","","ask",1);
            jshbresutl.putAll(jsbid);
            jshbresutl.putAll(jsask);
            //jshbresutl.put("askprice",((JSONArray) asks).get(0));
            //jshbresutl.put("askmount",((JSONArray) asks).get(1));
            System.out.print("11111");

          /*  String bids=jstick.getString("bids");
            //System.out.println(bids);
            bids=bids.replace("],","]#");
            bids=bids.substring(1,bids.length()-1);
            String[] bidsstring=bids.split("#");
            String bidtmp=bidsstring[0];
            bidtmp=bidtmp.substring(1,bidtmp.length()-1);
            String[] bidfirst=bidtmp.split(",");
            double bidprice =Double.parseDouble(bidfirst[0]);
            double bidmount=Double.parseDouble(bidfirst[1]);
            //卖一
            String asks=jstick.getString("asks");
            asks=asks.replace("],","]#");
            asks=asks.substring(1, asks.length()-1);
            String[] asksstring=asks.split("#");
            String asktmp=asksstring[0];
            asktmp=asktmp.substring(1,asktmp.length()-1);
            String[] askfirst=asktmp.split(",");
            double askprice =Double.parseDouble(askfirst[0]);
            double askmount=Double.parseDouble(askfirst[1]);

            JSONObject jshbresutl=new JSONObject();
            jshbresutl.put("bidprice",bidprice);
            jshbresutl.put("bidmount",bidmount);
            jshbresutl.put("askprice",askprice);
            jshbresutl.put("askmount",askprice);*/

//            //第一组数据为最新交易数据
//            JSONObject js = JSON.parseObject(charinfo.get(0));
//            String data = js.getString("data");
//            data = data.replaceAll("},", "}#");
//            List<String> datalist = Arrays.asList(data.split("#"));
//            //此处头有一个[
//            JSONObject datajs = JSONObject.parseObject(datalist.get(0).substring(1));
//            price = datajs.getFloat("trade_price");
        }
        catch (IOException  e)
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