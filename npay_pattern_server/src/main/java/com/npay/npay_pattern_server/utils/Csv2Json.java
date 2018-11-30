package com.npay.npay_pattern_server.utils;

import com.npay.npay_pattern_server.dto.PayInfo;
import com.npay.npay_pattern_server.enumeration.PayInfoEnum;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.HashMap;

public class Csv2Json {
    public static HashMap<String, String> payInfo2Map(String payInfoRow) {
        String str[] = payInfoRow.split(",");
        HashMap<String, String> payInfoMap = new HashMap<>();

        for (PayInfoEnum p : PayInfoEnum.values()) {
            payInfoMap.put(p.getName(), str[p.getIndex()].replaceAll("\"", ""));
        }
        return payInfoMap;
    }

    // map to json
    public static String map2Json(HashMap map) {
        return JSONValue.toJSONString(map);
    }

    // string to json
    public static JSONObject str2Json(String payInfoStr) {
        Object obj = JSONValue.parse(payInfoStr);
        JSONObject jsonObj = (JSONObject) obj;
        return jsonObj;
    }
}
