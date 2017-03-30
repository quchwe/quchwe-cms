package com.quchwe.gd.cms.utils;

import com.google.gson.Gson;
import com.google.gson.internal.Primitives;

import java.lang.reflect.Type;

/**
 * Created by quchwe on 2017/3/16 0016.
 */
public class JsonUtils {

    private static Gson gson = new Gson();

    public static String toJson(Object o) {
        return gson.toJson(o);
    }

    public static <T> T fromJson(String jsonString, Class<T> classOfT) {
        Object object = gson.fromJson(jsonString, (Type) classOfT);
        return Primitives.wrap(classOfT).cast(object);

    }
}
