package com.gyz.androiddevelope.retrofit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author: guoyazhou
 * @date: 2016-02-19 17:41
 */
public class RxUtil {
    private static final String TAG = "RxUtil";

    public static <S, T, R extends Observable<S>> void subscribe(T t, Func1<T, R> func1, Subscriber<S> subscriber) {

        Observable.just(t).flatMap(func1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

}
