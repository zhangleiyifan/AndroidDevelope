package com.gyz.androiddevelope.response_bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author: guoyazhou
 * @date: 2016-03-08 16:20
 */
public class HealthInfoList extends BaseResponBean {
    private static final String TAG = "HealthInfoList";

    @SerializedName(value = "tngou")
    public List<HealthInfo> healthInfoList;

    public class HealthInfo{
        public int count ;//访问次数
        public String description;//描述
        public int fcount;//收藏数
        public int id;
        public String img;//图片
        public int infoclass;//分类
        public String keywords;//关键字
        public int rcount;//评论读数
        public long time;
        public String title;//资讯标题
    }

}
