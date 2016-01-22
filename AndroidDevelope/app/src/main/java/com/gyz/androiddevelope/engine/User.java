package com.gyz.androiddevelope.engine;

import com.gyz.androiddevelope.util.FileUtil;

import java.io.Serializable;

/**
 * @author: guoyazhou
 * @date: 2016-01-22 15:16
 */
public class User implements Serializable {

    private static final String TAG = "User";

    private String userName;
    private boolean isLogin;
    private String LoginName;

    private static User instantce;


    private User() {
    }

    public static User getInstantce() {
        if (instantce == null) {
            Object object = FileUtil.restoreObject(AppContants.CACHE_PATH + TAG);
            if (object == null) {
                // App第一次启动，文件不存在，则新建之
                object = new User();
                FileUtil.saveObject(AppContants.CACHE_PATH + TAG, object);
            }
            instantce = (User) object;
        }
        return instantce;
    }

    public void reset() {
        userName = null;
        LoginName = null;
        isLogin = false;
    }

    public void saveInfo() {
        FileUtil.saveObject(AppContants.CACHE_PATH, this);
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public String getLoginName() {
        return LoginName;
    }

    public void setLoginName(String loginName) {
        LoginName = loginName;
    }

}
