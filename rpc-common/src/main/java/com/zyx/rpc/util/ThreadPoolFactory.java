package com.zyx.rpc.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * 创建线程池
 */
public class ThreadPoolFactory {

    private static final int CORE_POOL_SIZE = 10;
    private static final int MAXIMUM_POOL_SIZE = 100;
    private static final int KEEP_ALIVE_TIME = 1;
    private static final int BLOCKING_QUEUE_CAPACITY = 100;

    private ThreadPoolFactory() {}

    public static ExecutorService createDefaultThreadPool(String threadnamePrefix) {
        return createDefaultThreadPool(threadnamePrefix, false);
    }

    public static ExecutorService createDefaultThreadPool(String threadnamePrefix, Boolean daemon) {
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(BLOCKING_QUEUE_CAPACITY);
        ThreadFactory threadPoolFactory = createThreadFactory(threadnamePrefix, daemon);
        return new ThreadPoolExecutor(CORE_POOL_SIZE,
                MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.MINUTES,
                workQueue, threadPoolFactory);
    }


    /**
     * 创建ThreadFactory, threadNamePrefix不为空则自建ThreadFactory,
     * 否则使用defaultThreadFactory
     * @param threadNamePrefix
     * @param daemon
     * @return
     */
    private static ThreadFactory createThreadFactory(String threadNamePrefix, Boolean daemon) {
        if (threadNamePrefix != null) {
            return new ThreadFactoryBuilder().setNameFormat(threadNamePrefix + "-%d").setDaemon(daemon).build();
        } else {
            return new ThreadFactoryBuilder().setNameFormat(threadNamePrefix + "-%d").build();
        }
    }

}
