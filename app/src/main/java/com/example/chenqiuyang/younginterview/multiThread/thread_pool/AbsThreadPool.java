package com.example.chenqiuyang.younginterview.multiThread.thread_pool;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.example.chenqiuyang.younginterview.util.time.TimeUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by haibiao on 2017/12/1.
 */

public abstract class AbsThreadPool {
    private static final String TAG = "AbsThreadPool";
    public static final String KEY_DEFAULT = "default";
    public static final String KEY_SINGLE = "single";
    public static final String KEY_SHORTER = "shorter";
    public static final String KEY_UPLOAD = "upload";
    public static final String KEY_DOWNLOAD = "download";

    private final Map<String, Proxy> mPoolMap = new ConcurrentHashMap<>();

    protected final Proxy getPool(String type, String name) {
        final String key = getKey(type, name);
        Proxy pool = mPoolMap.get(key);
        if (pool == null) {
            pool = generatePool(type, name);
            mPoolMap.put(key, pool);
        }
        return pool;
    }

    public final String getKey(String type, String name) {
        if (TextUtils.isEmpty(type)) {
            throw new IllegalArgumentException("type can't be empty!");
        }
        return type + name;
    }

    public synchronized final List<Runnable> shutdownNowAll() {
        List<Runnable> list = new ArrayList<>();
        Set<String> keys = mPoolMap.keySet();
        for (Object key : keys.toArray()) {
            ThreadPoolExecutor executor = mPoolMap.get(String.valueOf(key));
            list.addAll(executor.shutdownNow());
        }
        //mPoolMap.clear();//如果clear的话，则会新建实例
        return list;
    }

    public synchronized final void reset() {
        mPoolMap.clear();
    }

    public final int getRunningTaskCount() {
        return TaskCounter.getDefault().getCount();
    }

