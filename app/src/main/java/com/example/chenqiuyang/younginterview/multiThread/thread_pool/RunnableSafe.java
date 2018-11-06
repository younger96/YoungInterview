package com.example.chenqiuyang.younginterview.multiThread.thread_pool;

import android.util.Log;

public abstract class RunnableSafe implements Runnable {
    private static final String TAG = "RunnableSafe";
    private final IReferable mRef;

    public RunnableSafe(IReferable ref) {
        this.mRef = ref;
    }

    public IReferable getRef() {
        return mRef;
    }

    @Override
    public final void run() {
        if (mRef == null || mRef.isReferenceActive()) {
            this.runSafe();
        } else {
            Log.w(TAG, "run(),this ref is not active,not call runSafe():" + getClass().getName());
        }
    }

    public abstract void runSafe();

}