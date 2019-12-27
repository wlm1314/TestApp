package com.wang.http;

import java.io.InputStream;

/**
 * 接收结果的回调接口 给框架层
 */
public interface CallBackListener {
    //请求成功
    void onSuccess(InputStream inputStream);
    //请求失败
    void onFailure();
}
