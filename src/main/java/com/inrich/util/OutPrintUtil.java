package com.inrich.util;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

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
        JSONObject json = new JSONObject();
        json.put("state", "state");
        json.put("data", data);
        return json.toJSONString();
    }

}
