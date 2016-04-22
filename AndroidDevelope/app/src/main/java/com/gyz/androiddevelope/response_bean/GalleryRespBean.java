package com.gyz.androiddevelope.response_bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author: guoyazhou
 * @date: 2016-04-21 14:35
 */
public class GalleryRespBean {

    private boolean status;

    private int total;

    @SerializedName(value = "tngou")
    private List<GalleryBean> tngouList;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<GalleryBean> getTngouList() {
        return tngouList;
    }

    public void setTngouList(List<GalleryBean> tngouList) {
        this.tngouList = tngouList;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
