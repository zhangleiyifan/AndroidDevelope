package com.gyz.androiddevelope.util;

/**
 * @author: guoyazhou
 * @date: 2016-03-15 13:51
 */
public class DateUtil {
    private static final String TAG = "DateUtil";

    public static String convertDate(String date) {
        String result = date.substring(0, 4);
        result += "年";
        result += date.substring(4, 6);
        result += "月";
        result += date.substring(6, 8);
        result += "日";
        return result;
    }

}
