package com.osshare.framework.util;

import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.subscriptions.ArrayCompositeSubscription;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subjects.Subject;

/**
 * Created by apple on 16/12/28.
 */
public class RxEventBus {
    private static volatile RxEventBus mInstance;
    private Subject<Object> mSubject;
    private final Map<Class<?>, Disposable> disposables = new HashMap<>();

    private RxEventBus() {
        mSubject = PublishSubject.create();
    }

    public static RxEventBus getInstance() {
        if (mInstance == null) {
            synchronized (RxEventBus.class) {
                if (mInstance == null) {
                    mInstance = new RxEventBus();
                }
            }
        }
        return mInstance;
    }

    private <T> Observable<T> toObservable(final Class<T> type) {
        return mSubject.ofType(type);
    }

    /**
     * 是否有订阅者／观察者
     *
     * @return
     */
    public boolean hasObservers() {
        return mSubject.hasObservers();
    }

    /**
     * 发布事件
     *
     * @param obj
     */
    public void publish(Object obj) {
        mSubject.onNext(obj);
    }

    /**
     * 订阅事件
     *
     * @param type
     * @param onNext
     * @param onError
     * @param <T>
     * @return
     */
    public <T> Disposable doSubscribe(Class<T> type, Consumer<T> onNext, Consumer<Throwable> onError) {
        return toObservable(type).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, onError);
    }

    /**
     * 释放订阅者／观察者
     *
     * @param obj
     */
    public boolean unSubscribe(Object obj) {
        if (disposables == null) {
            return false;
        }
        Disposable disposable = disposables.get(obj.getClass());
        if (disposable == null) {
            return false;
        }
        if (!disposable.isDisposed()) {
            disposable.dispose();
            return true;
        }
        return false;
    }
}
