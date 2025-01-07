package com.cuong02n.aimsbackend.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {
    private static final Gson gson = new Gson();
    private static final Gson gsonExpose = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    public static String toJson(Object obj) {
        return gsonExpose.toJson(obj);  // Use gsonExpose instead of gson
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);  // Keep this as is for deserialization
    }


}
