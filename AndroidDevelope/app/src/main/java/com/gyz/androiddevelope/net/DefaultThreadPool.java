package com.gyz.androiddevelope.net;

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: guoyazhou
 * @date: 2016-01-13 14:19
 */
public class DefaultThreadPool {
    private static final String TAG = "DefaultThreadPool";

    // 阻塞队列最大任务数量
    public static final int BLOCKING_QUEUE_SIZE = 20;
    public static final int THREAD_POOL_MAX_THREAD = 10;
    public static final int CORE_THREAD_POOL_SIZE = 5;

    /**
     * 缓冲BaseRequest任务队列
     */
    static ArrayBlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(BLOCKING_QUEUE_SIZE);
    private static DefaultThreadPool instance = null;
    /**
     * 线程池，目前是十个线程，
     */
    static AbstractExecutorService pool = new ThreadPoolExecutor(CORE_THREAD_POOL_SIZE, THREAD_POOL_MAX_THREAD, 15L, TimeUnit.SECONDS, blockingQueue, new ThreadPoolExecutor.DiscardOldestPolicy());

    public static synchronized DefaultThreadPool getInstance() {
        if (instance == null) {
            instance = new DefaultThreadPool();
        }
        return instance;
    }

    public static void removeAllTask() {
        blockingQueue.clear();
    }

    public static void removeTaskFromQueue(Object object) {
        blockingQueue.remove(object);
    }

    /**
     * 关闭，并等待任务执行完成，不接受新任务
     */
    public static void shutdown() {
        if (pool != null) {
            pool.shutdown();
        }
    }

    /**
     * 关闭，立即关闭，并挂起所有正在执行的线程，不接受新任务
     */
    public static void shutdownRightNow() {

        if (pool != null) {
            pool.shutdownNow();
        }
        try {
            // 设置超时极短，强制关闭所有任务
            pool.awaitTermination(1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void execute(final Runnable runnable) {
        if (runnable != null) {
            try {
                pool.execute(runnable);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
