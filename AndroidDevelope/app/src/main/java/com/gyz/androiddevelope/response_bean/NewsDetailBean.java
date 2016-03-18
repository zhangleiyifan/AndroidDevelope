package com.gyz.androiddevelope.response_bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author: guoyazhou
 * @date: 2016-03-17 11:20
 */
public class NewsDetailBean {

    private String body;  //HTML 格式的新闻
    @SerializedName(value = "image_source")  //
    private String imageSource;  //图片的内容提供方。为了避免被起诉非法使用图片，在显示图片时最好附上其版权信息。
    private String title;
    private String image;//获得的图片同 最新消息 获得的图片分辨率不同。这里获得的是在文章浏览界面中使用的大图。
    @SerializedName(value = "share_url")
    private String ShareUrl;//供在线查看内容与分享至 SNS 用的 URL
    private String[] js;//供手机端的 WebView(UIWebView) 使用
    @SerializedName(value = "ga_prefix")
    private String gaPrefix;//供 Google Analytics 使用
    private int type;//新闻的类型
    private int id;//新闻的 id
    private String[] css;// 供手机端的 WebView(UIWebView) 使用

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShareUrl() {
        return ShareUrl;
    }

    public void setShareUrl(String shareUrl) {
        ShareUrl = shareUrl;
    }

    public String[] getJs() {
        return js;
    }

    public void setJs(String[] js) {
        this.js = js;
    }

    public String getGaPrefix() {
        return gaPrefix;
    }

    public void setGaPrefix(String gaPrefix) {
        this.gaPrefix = gaPrefix;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String[] getCss() {
        return css;
    }

    public void setCss(String[] css) {
        this.css = css;
    }
}
