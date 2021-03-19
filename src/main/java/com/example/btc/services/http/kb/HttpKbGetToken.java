package com.example.btc.services.http.kb;

import com.alibaba.fastjson.JSONObject;
import com.example.btc.services.ws.util.JsToNew;
import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

@Service
public class HttpKbGetToken {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${kbtoken}")
    private String kbtoken;
    public String getkbToken() throws MalformedURLException {
        long startTime=System.currentTimeMillis();
        String token="";
        List <String> symbols=new ArrayList<>();
        //String mcurl=mourl+mckey+"&symbol="+para+"_USDT&limit=10";
        //String hblist=hblisturl+"symbol="+para+"usdt&type=step0&depth=5";
        URL url=new URL(kbtoken);
        HttpURLConnection urlConnection=null;
        try {
            //Thread.sleep(500);
             urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
            urlConnection.setRequestProperty("Content-type", "multipart/form-data; boundary=---------------------------bbbb");
            urlConnection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:78.0) Gecko/20100101 Firefox/78.0");
            //urlConnection.setRequestProperty("Content-Length","298");
            urlConnection.setRequestProperty("Connection","keep-alive");
            urlConnection.setRequestProperty("Host","trade.kucoin.cc");
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setRequestProperty("Referer","https://trade.kucoin.cc/");
            urlConnection.setRequestProperty("Origin","https://trade.kucoin.cc");
            urlConnection.setRequestProperty("TE","Trailers");
            urlConnection.setRequestProperty("Pragma","no-cache");
            urlConnection.setRequestProperty("Cache-Control","no-cache");
            urlConnection.setRequestProperty("Accept-Language","zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
            urlConnection.setRequestMethod("POST");
            // http正文内，因此需要设为true
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            //设置本次连接是否自动重定向
            urlConnection.setInstanceFollowRedirects(true);
            // Post 请求不能使用缓存
            urlConnection.setUseCaches(false);
            urlConnection.setConnectTimeout(60000);
            urlConnection.setReadTimeout(60000);
            urlConnection.connect();
            OutputStream out = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(out, "GBK"));
//            writer.append("-----------------------------bbbb");
//            writer.newLine();
//            writer.append("Content-Disposition: form-data; name=\"protocol\"");
//            writer.newLine();
//            writer.newLine();
//            writer.append("socket.io");
//            writer.newLine();
//            writer.append("-----------------------------bbbb");
//            writer.newLine();
//            writer.append("Content-Disposition: form-data; name=\"source\"");
//            writer.newLine();
//            writer.newLine();
//            writer.append("web");
//            writer.newLine();
//            writer.append("-----------------------------bbbb--");
            writer.write("-----------------------------bbbb\n" +
                    "Content-Disposition: form-data; name=\"protocol\"\n" +
                    "\n" +
                    "socket.io\n" +
                    "-----------------------------bbbb\n" +
                    "Content-Disposition: form-data; name=\"source\"\n" +
                    "\n" +
                    "web\n" +
                    "-----------------------------bbbb--");
            out.flush();
            out.close();
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
            logger.info("库币token数据加载完成用时{}----------->", (endTime - startTime) + "ms");
            JSONObject js =JSONObject.parseObject(charinfo.get(0));
            token= ((JSONObject)js.get("data")).getString("token");
        }
        catch (IOException  e)
        {
            e.printStackTrace();
        }
        finally {
            if (urlConnection!=null) {
                urlConnection.disconnect();
            }
        }
        return token;
    }
}
