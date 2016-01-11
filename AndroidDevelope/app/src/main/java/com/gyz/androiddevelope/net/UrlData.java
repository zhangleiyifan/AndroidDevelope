package com.gyz.androiddevelope.net;

import java.io.Serializable;

/**
 * @version V1.0
 * @FileName: com.gyz.androiddevelope.net.UrlData.java
 * @author: ZhaoHao
 * @date: 2016-01-11 23:57
 */
public class UrlData implements Serializable {

    private String netType;
    private String url;
    private long exirp;

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

    public long getExirp() {
        return exirp;
    }

    public void setExirp(long exirp) {
        this.exirp = exirp;
    }
}
