package com.wang.http;

import android.util.Log;

import com.google.gson.Gson;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class HttpTask<T> implements Runnable, Delayed {
    private IHttpRequest httpRequest;
    private int failedNum;
    private long delayTime;

    public HttpTask(IHttpRequest httpRequest, CallBackListener callBackListener, String url, T requestData){
        this.httpRequest = httpRequest;
        this.httpRequest.setUrl(url);
        this.httpRequest.setListener(callBackListener);
        if (requestData != null){
            String request = new Gson().toJson(requestData);
            Log.e("param", request);
            try {
                this.httpRequest.setData(request.getBytes("utf-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public long getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(long delayTime) {
        this.delayTime = delayTime + System.currentTimeMillis();
    }

    public int getFailedNum() {
        return failedNum;
    }

    public void setFailedNum(int failedNum) {
        this.failedNum = failedNum;
    }

    @Override
    public void run() {
        try {
            this.httpRequest.execute();
        }catch(Exception e){
            ThreadManager.getInstance().addFailedTask(this);
        }
    }

    @Override
    public long getDelay(TimeUnit timeUnit) {
        return timeUnit.convert(getDelayTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed delayed) {
        return 0;
    }

    public IHttpRequest getHttpRequest() {
        return httpRequest;
    }
}
