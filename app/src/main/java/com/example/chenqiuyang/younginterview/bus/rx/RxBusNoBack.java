package com.example.chenqiuyang.younginterview.bus.rx;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * 2018/12/5
 * from 陈秋阳
 * 功能描述：没有背压处理（Backpressure）的 Rxbus
 */
public class RxBusNoBack {
    private final Subject<Object> mBus;

    private RxBusNoBack() {
        // toSerialized method made bus thread safe
        mBus = PublishSubject.create().toSerialized();
    }

    public static RxBusNoBack get() {
        return Holder.BUS;
    }

    public void post(Object obj) {
        mBus.onNext(obj);
    }

    public <T> Observable<T> toObservable(Class<T> tClass) {
        return mBus.ofType(tClass);
    }

    public Observable<Object> toObservable() {
        return mBus;
    }

    public boolean hasObservers() {
        return mBus.hasObservers();
    }

    private static class Holder {
        private static final RxBusNoBack BUS = new RxBusNoBack();
    }
}
