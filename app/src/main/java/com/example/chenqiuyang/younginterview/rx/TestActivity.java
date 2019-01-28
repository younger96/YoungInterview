package com.example.chenqiuyang.younginterview.rx;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.chenqiuyang.younginterview.R;
import com.google.gson.Gson;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 2018/12/10
 * from 陈秋阳
 * 功能描述：
 */
public class TestActivity extends Activity {
    private static final String TAG = "TestActivityYoung";
    private int tagInt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);
        tagInt = 0;
        Observable.create(new ObservableOnSubscribe<Response>() {
            @Override
            public void subscribe(ObservableEmitter<Response> e) throws Exception {

            }
        }).map(new Function<Response, Boolean>() {
            @Override
            public Boolean apply(Response response) throws Exception {
                return null;
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {

                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean){
                            Log.i(TAG, "accept: "+aBoolean);
                        }
                    }
                });



        tagInt++;
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.e(TAG + tagInt, "Observable emit 1" + "\n");
                e.onNext(1);
                Log.e(TAG + tagInt, "Observable emit 2" + "\n");
                e.onNext(2);
                Log.e(TAG + tagInt, "Observable emit 3" + "\n");
                e.onNext(3);
                e.onComplete();//被观察者完成发送,接下来发送的事件下面接受不到
                Log.e(TAG + tagInt, "Observable emit 4" + "\n" );
                e.onNext(4);
            }
        }).subscribe(new Observer<Integer>() {
            // 第二步：初始化Observer
            private int i;
            private Disposable mDisposable;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mDisposable = d;
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.i(TAG + tagInt, "onNext: "+integer);
//                i++;
//                if (i == 2) {
//                    // 在RxJava 2.x 中，新增的Disposable可以做到切断的操作，让Observer观察者不再接收上游事件
//                    mDisposable.dispose();//观察者停止上游发送
//                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG + tagInt, "onError : value : " + e.getMessage() + "\n" );
            }

            @Override
            public void onComplete() {
                Log.e(TAG + tagInt, "onComplete" + "\n" );
            }
        });


    }
}
