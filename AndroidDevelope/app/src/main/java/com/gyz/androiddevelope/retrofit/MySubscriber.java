package com.gyz.androiddevelope.retrofit;

import rx.Subscriber;

/**
 * @author: guoyazhou
 * @date: 2016-03-21 17:06
 */
public class MySubscriber<T> extends Subscriber<T> {

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onNext(T o) {

    }
}
