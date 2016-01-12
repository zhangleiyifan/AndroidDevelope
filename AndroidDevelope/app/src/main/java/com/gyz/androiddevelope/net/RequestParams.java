package com.gyz.androiddevelope.net;

import java.io.Serializable;

/**
 * @author: guoyazhou
 * @date: 2016-01-12 16:21
 */
public class RequestParams implements Serializable {

    private static final String TAG = "RequestParams";

    private String name;
    private  String value;

    public RequestParams(String name, String value){
        this.name = name;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
