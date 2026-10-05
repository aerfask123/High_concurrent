package com.aerfask.netty.study.util;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.GsonBuilder;

public class JsonUtils {

    static GsonBuilder gb = new GsonBuilder();

    static {
        gb.disableHtmlEscaping();
    }

    public static <T> String pojoToJson(T obj) {
        return gb.create().toJson(obj);
    }

    public static <T> T jsonToPojo(String json, Class<T> tClass) {
        return JSONObject.parseObject(json, tClass);
    }
}
