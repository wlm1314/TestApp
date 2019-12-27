package com.wang.http;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonHttpRequest implements IHttpRequest {
    private String url;
    private byte[] data;
    private CallBackListener listener;
    private HttpURLConnection httpURLConnection;

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public void setListener(CallBackListener listener) {
        this.listener = listener;
    }

    @Override
    public void execute() {
        URL url;
        try {
            url = new URL(this.url);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(6000);//连接超时时间
            httpURLConnection.setUseCaches(false);//不使用缓存
            httpURLConnection.setInstanceFollowRedirects(true);//成员变量 设置当前这个对象 仅作用于当前函数
            httpURLConnection.setReadTimeout(6000);//响应超时时间
            httpURLConnection.setDoInput(true);//设置这个连接是否可以写入数据
            httpURLConnection.setDoOutput(true);//设置这个连接是否可以输出数据
            httpURLConnection.setRequestMethod("POST");//设置请求的方法
            httpURLConnection.setRequestProperty("Content-Type", "application.json;charset=UTF-8");
            httpURLConnection.connect();//建立连接

            //使用字节流发送数据
            OutputStream os = httpURLConnection.getOutputStream();
            //缓冲字节流 包装字节流
            BufferedOutputStream bos = new BufferedOutputStream(os);
            //把字节流数据写入缓冲区
            bos.write(data);
            bos.flush();
            os.close();
            bos.close();

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream in = httpURLConnection.getInputStream();
                //回调内部的回调接口
                listener.onSuccess(in);
            }else{
                throw new RuntimeException("请求失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("请求失败");
        }
    }

    public CallBackListener getListener() {
        return listener;
    }
}
