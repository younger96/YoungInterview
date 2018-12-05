package com.example.chenqiuyang.younginterview.bus.rx;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.chenqiuyang.younginterview.bus.rx.message.MyMsg;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * 2018/12/5
 * from 陈秋阳
 * 功能描述：
 */
public class DemoReceive extends Activity {
    private static final String TAG = "DemoReceive";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        RxBus.getInstance().toObservable().map(new Function<Object, MyMsg>() {
            @Override
            public MyMsg apply(Object o) throws Exception {
                return (MyMsg)o;
            }
        }).subscribe(new Consumer<MyMsg>() {
            @Override
            public void accept(MyMsg myMsg) throws Exception {
                if (myMsg!=null){
                    Log.i(TAG, "accept: "+myMsg.getMsg());
                }
            }
        });


        RxBusNoBack.get().toObservable().map(new Function<Object, MyMsg>() {
            @Override
            public MyMsg apply(Object o) throws Exception {
                return null;
            }
        }).subscribe(new Consumer<MyMsg>() {
            @Override
            public void accept(MyMsg myMsg) throws Exception {

            }
        });


        RxBusWithBack.get().toFlowable().map(new Function<Object, MyMsg>() {
            @Override
            public MyMsg apply(Object o) throws Exception {
                return null;
            }
        }).subscribe(new Consumer<MyMsg>() {
            @Override
            public void accept(MyMsg myMsg) throws Exception {

            }
        });



    }

}
