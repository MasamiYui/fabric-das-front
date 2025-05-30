package org.it611.das.util;

import com.alibaba.fastjson.JSONObject;

public class ResultUtil {

    public static JSONObject constructResponse(int code, String msg, Object data) {
        JSONObject jo = new JSONObject();
        jo.put("code", code);
        jo.put("msg", msg);
        jo.put("data", data);
        return jo;
    }

}
