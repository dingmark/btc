package com.example.btc.services.ws.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2021-02-22.
 */
public class DealDepth {
    public static JSONObject getBnDetpth(String message){
        JSONObject jsre=new JSONObject();
        JSONObject jsonObject=JSONObject.parseObject(message);
        String symbol=jsonObject.getString("stream");
        symbol=symbol.substring(0,symbol.indexOf("@"));
        JSONObject jsdata=jsonObject.getJSONObject("data");
        JSONArray jsasks=jsdata.getJSONArray("asks");
        JSONArray jsbids=jsdata.getJSONArray("bids");
        jsre.put("symbol",symbol);
        jsre.put("asks",jsasks);
        jsre.put("bids",jsbids);
        return  jsre;
    }
    public static JSONObject getMcDetpth(String message){
        JSONObject jsre=new JSONObject();
        JSONObject jsonObject=JSONObject.parseObject(message);
        JSONObject jsdata=jsonObject.getJSONObject("data");
        String symbol=jsonObject.getString("symbol");
        JSONArray jsasks=jsdata.getJSONArray("asks");
        JSONArray jsbids=jsdata.getJSONArray("bids");
        jsre.put("symbol",symbol);
        jsre.put("asks",jsasks);
        jsre.put("bids",jsbids);
        return  jsre;
    }
    public static JSONObject getOkDetpth(String message)
    {
        JSONObject jsre=new JSONObject();
        JSONObject jsonObject=JSONObject.parseObject(message);
        JSONArray jsdata=jsonObject.getJSONArray("data");
        JSONObject js=jsdata.getJSONObject(0);
        JSONArray asks=js.getJSONArray("asks");
        JSONArray bids=js.getJSONArray("bids");

        String instrument_id=js.getString("instrument_id");
        jsre.put("symbol",instrument_id);
        jsre.put("asks",asks);
        jsre.put("bids",bids);
        return  jsre;
    }
    public static   JSONObject getHbDetpth(String message)
    {
        JSONObject jsre=new JSONObject();
        JSONObject jsonObject=JSONObject.parseObject(message);
        //JSONArray jsasks=(JSONArray) ((JSONObject)jsonObject.get("tick")).get("asks");
        //JSONArray jsbids=(JSONArray) ((JSONObject)jsonObject.get("tick")).get("bids");
        String bz=jsonObject.getString("ch");
        int begin=bz.indexOf(".");
        int end=bz.indexOf(".",begin+1);
        bz=bz.substring(begin+1,end);
        JSONObject jstick=jsonObject.getJSONObject("tick");
        JSONArray asks= jstick.getJSONArray("asks");
        JSONArray bids= jstick.getJSONArray("bids");
        List<Object> asksdepth=asks.subList(0,asks.size()-5>=0 ? 5:asks.size());
        List<Object>bidsdepth=bids.subList(0,bids.size()-5>=0 ? 5:bids.size());
        jsre.put("symbol",bz);
        jsre.put("asks",asksdepth);
        jsre.put("bids",bidsdepth);
        return  jsre;

    }
    public static  JSONObject getBsDepth(String message)
    {
        JSONObject jsre=new JSONObject();
        JSONObject jsonObject=JSONObject.parseObject(message);
        JSONObject jsdepth=jsonObject.getJSONObject("depth");
        JSONArray asks= jsdepth.getJSONArray("asks");
        JSONArray bids= jsdepth.getJSONArray("bids");
        List<Object> asksobject=asks.subList(0,asks.size()-5>=0 ? 5:asks.size());
        List<Object> bidsobject=bids.subList(0,bids.size()-5>=0 ? 5:bids.size());
        String symbol=jsonObject.getString("symbol");
        jsre.put("symbol",symbol);
        jsre.put("asks",asksobject);
        jsre.put("bids",bidsobject);
        return jsre;
    }
    public static  JSONObject getZbDepth(String message)
    {
        JSONObject jsre=new JSONObject();
        JSONObject jsonObject=JSONObject.parseObject(message);
        String symbol=jsonObject.getString("channel");
        JSONArray asks=jsonObject.getJSONArray("asks");
        JSONArray bids=jsonObject.getJSONArray("bids");
        List<Object> lisasks=asks.subList(asks.size()-5>=0 ? asks.size()-5:0,asks.size());
        List<Object> lisbids=bids.subList(0,bids.size()-5>=0 ? 5:bids.size());
        jsre.put("symbol",symbol);
        jsre.put("asks",lisasks);
        jsre.put("bids",lisbids);
        return  jsre;
    }
}
