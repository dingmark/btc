package com.example.btc.services.http.bter;

import io.socket.client.Url;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

@Service
public class bter {
    public float getbteprice(URL url) throws IOException {
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
        return price;
    }
}
