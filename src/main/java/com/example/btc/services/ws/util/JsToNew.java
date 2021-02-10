package com.example.btc.services.ws.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class JsToNew {
    public static JSONObject jstojs(String name,JSONArray js,
                                    String pricename,String mountname,
                                    String type,int gate)
    {
        int i=0;
        JSONObject jsreuslt=new JSONObject();
       // jsreuslt.put("name",name);
        if (gate==0) {
            switch (name) {
                case "mocha":
                for (Object jstmp : js) {
                    jsreuslt.put(type + "price" + String.valueOf(i), ((JSONObject) jstmp).get(pricename));
                    jsreuslt.put(type + "mount" + String.valueOf(i), ((JSONObject) jstmp).get(mountname));
                    i++;
                }
                    break;
                case "bian":
                case "ok":
                case "hb":
                    for (Object jstmp : js) {
                        jsreuslt.put(type + "price" + String.valueOf(i), ((JSONArray) jstmp).get(0));
                        jsreuslt.put(type + "mount" + String.valueOf(i), ((JSONArray) jstmp).get(1));
                        i++;
                    }
                    break;
                case "bter":
                    if(type.equals("bid"))
                    {
                        for(Object jstmp : js)
                        {

                            jsreuslt.put(type + "price" + String.valueOf(i), ((JSONArray) jstmp).get(0));
                            jsreuslt.put(type + "mount" + String.valueOf(i), ((JSONArray) jstmp).get(1));
                            i++;
                            if(i==5) {
                                break;
                            }
                        }
                    }
                    if(type.equals("ask"))
                    {
                        for(int k=49;k>44;k--)
                        {
                            Object jstmp=((JSONArray) js).get(k);
                            jsreuslt.put(type + "price" + String.valueOf(i), ((JSONArray)jstmp).get(0));
                            jsreuslt.put(type + "mount" + String.valueOf(i), ((JSONArray) jstmp).get(1));
                            i++;
                            if(k==44) {
                                break;
                            }
                        }
                    }

                    break;
                default:
                    break;
            }
        }
        else {
            for (; i < 5; i++) {
                jsreuslt.put(type + "price" + String.valueOf(i), 0);
                jsreuslt.put(type + "mount" + String.valueOf(i), 0);
            }
        }
        return jsreuslt;
    }
    public static JSONObject jstojs(JSONArray js)
    {
        JSONObject jsresult=new JSONObject();

        return jsresult;
    }
    //参数转化
    public static   String listToJson(final List<String> list) {
        final JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(list);
        return jsonArray.toJSONString();
    }
}
