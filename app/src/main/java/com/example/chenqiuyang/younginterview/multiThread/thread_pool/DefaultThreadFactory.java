package com.example.chenqiuyang.younginterview.multiThread.thread_pool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultThreadFactory implements ThreadFactory {
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;
    private final int threadPriority;

    public DefaultThreadFactory( String threadNamePrefix) {
        this(Thread.NORM_PRIORITY,threadNamePrefix);
    }

    public DefaultThreadFactory(int threadPriority, String threadNamePrefix) {
        this.threadPriority = threadPriority;
        group = Thread.currentThread().getThreadGroup();
        namePrefix = threadNamePrefix + "-t-";
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
        if (t.isDaemon()) t.setDaemon(false);
        t.setPriority(threadPriority);
        return t;
    }
}