package com.gyz.androiddevelope.net;

/**
 * @author: guoyazhou
 * @date: 2016-01-12 20:09
 */
public interface RequestCallback {

    public void onSuccess(String result);

    public void onFail(String errorMessage);
}
