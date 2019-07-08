package com.shouzan.back.constant.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: com.shouzan.back.constant.thread.SingleThreadPoolExecutor
 * @Author: bin.yang
 * @Date: 2019/6/18 14:47
 * @Description: TODO  单例线程池
 */
public class SingleThreadPoolExecutor {

    private volatile static ThreadPoolExecutor executorService;

    private volatile static ThreadPoolExecutor singleExecutor;

    /**
     * @Description: 单例定长线程池
     * @param size
     * @[param] [size]
     * @return java.util.concurrent.ThreadPoolExecutor
     * @author:  bin.yang
     * @date:  2019/6/18 4:11 PM
     */
    public static ThreadPoolExecutor getFixedThreadPool(int size) {

        if (executorService == null) {

            synchronized (SingleThreadPoolExecutor.class) {

                if (executorService == null) {

                    executorService = new ThreadPoolExecutor(
                            size, // 核心线程数
                            size, // 最大线程数
                            0L, // 闲置线程存活时间
                            TimeUnit.MILLISECONDS, // 时间单位
                            new LinkedBlockingQueue<Runnable>()); // 线程队列

                }
            }
        }
        return executorService;
    }

    /**
     * @Description: 单例唯一线程池
     * @param
     * @[param] []
     * @return java.util.concurrent.ThreadPoolExecutor
     * @author:  bin.yang
     * @date:  2019/6/18 4:11 PM
     */
    public static ThreadPoolExecutor getSingleThreadPool() {

        if (singleExecutor == null) {

            synchronized (SingleThreadPoolExecutor.class) {

                if (singleExecutor == null) {

                    singleExecutor = new ThreadPoolExecutor(
                            1, // 核心线程数
                            1, // 最大线程数
                            0L, // 闲置线程存活时间
                            TimeUnit.MILLISECONDS, // 时间单位
                            new LinkedBlockingQueue<Runnable>()); // 线程队列

                }
            }
        }
        return singleExecutor;
    }

}

