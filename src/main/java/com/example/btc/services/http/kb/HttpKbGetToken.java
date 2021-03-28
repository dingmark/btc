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
    private  static String BOUNDARY ="bbbb";
    private final static String PREFIX = "-----------------------------";// 必须存在
    private  final static String ENDFIX="--";
    private final static String LINE_END = "\r\n";

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
            urlConnection.setRequestProperty("Content-type", "multipart/form-data; boundary="+PREFIX.substring(0,27)+BOUNDARY);
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

            //OutputStream out = urlConnection.getOutputStream();
            DataOutputStream out = new DataOutputStream (urlConnection.getOutputStream());
            //urlConnection.
            StringBuilder writer = new StringBuilder();
//            BufferedWriter writer = new BufferedWriter(
//                    new OutputStreamWriter(out, "GBK"));
            writer.append(PREFIX).append(BOUNDARY).append(LINE_END);
            writer.append("Content-Disposition: form-data; name=\"protocol\"").append(LINE_END);
            writer.append(LINE_END);
            writer.append("socket.io").append(LINE_END);
            writer.append(PREFIX).append(BOUNDARY).append(LINE_END);
            writer.append("Content-Disposition: form-data; name=\"source\"").append(LINE_END);
            writer.append(LINE_END);
            writer.append("web").append(LINE_END);
            writer.append(PREFIX).append(BOUNDARY).append(ENDFIX).append(LINE_END);
            byte[] data=writer.toString().getBytes("GBK");
            out.write(data);
//            writer.write("-----------------------------bbbb\r\n" +
//                    "Content-Disposition: form-data; name=\"protocol\"\r\n" +
//                    "\r\n" +
//                    "socket.io\r\n" +
//                    "-----------------------------bbbb\r\n" +
//                    "Content-Disposition: form-data; name=\"source\"\r\n" +
//                    "\r\n" +
//                    "web\r\n" +
//                    "-----------------------------bbbb--");
            out.flush();//发送数据
            //urlConnection.connect();
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
    public String getkbToken_old() throws MalformedURLException {
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
            urlConnection.setRequestProperty("Content-type", "multipart/form-data; boundary="+PREFIX.substring(0,27)+BOUNDARY);
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
