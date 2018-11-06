
package com.example.chenqiuyang.younginterview.customView.util.tasks;

import android.os.Looper;


/**
 * Created by walljiang on 2018/5/8.
 */

public abstract class VBaseOperator implements IVOperator, Runnable {

    protected volatile boolean isProccessing = false;

    protected IVResultCallback mResultCallback;

    @Override
    public void start(IVResultCallback callback) {
        this.mResultCallback = callback;
        if (!isProccessing)
            executeRunnable(this);
    }

    protected void executeRunnable(Runnable runnable) {
        if (runnable != null) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
//                ThreadPool.shorter().execute(runnable);
            } else {
                runnable.run();
            }
        }
    }

    @Override
    public void removeCallback() {
        this.mResultCallback = null;
    }

    @Override
    public void run() {
        isProccessing = true;
        int result = operate();
        if (this.mResultCallback != null) {
            mResultCallback.onResult(this, result);
        }
        this.mResultCallback = null;
        isProccessing = false;
    }

    @Override
    public boolean isProccessing() {
        return isProccessing;
    }

    protected abstract int operate();
}
