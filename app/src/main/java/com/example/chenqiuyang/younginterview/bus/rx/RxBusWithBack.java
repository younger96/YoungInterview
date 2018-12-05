package com.example.chenqiuyang.younginterview.bus.rx;

import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * 2018/12/5
 * from 陈秋阳
 * 功能描述：有背压处理（Backpressure）的 Rxbus
 */
public class RxBusWithBack {
    private final FlowableProcessor<Object> mBus;

    private RxBusWithBack() {
        // toSerialized method made bus thread safe
        mBus = PublishProcessor.create().toSerialized();
    }

    public static RxBusWithBack get() {
        return Holder.BUS;
    }

    public void post(Object obj) {
        mBus.onNext(obj);
    }

    public <T> Flowable<T> toFlowable(Class<T> tClass) {
        return mBus.ofType(tClass);
    }

    public Flowable<Object> toFlowable() {
        return mBus;
    }

    public boolean hasSubscribers() {
        return mBus.hasSubscribers();
    }

    private static class Holder {
        private static final RxBusWithBack BUS = new RxBusWithBack();
    }
}
