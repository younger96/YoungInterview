package com.example.chenqiuyang.younginterview.sourse.handle;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

/**
 * TODO:功能说明
 *
 * @author: chenqiuyang
 * @date: 2018-08-01 10:26
 */
public class HandleActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    private static class MyHandler extends Handler {
        private final WeakReference<HandleActivity> mWeakReference;

        public MyHandler(HandleActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            HandleActivity activity = mWeakReference.get();
            if (activity != null) {
                switch (msg.what) {

                    default:
                        break;
                }
            }
        }
    }
}
