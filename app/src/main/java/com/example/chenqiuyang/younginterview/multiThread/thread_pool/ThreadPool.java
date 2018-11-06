package com.example.chenqiuyang.younginterview.multiThread.thread_pool;

import android.util.Log;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool// extends AbsThreadPool
{
    private static final String TAG = "ThreadPool";
    private static final AbsThreadPool sPool = new ThreadPoolImpl();

    public static AbsThreadPool.Proxy shorter() {
        return sPool.shorter();
    }

    public static AbsThreadPool.Proxy upload(){
        return sPool.upload();
    }

    public static AbsThreadPool.Proxy download() {
        return sPool.download();
    }

    public static AbsThreadPool.Proxy single() {
        return sPool.single();
    }

    /**发送auth请求专用单线程池*/
    public static AbsThreadPool.Proxy authThread(){
        return sPool.single("authThread");
    }

    public static AbsThreadPool.Proxy syncThread(){
        return sPool.single("syncThread");
    }

    public static int getRunningTaskCount(){
        return sPool.getRunningTaskCount();
    }

    public static String dumpState(){
        return sPool.dumpState();
    }

    public static List<Runnable> shutdownNowAll(){
        return sPool.shutdownNowAll();
    }

    public static void reset(){
        sPool.reset();
    }

    public static void removeReference(IReferable referable){
        sPool.removeReference(referable);
    }

    public static class ThreadPoolImpl extends AbsThreadPool {

        @Override
        protected synchronized Proxy generatePool(String type,String name) {
            if (KEY_SINGLE.equals(type)) {
                return new Proxy(1, 1,
                        60L, TimeUnit.SECONDS,
                        new LinkedBlockingQueue<Runnable>(),name) {
                    @Override
                    protected void finalize() {
                        super.finalize();
                        super.shutdown();
                    }
                };
            }else{
                return new Proxy(16, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
                    new ArrayBlockingQueue<Runnable>(8), new DefaultThreadFactory(name),
                    new RejectedExecutionHandler() {
                        @Override public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                            Log.e(TAG,"mGlobalPools rejectedExecution Exception,executor status : " + executor.toString());
                            new Thread(r).start();
                        }
                    });
            }
        }

        private Proxy defPools(){
            return getPool(KEY_DEFAULT,"sky-pools");
        }

        @Override
        public Proxy shorter() {
            return defPools();
        }

        @Override public Proxy upload() {
            return defPools();
        }

        @Override
        public Proxy download() {
            return defPools();
        }

        @Override
        public Proxy single() {
            return single("p-single-def");
        }
    }
}
