package com.gyz.androiddevelope.retrofit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author: guoyazhou
 * @date: 2016-02-19 17:41
 */
public class RxUtil {
    private static final String TAG = "RxUtil";

    /**
     *
     * @param func1 输入调用接口及参数
     * @param subscriber 请求成功后调用(onNext     onError    onComplete)
     * @param <S>
     * @param <T>
     * @param <R>
     */
    public static <S, R extends Observable<S>> void subscribeAll( Func1<String, R> func1, Subscriber<S> subscriber) {

        Observable.just("").flatMap(func1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    public static <S,R extends Observable<S>> void subscribeOnError(Func1<String, R> func1,Action1<S> actionNext,Action1<Throwable> actionError){

        Observable.just("").flatMap(func1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(actionNext,actionError);
    }

    /**
     *
     * @param func1  输入调用接口及参数
     * @param action  请求成功后调用 (onNext)
     * @param <S>
     * @param <T>
     * @param <R>
     */
    public static <S,R extends Observable<S>> void subscribeOnNext(Func1<String,R> func1,Action1<S> action){
        Observable.just("").flatMap(func1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(action);
    }

}
