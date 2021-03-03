package com.example.btc.services.ws.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2021-02-22.
 */
public class DealDepth {
    static List<JSONObject> btlist = new ArrayList<>();

    public static JSONObject getBnDetpth(String message) {
        JSONObject jsre = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(message);
        String symbol = jsonObject.getString("stream");
        symbol = symbol.substring(0, symbol.indexOf("@"));
        JSONObject jsdata = jsonObject.getJSONObject("data");
        JSONArray jsasks = jsdata.getJSONArray("asks");
        JSONArray jsbids = jsdata.getJSONArray("bids");
        jsre.put("symbol", symbol);
        jsre.put("asks", jsasks);
        jsre.put("bids", jsbids);
        return jsre;
    }

    public static JSONObject getMcDetpth(String message) {
        JSONObject jsre = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(message);
        JSONObject jsdata = jsonObject.getJSONObject("data");
        String symbol = jsonObject.getString("symbol");
        JSONArray jsasks = jsdata.getJSONArray("asks");
        JSONArray jsbids = jsdata.getJSONArray("bids");
        jsre.put("symbol", symbol);
        jsre.put("asks", jsasks);
        jsre.put("bids", jsbids);
        return jsre;
    }

    public static JSONObject getOkDetpth(String message) {
        JSONObject jsre = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(message);
        JSONArray jsdata = jsonObject.getJSONArray("data");
        JSONObject js = jsdata.getJSONObject(0);
        JSONArray asks = js.getJSONArray("asks");
        JSONArray bids = js.getJSONArray("bids");

        String instrument_id = js.getString("instrument_id");
        jsre.put("symbol", instrument_id);
        jsre.put("asks", asks);
        jsre.put("bids", bids);
        return jsre;
    }

    public static JSONObject getHbDetpth(String message) {
        JSONObject jsre = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(message);
        //JSONArray jsasks=(JSONArray) ((JSONObject)jsonObject.get("tick")).get("asks");
        //JSONArray jsbids=(JSONArray) ((JSONObject)jsonObject.get("tick")).get("bids");
        String bz = jsonObject.getString("ch");
        int begin = bz.indexOf(".");
        int end = bz.indexOf(".", begin + 1);
        bz = bz.substring(begin + 1, end);
        JSONObject jstick = jsonObject.getJSONObject("tick");
        JSONArray asks = jstick.getJSONArray("asks");
        JSONArray bids = jstick.getJSONArray("bids");
        List<Object> asksdepth = asks.subList(0, asks.size() - 5 >= 0 ? 5 : asks.size());
        List<Object> bidsdepth = bids.subList(0, bids.size() - 5 >= 0 ? 5 : bids.size());
        jsre.put("symbol", bz);
        jsre.put("asks", asksdepth);
        jsre.put("bids", bidsdepth);
        return jsre;

    }

    public static JSONObject getBsDepth(String message) {
        JSONObject jsre = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(message);
        JSONObject jsdepth = jsonObject.getJSONObject("depth");
        JSONArray asks = jsdepth.getJSONArray("asks");
        JSONArray bids = jsdepth.getJSONArray("bids");
        List<Object> asksobject = asks.subList(0, asks.size() - 5 >= 0 ? 5 : asks.size());
        List<Object> bidsobject = bids.subList(0, bids.size() - 5 >= 0 ? 5 : bids.size());
        String symbol = jsonObject.getString("symbol");
        jsre.put("symbol", symbol);
        jsre.put("asks", asksobject);
        jsre.put("bids", bidsobject);
        return jsre;
    }

    public static JSONObject getZbDepth(String message) {
        JSONObject jsre = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(message);
        String symbol = jsonObject.getString("channel");
        JSONArray asks = jsonObject.getJSONArray("asks");
        JSONArray bids = jsonObject.getJSONArray("bids");
        List<Object> lisasks = asks.subList(asks.size() - 5 >= 0 ? asks.size() - 5 : 0, asks.size());
        List<Object> lisbids = bids.subList(0, bids.size() - 5 >= 0 ? 5 : bids.size());
        jsre.put("symbol", symbol);
        jsre.put("asks", lisasks);
        jsre.put("bids", lisbids);
        return jsre;
    }

    public static JSONObject getKbDepth(String message) {
        JSONObject jsre = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(message);
        String symbol = jsonObject.getString("topic");
        int begin = symbol.indexOf(":");
        symbol = symbol.substring(begin + 1, symbol.length());
        JSONObject jsdata = jsonObject.getJSONObject("data");
        JSONArray asks = jsdata.getJSONArray("asks");
        JSONArray bids = jsdata.getJSONArray("bids");
        jsre.put("symbol", symbol);
        jsre.put("asks", asks);
        jsre.put("bids", bids);
        return jsre;
    }

