package com.wang.http;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 服务器返回结果之后调用这个类，转换成用户需要的类型
 */
public class JsonCallBackListener<T> implements CallBackListener {
    private Class<T> response;
    //外部的回调接口
    private IJsonDataListener jsonDataListener;

    private Handler handler = new Handler(Looper.getMainLooper());

    public JsonCallBackListener(Class<T> response, IJsonDataListener jsonDataListener) {
        this.response = response;
        this.jsonDataListener = jsonDataListener;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        String jsonData = getContent(inputStream);
        final T t = new Gson().fromJson(jsonData, response);
        handler.post(new Runnable() {
            @Override
            public void run() {
                jsonDataListener.onSuccess(t);
            }
        });
    }

    @Override
    public void onFailure() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                jsonDataListener.onFailure();
            }
        });
    }

    /**
     * inputStream转为String类型
     * @param inputStream
     * @return
     */
    private String getContent(InputStream inputStream){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = bufferedReader.readLine()) != null){
                sb.append(line+"\n");
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
