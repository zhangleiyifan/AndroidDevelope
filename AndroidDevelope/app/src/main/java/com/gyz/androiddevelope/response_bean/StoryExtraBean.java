package com.gyz.androiddevelope.response_bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author: guoyazhou
 * @date: 2016-03-29 17:38
 */
public class StoryExtraBean {
//
//    long_comments : 长评论总数
//    popularity : 点赞总数
//    short_comments : 短评论总数
//    comments : 评论总数

    @SerializedName(value = "long_comments")
    private int longComments;
    private int popularity;
    @SerializedName(value = "short_comments")
    private int shortComments;
    private int comments;

    public int getLongComments() {
        return longComments;
    }

    public void setLongComments(int longComments) {
        this.longComments = longComments;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getShortComments() {
        return shortComments;
    }

    public void setShortComments(int shortComments) {
        this.shortComments = shortComments;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }
}
