package com.wang.network.commoninterceptor;

import com.wang.network.base.INetworkRequiredInfo;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CommonRequestInterceptor implements Interceptor {
    private INetworkRequiredInfo requiredInfo;

    public CommonRequestInterceptor(INetworkRequiredInfo iNetworkRequiredInfo) {
        this.requiredInfo = iNetworkRequiredInfo;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("os", "android");
        builder.addHeader("appVersion", this.requiredInfo.getVersionCode()+"");
        if (request.method().equals("GET")){
            HttpUrl url = request.url();
            HttpUrl newUrl = url.newBuilder().addQueryParameter("key", "d6932db06ca86a6914511c3509be30e0").build();
            request = builder.url(newUrl).build();
        }
        return chain.proceed(request);
    }
}
