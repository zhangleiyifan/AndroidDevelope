package com.gyz.androiddevelope.response_bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 图片种类类
 * @author: guoyazhou
 * @date: 2016-04-21 13:37
 */
public class GalleryBean implements Parcelable {
    private int id;
    private int  galleryclass ;//          图片分类
    private String title     ;//          标题
    private String img     ;//          图库封面
    private int count     ;//          访问数
    private int rcount     ;//           回复数
    private int fcount     ;//          收藏数
    private int size     ;//      图片多少张

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGalleryclass() {
        return galleryclass;
    }

    public void setGalleryclass(int galleryclass) {
        this.galleryclass = galleryclass;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getRcount() {
        return rcount;
    }

    public void setRcount(int rcount) {
        this.rcount = rcount;
    }

    public int getFcount() {
        return fcount;
    }

    public void setFcount(int fcount) {
        this.fcount = fcount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.galleryclass);
        dest.writeString(this.title);
        dest.writeString(this.img);
        dest.writeInt(this.count);
        dest.writeInt(this.rcount);
        dest.writeInt(this.fcount);
        dest.writeInt(this.size);
    }

    public GalleryBean() {
    }

    protected GalleryBean(Parcel in) {
        this.id = in.readInt();
        this.galleryclass = in.readInt();
        this.title = in.readString();
        this.img = in.readString();
        this.count = in.readInt();
        this.rcount = in.readInt();
        this.fcount = in.readInt();
        this.size = in.readInt();
    }

    public static final Parcelable.Creator<GalleryBean> CREATOR = new Parcelable.Creator<GalleryBean>() {
        @Override
        public GalleryBean createFromParcel(Parcel source) {
            return new GalleryBean(source);
        }

        @Override
        public GalleryBean[] newArray(int size) {
            return new GalleryBean[size];
        }
    };
}
