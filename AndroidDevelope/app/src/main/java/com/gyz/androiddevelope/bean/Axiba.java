package com.gyz.androiddevelope.bean;

/**
 * @author: guoyazhou
 * @date: 2016-02-01 16:23
 */
public class Axiba {
    private static final String TAG = "Axiba";

    private WeatherInfo weatherinfo;

    public static String getTAG() {
        return TAG;
    }

    public WeatherInfo getWeatherinfo() {
        return weatherinfo;
    }

    public void setWeatherinfo(WeatherInfo weatherinfo) {
        this.weatherinfo = weatherinfo;
    }
}
