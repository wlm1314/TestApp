package com.wang.http;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadManager {
    private static ThreadManager threadManager = new ThreadManager();
    //请求任务队列
    private LinkedBlockingQueue<Runnable> mQueue = new LinkedBlockingQueue<>();
    private DelayQueue<Delayed> failedQueue = new DelayQueue<>();
    //线程池
    private ThreadPoolExecutor threadPoolExecutor;

    private ThreadManager() {
        threadPoolExecutor = new ThreadPoolExecutor(3, 10, 15, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(4), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
                addTask(runnable);
            }
        });
        threadPoolExecutor.execute(runnable);
        threadPoolExecutor.execute(failedRunnable);
    }

    public static ThreadManager getInstance() {
        return threadManager;
    }

    /**
     * 将请求任务添加进任务队列
     *
     * @param runnable
     */
    public void addTask(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        try {
            mQueue.put(runnable);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 核心线程 一直去队列中获取请求任务，然后丢给线程池去执行
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true){
                try {
                    Runnable runnable = mQueue.take();
                    threadPoolExecutor.execute(runnable);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    /**
     * 将请求任务添加进任务队列
     *
     * @param httpTask
     */
    public void addFailedTask(HttpTask httpTask) {
        if (httpTask == null) {
            return;
        }
        httpTask.setDelayTime(3000);
        failedQueue.offer(httpTask);
    }

    /**
     * 核心线程 一直去队列中获取请求任务，然后丢给线程池去执行
     */
    private Runnable failedRunnable = new Runnable() {
        @Override
        public void run() {
            while (true){
                try {
                    HttpTask take = (HttpTask) failedQueue.take();
                    if(take.getFailedNum() < 3){
                        take.setFailedNum(take.getFailedNum()+1);
                        threadPoolExecutor.execute(take);
                        Log.e("请求重试机制=========", "重试次数"+take.getFailedNum());
                    }else{
                        JsonHttpRequest jsonHttpRequest = (JsonHttpRequest) take.getHttpRequest();
                        jsonHttpRequest.getListener().onFailure();
                        Log.e("请求重试机制=========", "失败超过三次，我尽力了");

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}
