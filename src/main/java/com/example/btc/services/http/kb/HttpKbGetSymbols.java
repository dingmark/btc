package com.example.btc.services.http.kb;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.btc.services.ws.util.JsToNew;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

@Service
public class HttpKbGetSymbols {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${kbsymbols}")
    private String kbsymbols;
    public List<String> gethbSymbols() throws MalformedURLException {
        long startTime=System.currentTimeMillis();
        List <String> symbols=new ArrayList<>();
        //String mcurl=mourl+mckey+"&symbol="+para+"_USDT&limit=10";
        //String hblist=hblisturl+"symbol="+para+"usdt&type=step0&depth=5";
        URL url=new URL(kbsymbols);
        HttpsURLConnection urlConnectionsy=null;
        try {
            //Thread.sleep(500);
            urlConnectionsy = (HttpsURLConnection) url.openConnection();
            urlConnectionsy.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
           // urlConnectionsy.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            urlConnectionsy.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:78.0) Gecko/20100101 Firefox/78.0");
            urlConnectionsy.setConnectTimeout(60000);
            urlConnectionsy.setReadTimeout(60000);
            InputStream in = urlConnectionsy.getInputStream();
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
            logger.info("库币List数据加载完成用时{}----------->", (endTime - startTime) + "ms");
            JSONObject js =JSONObject.parseObject(charinfo.get(0));

            if(js.getString("code").equals("200000")) {
                //symbols=  JsToNew.castList(js.get("data"), String.class);
                JSONArray jsarry=(JSONArray)js.get("data");
                for(int i=0;i<jsarry.size();i++)
                {
                    String symbol=( (JSONObject)jsarry.get(i)).getString("symbol");
                    symbols.add(symbol);
                }
            }

        }
        catch (IOException  e)
        {
            e.printStackTrace();
        }
        finally {
            if (urlConnectionsy!=null) {
                urlConnectionsy.disconnect();
            }
        }
        return symbols;
    }
}
