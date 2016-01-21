package com.gyz.androiddevelope.cache;

import java.io.Serializable;

/**
 * @author: guoyazhou
 * @date: 2016-01-19 15:19
 */
public class CacheItem implements Serializable{

    /** 过期时间的时间戳 */
    private long timeStamp = 0L;


    /** JSON字符串 */
    private String data;


    /** 存储的Key */
    private String key;


    public CacheItem(final String key, final String data, final long expiredTime) {
        this.key = key;
//        将当前时间加上 想缓存的时间进行存储，这样下次取缓存时，直接对该值进行对比
        this.timeStamp = System.currentTimeMillis() + expiredTime *1000 ;
        this.data = data;
    }

    public long getTimeStamp(){
        return timeStamp;
    }

    public String getData(){
     return data;
    }

    public String getKey(){
        return key;
    }
}
