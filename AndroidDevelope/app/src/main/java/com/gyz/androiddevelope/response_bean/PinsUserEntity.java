package com.gyz.androiddevelope.response_bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LiCola on  2016/05/05  21:28
 */

public class PinsUserEntity implements Parcelable {
    private int user_id;
    private String username;
    private String urlname;
    private int created_at;
    private Avatar avatar;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrlname() {
        return urlname;
    }

    public void setUrlname(String urlname) {
        this.urlname = urlname;
    }

    public int getCreated_at() {
        return created_at;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.user_id);
        dest.writeString(this.username);
        dest.writeString(this.urlname);
        dest.writeInt(this.created_at);
        dest.writeParcelable(this.avatar, flags);
    }

    public PinsUserEntity() {
    }

    protected PinsUserEntity(Parcel in) {
        this.user_id = in.readInt();
        this.username = in.readString();
        this.urlname = in.readString();
        this.created_at = in.readInt();
        this.avatar = in.readParcelable(Avatar.class.getClassLoader());
    }

    public static final Creator<PinsUserEntity> CREATOR = new Creator<PinsUserEntity>() {
        @Override
        public PinsUserEntity createFromParcel(Parcel source) {
            return new PinsUserEntity(source);
        }

        @Override
        public PinsUserEntity[] newArray(int size) {
            return new PinsUserEntity[size];
        }
    };
}
