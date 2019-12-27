package com.wang.http;

/**
 * 接收结果的回调接口 给应用层
 */
public interface IJsonDataListener<T> {
    void onSuccess(T t);
    void onFailure();
}
