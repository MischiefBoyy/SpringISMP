package com.inrich.util;


import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class OutPrintUtil {
	public static String getJSONString(String state) {
        JSONObject json = new JSONObject();
        json.put("state", state);
        return json.toJSONString();
    }

    public static String getJSONString(String state, String msg) {
        JSONObject json = new JSONObject();
        json.put("state", state);
        json.put("msg", msg);
        return json.toJSONString();
    }

    
    public static String getJSONString( String state,Object data) {
    	Map<String,Object> map=new HashMap<>(2);
        map.put("state", state);
        map.put("data", data);
        return JSONObject.toJSONString(map, SerializerFeature.WriteMapNullValue);
    }

}
