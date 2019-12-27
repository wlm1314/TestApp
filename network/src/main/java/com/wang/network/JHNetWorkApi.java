package com.wang.network;

import com.wang.network.base.NetWorkApi;
import com.wang.network.beans.JHBaseResponse;
import com.wang.network.errorhandler.ExceptionHandle;

import io.reactivex.functions.Function;
import okhttp3.Interceptor;

public class JHNetWorkApi extends NetWorkApi {
    private static JHNetWorkApi sInstance;
    public static JHNetWorkApi getInstance(){
        if (sInstance == null){
            synchronized (JHNetWorkApi.class){
                if (sInstance == null){
                    sInstance = new JHNetWorkApi();
                }
            }
        }
        return sInstance;
    }

    protected JHNetWorkApi() {
        super("http://v.juhe.cn/");
    }

    public static <T> T getService(Class<T> service) {
        return getInstance().getRetrofit(service).create(service);
    }

    @Override
    protected Interceptor getInterceptor() {
        return null;
    }

    @Override
    protected Function<JHBaseResponse, JHBaseResponse> getAppErrorHandler() {
        return new Function<JHBaseResponse, JHBaseResponse>() {
            @Override
            public JHBaseResponse apply(JHBaseResponse response) {
                if (response.getError_code() != 0) {
                    ExceptionHandle.ServerException exception = new ExceptionHandle.ServerException();
                    exception.code = response.getError_code();
                    exception.message = response.getReason();
                    throw exception;
                }
                return response;
            }
        };
    }
}
