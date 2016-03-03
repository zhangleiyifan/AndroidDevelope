package com.gyz.androiddevelope.response_bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author: guoyazhou
 * @date: 2016-03-03 16:16
 */
public class InfoList extends BaseResponBean {

    @SerializedName(value = "tngou")
    public List<Info> infos;


    public class Info {
        public String title;//资讯标题
        public int infoclass;//分类
        public String img;//图片
        public String description;//描述
        public String keywords;//关键字
        //  private String message;//资讯内容
        public int count;//访问次数
        public int fcount;//收藏数
        public int rcount;//评论读数
        public long time;
    }
}
