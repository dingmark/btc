package com.example.btc.services.http.bter;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.btc.services.ws.util.JsToNew;
import org.junit.Test;


public  class bterTest {

    @Test
   public void getbteprice() throws IOException {
        float price=0;
        URL url=new URL("https://data.gateapi.io/api2/1/orderBook/btc_usdt?depth=0.1");
               // "https://fx-api.gateio.ws/api/v4/futures/btc/contracts/BTC_USD");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestProperty("Accept-Encoding","gzip, deflate");
        urlConnection.setRequestProperty("Content-type","application/x-www-form-urlencoded");
        InputStream in = urlConnection.getInputStream();
        GZIPInputStream gZipS=new GZIPInputStream(in);
        InputStreamReader res = new InputStreamReader(gZipS,"GBK");
        BufferedReader reader=new BufferedReader(res);
        String line;
        List<String> charinfo=new ArrayList<String>();
        while ((line = reader.readLine()) != null) {

            charinfo.add(line);
        }
        System.out.println(charinfo.toString());
        JSONObject jsBteresult=new JSONObject();
        JSONObject js=JSONObject.parseObject(charinfo.get(0));
        Object bids=js.get("bids");
        //Object bid=((JSONArray)bids).get(0);

        //jsOKresult.put("bterbidprice",((JSONArray) bid).get(0));
        //jsOKresult.put("bterbidmount",((JSONArray) bid).get(1));

        Object asks=js.get("asks");
        //Object ask=((JSONArray) asks).get(49);
        //jsOKresult.put("bteraskprice",((JSONArray) ask).get(0));
        //jsOKresult.put("bteraskmount",((JSONArray) ask).get(1));
         //jsBteresult.put("name","bter");
         JSONObject jsbid= JsToNew.jstojs("bter",(JSONArray) bids,"","","bid",0);
         JSONObject jsask= JsToNew.jstojs("bter",(JSONArray) asks,"","","ask",0);
         JSONObject jstmp=new JSONObject();
         jstmp.putAll(jsbid);
         jstmp.putAll(jsask);
         jsBteresult.put("bter",jstmp);

         System.out.print("11111");

    }
}