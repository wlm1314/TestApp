package com.wang.network.base;

import com.wang.network.commoninterceptor.CommonRequestInterceptor;
import com.wang.network.commoninterceptor.CommonResponseInterceptor;
import com.wang.network.errorhandler.HttpErrorHandler;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class NetWorkApi {

    private static INetworkRequiredInfo iNetworkRequiredInfo;
    private static Map<String, Retrofit> retrofitMap = new HashMap<>();
    private String mBaseUrl;
    private OkHttpClient mOkHttpClient;

    protected NetWorkApi(String mBaseUrl) {
        this.mBaseUrl = mBaseUrl;
    }

    public static void init(INetworkRequiredInfo networkRequiredInfo) {
        iNetworkRequiredInfo = networkRequiredInfo;
    }

    protected Retrofit getRetrofit(Class service) {
        if (retrofitMap.get(mBaseUrl + service.getName()) != null) {
            return retrofitMap.get(mBaseUrl + service.getName());
        }
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        retrofitBuilder.baseUrl(mBaseUrl);
        retrofitBuilder.addConverterFactory(GsonConverterFactory.create());
        retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        retrofitBuilder.client(getOkHttpClient());
        Retrofit retrofit = retrofitBuilder.build();
        retrofitMap.put(mBaseUrl + service.getName(), retrofit);
        return retrofit;
    }

    private OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            OkHttpClient.Builder okhttpBuild = new OkHttpClient.Builder();
            if (getInterceptor() != null) {
                okhttpBuild.addInterceptor(getInterceptor());
            }
            okhttpBuild.addInterceptor(new CommonRequestInterceptor(iNetworkRequiredInfo));
            okhttpBuild.addInterceptor(new CommonResponseInterceptor());
            if (iNetworkRequiredInfo != null && iNetworkRequiredInfo.isDebug()) {
                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                okhttpBuild.addInterceptor(httpLoggingInterceptor);
            }
            mOkHttpClient = okhttpBuild.build();
        }
        return mOkHttpClient;
    }

    public <T> ObservableTransformer<T, T> appluSchedulers(final Observer<T> observer) {
        ObservableTransformer<T, T> transformer = new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                Observable<T> observable = upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(NetWorkApi.this.<T>getAppErrorHandler())
                        .onErrorResumeNext(new HttpErrorHandler<T>());
                observable.subscribe(observer);
                return observable;
            }
        };
        return transformer;
    }

    protected abstract Interceptor getInterceptor();

    protected abstract <T> Function<T, T> getAppErrorHandler();

}
