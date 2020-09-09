package com.chenay.platform.http.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonUtil {
    private volatile static Gson sGson;

    private GsonUtil() {
    }

    public static Gson getGson() {
        if (sGson == null) {
            synchronized (GsonUtil.class) {
                if (sGson == null) {
                    sGson = new Gson();
                }
            }
        }
        return sGson;
    }

    public static  <T> T fromJson(String json, Type typeOfT) throws JsonSyntaxException {
        return getGson().fromJson(json, typeOfT);
    }

    public static String toJson(Object src) {

        return getGson().toJson(src);
    }

    public static <T> List<T> getObjectList(String jsonString, Class<T> cls){
        List<T> list = new ArrayList<T>();
        try {
            JsonArray arry = new JsonParser().parse(jsonString).getAsJsonArray();

            for (JsonElement jsonElement : arry) {
                list.add(sGson.fromJson(jsonElement, cls));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


}
