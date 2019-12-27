package com.wang.http;

public class WsyHttp {

    //请求路径 url
    //请求参数
    //接受结果的类型
    //接收结果的回调接口
    public static<T,M> void sendRequest(String url, T requestData, Class<M> response, IJsonDataListener listener){
        IHttpRequest httpRequest = new JsonHttpRequest();
        CallBackListener callBackListener = new JsonCallBackListener<>(response, listener);
        HttpTask<T> httpTask = new HttpTask<>(httpRequest, callBackListener, url, requestData);
        ThreadManager.getInstance().addTask(httpTask);
    }
}