    public synchronized final String dumpState(){
        JSONObject json = new JSONObject();
        Set<String> keys = mPoolMap.keySet();
        for (Object key : keys.toArray()) {
            ThreadPoolExecutor executor = mPoolMap.get(String.valueOf(key));
            if(executor != null){
                try {
                    json.put(String.valueOf(key),executor.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return json.toString();
    }

    public void removeReference(IReferable ref) {
        Set<String> keys = mPoolMap.keySet();
        for (String key : keys.toArray(new String[keys.size()])) {
            Proxy proxy = mPoolMap.get(key);
            if (proxy != null) {
                proxy.cancel(ref);
            }
        }
    }

    protected abstract Proxy generatePool(String type, String name);

    public abstract Proxy shorter();

    public abstract Proxy upload();

    public abstract Proxy download();

    public abstract Proxy single();

    public Proxy single(String name) {
        return getPool(KEY_SINGLE, name);
    }

    public static class Proxy extends ThreadPoolExecutor {
        private static final AtomicInteger sSeqCounter = new AtomicInteger(1);
        private final Map<Integer, Runnable> mTaskIndex = new ConcurrentHashMap<>();
        private final Map<String, Set<Integer>> mRefIndex = new ConcurrentHashMap<>();
        //key = ref.getClassName + hashCode;

        public Proxy(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
            this(corePoolSize, maximumPoolSize, keepAliveTime, "def");
        }

        public Proxy(int corePoolSize, int maximumPoolSize, long keepAliveTime, String poolPrefix) {
            this(corePoolSize, maximumPoolSize, keepAliveTime,
                    new DefaultThreadFactory(poolPrefix));
        }

        public Proxy(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                ThreadFactory factory) {
            super(corePoolSize, maximumPoolSize, keepAliveTime,
                    TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(),factory,
                    new DiscardPolicy());
        }

        public Proxy(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                BlockingQueue<Runnable> workQueue) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        }

        public Proxy(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                BlockingQueue<Runnable> workQueue, String prefix) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
                    new DefaultThreadFactory(prefix));
        }

        public Proxy(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        }

        public Proxy(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
        }

        public Proxy(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
                RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory,
                    handler);
        }

        public boolean isAlive() {
            if (isTerminated()) {
                return false;
            }
            return !isShutdown() || isTerminating();
        }

        private String createRefKey(IReferable ref) {
            return ref.getClass().getName() + ref.hashCode();
        }

        public void executeSafe(final RunnableSafe command) {
            if (command == null) return;
            final int seq = sSeqCounter.incrementAndGet();
            mTaskIndex.put(seq, command);
            IReferable ref = command.getRef();
            if (ref != null) {
                final String refKey = createRefKey(ref);
                Set<Integer> seqSet = mRefIndex.get(refKey);
                if (seqSet == null) {
                    seqSet = Collections.synchronizedSet(new HashSet<Integer>());
                    mRefIndex.put(refKey, seqSet);
                }
                seqSet.add(seq);
            }
            this.execute(new Runnable() {
                @Override
                public void run() {
                    Runnable runnable = mTaskIndex.remove(seq);
                    if (runnable != null) {
                        runnable.run();
                    }
                }
            });
        }

        @Override
        public void execute(Runnable command) {
            final boolean isAlive = isAlive();
            if (isAlive) {
                //包裹一层，用来做后台任务计数
                super.execute(TaskCounter.getDefault().wrap(command));
            } else {
                Log.w(TAG, "execute Exception：", new IllegalStateException(
                        "Pool not alive,runnable = " + String.valueOf(command)));
            }
        }

        public void cancel(IReferable ref) {
            if (ref == null) return;
            final String refKey = createRefKey(ref);
            Set<Integer> seqSet = mRefIndex.remove(refKey);
            if (seqSet != null && !seqSet.isEmpty()) {
                this.cancel(seqSet.toArray(new Integer[seqSet.size()]));
            }
        }

        private void cancel(Integer[] seqArr) {
            if (seqArr == null || seqArr.length == 0) {
                return;
            }
            for (Integer seq : seqArr) {
                if (seq == null) continue;
                mTaskIndex.remove(seq);
            }
        }

        /** 取消线程池中某个还未执行的任务 */
        public synchronized void cancel(Runnable runnable) {
            if (isAlive()) {
                getQueue().remove(runnable);
            }
        }

        public synchronized boolean contains(Runnable runnable) {
            if (isAlive()) {
                return getQueue().contains(runnable);
            } else {
                return false;
            }
        }

        @Override
        public void shutdown() {
            if (isAlive()) {
                super.shutdown();
            }
        }

        @NonNull
        @Override
        public List<Runnable> shutdownNow() {
            if (isAlive()) {
                return super.shutdownNow();
            }
            return new ArrayList<>(0);
        }
    }

    private static final class TaskCounter {
        private static final String TAG = "TaskCounter";
        private static TaskCounter INSTANCE = null;
        private final AtomicInteger mCounter = new AtomicInteger(0);

        public static TaskCounter getDefault() {
            if (INSTANCE == null) {
                synchronized (TaskCounter.class) {
                    if (INSTANCE == null) {
                        INSTANCE = new TaskCounter();
                    }
                }
            }
            return INSTANCE;
        }

        private TaskCounter() {
        }

        public int getCount() {
            return mCounter.get();
        }

        public Runnable wrap(Runnable runnable) {
            return new TaskCounter.CounterRunnable(mCounter, runnable);
        }

        private static class CounterRunnable implements Runnable {
            private final AtomicInteger counter;
            private final Runnable runnable;

            CounterRunnable(AtomicInteger counter, Runnable runnable) {
                this.counter = counter;
                this.runnable = runnable;
            }

            @Override
            public void run() {
                int count = counter.incrementAndGet();
                final int taskId = Math.abs(runnable.hashCode());
                final long t0 = TimeUtils.getRealTime();
                //Log.i(TAG, "[taskId:%s]task start,total count = %s,runnable = %s %s",taskId, count, runnable,(count <= 16 ? "" : ",[Task Too Many!]"));
                try {
                    runnable.run();
                } catch (Throwable t) {
                    t.printStackTrace();
                    Log.e(TAG, "[taskId:"+taskId+"]task run exception,runnable = " + runnable, t);
                    throw t;
                } finally {
                    count = counter.decrementAndGet();
                    //Log.i(TAG, "[taskId:%s]task end,duration = %sms,total count = %s,runnable = %s", taskId,(TimeUtils.getRealTime() - t0),count, runnable);
                }
            }
        }
    }
}
