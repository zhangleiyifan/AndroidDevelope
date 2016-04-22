package com.gyz.androiddevelope.response_bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author: guoyazhou
 * @date: 2016-04-21 14:35
 */
public class GalleryTypeRespBean {

    private boolean status;

    @SerializedName(value = "tngou")
    private List<GalleryTypeBean> tngouList;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<GalleryTypeBean> getTngouList() {
        return tngouList;
    }

    public void setTngouList(List<GalleryTypeBean> tngouList) {
        this.tngouList = tngouList;
    }
}
