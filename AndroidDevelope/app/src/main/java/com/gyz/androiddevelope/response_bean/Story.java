package com.gyz.androiddevelope.response_bean;

import java.util.List;

/**
 * @author: guoyazhou
 * @date: 2016-03-15 13:55
 */
public class Story {
    public int id;
    public String title;
    public String ga_prefix; //供 Google Analytics 使用
    public List<String> images;
    public int type;

}