    public static JSONObject getBtDepth(String message) {
        JSONObject jsre = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(message);
        JSONArray params = jsonObject.getJSONArray("params");
        JSONObject jsdata = params.getJSONObject(1);
        String symbol = params.getString(2);
        JSONArray asks = jsdata.getJSONArray("asks");
        JSONArray bids = jsdata.getJSONArray("bids");
        List<Object> asksobject = asks.subList(0, asks.size() - 5 >= 0 ? 5 : asks.size());
        List<Object> bidsobject = bids.subList(0, bids.size() - 5 >= 0 ? 5 : bids.size());
        jsre.put("symbol", symbol);
        jsre.put("asks", asksobject);
        jsre.put("bids", bidsobject);
        return jsre;
    }
    public static JSONObject getBtDepth(JSONObject object) {
        JSONObject jsre = new JSONObject();
        String symbol=object.getString("symbol");
        JSONArray asks = object.getJSONArray("asks");
        JSONArray bids = object.getJSONArray("bids");
        List<Object> asksobject = asks.subList(0, asks.size() - 5 >= 0 ? 5 : asks.size());
        List<Object> bidsobject = bids.subList(0, bids.size() - 5 >= 0 ? 5 : bids.size());
        jsre.put("symbol", symbol);
        jsre.put("asks", asksobject);
        jsre.put("bids", bidsobject);
        return jsre;
    }
    public static JSONObject getBtDepthUpdate(JSONObject jsold,String message)
    {
        JSONObject jsre=new JSONObject();
        JSONArray asksre=new JSONArray();
        JSONArray bidsre=new JSONArray();
        JSONObject jsonObject=JSONObject.parseObject(message);
        JSONArray jsonArray= jsonObject.getJSONArray("params");
        //jsonArray.getJSONObject(2);
        JSONObject jsdata=jsonArray.getJSONObject(1);

        //对卖方深度更新
        if(jsdata.getJSONArray("asks")!=null)
        {   //原数组下标i 更新数组下标j
            int i=0,j=0;
            JSONArray jsasksold=jsold.getJSONArray("asks");
            JSONArray jsasksupdate=jsdata.getJSONArray("asks");
           // "undefined" == typeof c[e.a] ? d.push(a[e.b++]) : "undefined" == typeof a[e.b] ? d.push(c[e.a++]) : parseFloat(c[e.a][0]) > parseFloat(a[e.b][0]) ? d.push(a[e.b++]) : c[e.a][0] == a[e.b][0] ? (1e-8 < parseFloat(a[e.b][1]) ? d.push(a[e.b++]) : e.b++,e.a++) : d.push(c[e.a++]);
            for (; i < jsasksold.size() || j < jsasksupdate.size(); )
            {
              if(i>=jsasksold.size()) {
                asksre.add(jsasksupdate.get(j));
                j++;
              }else
                {
                  if(j>=jsasksupdate.size())
                  {
                        asksre.add(jsasksold.get(i));
                        i++;
                  }
                  else
                  {
                      if(jsasksold.getJSONArray(i).getBigDecimal(0).compareTo(jsasksupdate.getJSONArray(j).getBigDecimal(0))==1)
                      {
                          asksre.add(jsasksupdate.get(j));//更新组处理一个数据则更新组下标加1
                          j++;
                      }
                      else
                      {
                          if(jsasksold.getJSONArray(i).getBigDecimal(0).compareTo(jsasksupdate.getJSONArray(j).getBigDecimal(0))==0)
                          {
                              if(Math.pow(1 ,-8)<jsasksupdate.getJSONArray(j).getDouble(1))
                              {
                                  asksre.add(jsasksupdate.get(j));
                                  i++;
                                  j++;
                              }else
                              {
                                  // i++;
                                  j++;
                              }
                          }
                          else
                          {
                              asksre.add(jsasksold.get(i));//更新组处理一个数据则更新组下标加1
                              i++;
                          }
                      }
                   }
                  }
            }

        }
        //买方深度更新
        if(jsdata.getJSONArray("bids")!=null)
        {
            //买方就是值大放在第一位
            int i=0,j=0;
            JSONArray jsbidssold=jsold.getJSONArray("bids");
            JSONArray jsbidsupdate=jsdata.getJSONArray("bids");
            // "undefined" == typeof c[e.a] ? d.push(a[e.b++]) : "undefined" == typeof a[e.b] ? d.push(c[e.a++]) : parseFloat(c[e.a][0]) > parseFloat(a[e.b][0]) ? d.push(a[e.b++]) : c[e.a][0] == a[e.b][0] ? (1e-8 < parseFloat(a[e.b][1]) ? d.push(a[e.b++]) : e.b++,e.a++) : d.push(c[e.a++]);
            for (; i < jsbidssold.size() || j < jsbidsupdate.size(); )
            {
                if(i>=jsbidssold.size()) {
                    asksre.add(jsbidsupdate.get(j));
                    j++;
                }else
                {
                    if(j>=jsbidsupdate.size())
                    {
                        asksre.add(jsbidssold.get(i));
                        i++;
                    }
                    else
                    {
                        if(jsbidssold.getJSONArray(i).getBigDecimal(0).compareTo(jsbidsupdate.getJSONArray(j).getBigDecimal(0))==-1)
                        {
                            asksre.add(jsbidsupdate.get(j));//更新组处理一个数据则更新组下标加1
                            j++;
                        }
                        else
                        {
                            if(jsbidssold.getJSONArray(i).getBigDecimal(0).compareTo(jsbidsupdate.getJSONArray(j).getBigDecimal(0))==0)
                            {
                                if(Math.pow(1 ,-8)<jsbidsupdate.getJSONArray(j).getDouble(1))
                                {
                                    asksre.add(jsbidsupdate.get(j));
                                    i++;
                                    j++;
                                }else
                                {
                                    // i++;
                                    j++;
                                }
                            }
                            else
                            {
                                asksre.add(jsbidssold.get(i));//更新组处理一个数据则更新组下标加1
                                i++;
                            }
                        }
                    }
                }
            }
        }
        jsre.put("symbol",jsold.getString("symbol"));
        if(asksre.isEmpty())
        {
            jsre.put("asks",jsold.getJSONArray("asks"));
        }else
        {
            jsre.put("asks",asksre);
        }

        if(bidsre.isEmpty())
        {
            jsre.put("bids",jsold.getJSONArray("bids"));
        }else
        {
            jsre.put("bids",bidsre);
        }
        return  jsre;
    }

}
