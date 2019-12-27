package com.wang.http;

/**
 * 请求对象的顶层接口
 */
public interface IHttpRequest {
    //设置请求路径
    void setUrl(String url);
    //设置请求参数
    void setData(byte[] data);
    //设置回调接口
    void setListener(CallBackListener listener);
    //执行请求方法
    void execute();
}
