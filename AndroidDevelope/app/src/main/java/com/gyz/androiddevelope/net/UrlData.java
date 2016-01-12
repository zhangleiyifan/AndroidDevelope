package com.gyz.androiddevelope.net;

import java.io.Serializable;

/**
 * @version V1.0
 * @FileName: com.gyz.androiddevelope.net.UrlData.java
 * @author: ZhaoHao
 * @date: 2016-01-11 23:57
 */
public class UrlData implements Serializable {

    private String key;
    private String netType;
    private String url;
    private long expires;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
