package com.example.btc.services.http.mocha;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.GZIPInputStream;
import org.junit.Test;


class mochaTest {

    @Test
    void getMcPrice() throws IOException {
        URL url=new URL("https://www.mxc.me/open/api/v1/data/history?market=BTC_USDT");
        float price=0;
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
        JSONObject js = JSON.parseObject(charinfo.get(0));//第一组数据为最新交易数据
        String data=js.getString("data");
        data=data.replaceAll("},","}#");
        List<String>datalist=Arrays.asList(data.split("#"));
        //此处头有一个[
        JSONObject datajs=JSONObject.parseObject(datalist.get(0).substring(1));
        price=datajs.getFloat("tradePrice");
        //        String tmp=charinfo.toString().substring(1,charinfo.toString().length()-1);
//        JSONObject js=JSONObject.parseObject(tmp);
      //  price=js.getFloatValue("last_price");
    }
}