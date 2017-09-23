package com.meng.newsreader.presentation.beans;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mengzhou on 9/22/17.
 */

public class QueryMap {

    public static String API_KEY = "api_key";
    public static String QUERY = "q";
    public static String FILTER_QUERY = "fq";
    public static String SORT = "sort";
    public static String BEGIN_DATE = "begin_date";
    public static String END_DATE = "end_date";
    public static String FL = "fl";
    public static String HL = "hl";
    public static String PAGE = "page";
    public static String FACET_FIELD = "facet_field";
    public static String FACET_FILTER = "facet_filter";

    public static Map<String, String> initValue(String key) {
        Map<String, String> map = new HashMap<>();
        map.put(QUERY, key);
        return map;
    }
}
