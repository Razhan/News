package com.guanchazhe.news.model.entities;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ranzh on 1/5/2016.
 */

public class NewsType {

    private static Map<String, String> map;

    static {
        map = new HashMap<String, String>();
        map.put("yaowen", "要闻");
        map.put("xinwen", "新闻");
    }

    public static String getNewsType(String key) {

        if (map.containsKey(key)) {
            return map.get(key);
        }
        return "要闻";
    }
}