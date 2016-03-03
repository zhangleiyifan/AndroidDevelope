package com.gyz.androiddevelope.response_bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author: guoyazhou
 * @date: 2016-03-03 15:53
 */
public class Tngou extends BaseResponBean {

    @SerializedName(value = "tngou")
    public List<InfoClass> infoList;


    class InfoClass {
        public int id;
        public String name;
        public String title;
        public String keywords;
        public String description;
        public int seq;//排序 从0。。。。10开始
    }

}
