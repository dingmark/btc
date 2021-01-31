package com.example.btc.services.ws.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JsToNew {
    public static JSONObject jstojs(String name,JSONArray js,
                                    String pricename,String mountname,
                                    String type,int gate)
    {
        int i=0;
        JSONObject jsreuslt=new JSONObject();
       // jsreuslt.put("name",name);
        switch (gate)
        {
            case 0:
                for (Object jstmp:js)
                {
                    jsreuslt.put(type+"price"+String.valueOf(i),((JSONObject)jstmp).get(pricename));
                    jsreuslt.put(type+"mount"+String.valueOf(i),((JSONObject)jstmp).get(mountname));
                    i++;
                }
                break;
            case 1:
                for(;i<5;i++)
                {
                    jsreuslt.put(type+"price"+String.valueOf(i),0);
                    jsreuslt.put(type+"mount"+String.valueOf(i),0);
                }
                break;
            default:
                break;
        }

        return jsreuslt;
    }
}
