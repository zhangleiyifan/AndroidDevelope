package com.gyz.androiddevelope.response_bean;

/**
 * 图片种类类
 * @author: guoyazhou
 * @date: 2016-04-21 13:37
 */
public class GalleryTypeBean {
    private int id;
    private String name;
    private String title;
    private String keywords;
    private String description;
    private int seq;//排序 从1。。。。10开始

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }


}
