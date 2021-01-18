package com.example.btc.services.http.bian;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

@Service
public class biAn {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public float getBiAnPrice(URL url) throws IOException {
        long startTime=System.currentTimeMillis();
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
        //System.out.println(charinfo.toString());
        long endTime=System.currentTimeMillis();
        logger.info("币安数据加载完成用时{}----------->",(endTime-startTime)+"ms");
        String tmp=charinfo.toString().substring(2,charinfo.toString().length()-2);
        tmp=tmp.replaceAll("},","}#");
        List<String> listdata=new ArrayList<String>();
        listdata= Arrays.asList(tmp.split("#"));
        //取第一个数据为最近交易数据
        JSONObject js=JSONObject.parseObject(listdata.get(0));
        price=js.getFloatValue("price");
        return price;
    }
}
