package com.cuong02n.aimsbackend.util;

import com.google.gson.Gson;

public class GsonUtil {
    private static final Gson gson = new Gson();
    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

}
