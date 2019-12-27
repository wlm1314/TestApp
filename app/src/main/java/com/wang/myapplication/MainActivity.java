package com.wang.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.wang.http.IJsonDataListener;
import com.wang.http.WsyHttp;
import com.wang.myapplication.api.ApiInterface;
import com.wang.myapplication.api.ProvinceBean;
import com.wang.myapplication.entity.RequestBean;
import com.wang.myapplication.entity.ResultBean;
import com.wang.network.JHNetWorkApi;
import com.wang.network.beans.JHBaseResponse;
import com.wang.network.errorhandler.ExceptionHandle;
import com.wang.network.observer.BaseObserver;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    String url = "http://v.juhe.cn/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendRequest(View view) {
        JHNetWorkApi.getService(ApiInterface.class)
                .getProvince()
                .compose(JHNetWorkApi.getInstance().appluSchedulers(new BaseObserver<JHBaseResponse<List<ProvinceBean>>>() {
                    @Override
                    public void onSuccess(JHBaseResponse<List<ProvinceBean>> listJHBaseResponse) {
                        Log.e(TAG, new Gson().toJson(listJHBaseResponse));
                    }

                    @Override
                    public void onFailure(ExceptionHandle.ResponseThrowable e) {
                        Log.e(TAG, "code:" + e.code + " message:" + e.message);
                    }
                }));
    }

    private void HttpUrlConnection() {
        WsyHttp.sendRequest(url, new RequestBean("2", "d6932db06ca86a6914511c3509be30e0"), ResultBean.class, new IJsonDataListener<ResultBean>() {
            @Override
            public void onSuccess(ResultBean resultBean) {
                Log.e("WsyHttp=========", "请求成功" + resultBean.getError_code() + "--------" + resultBean.getReason());
            }

            @Override
            public void onFailure() {
                Log.e("WsyHttp=========", "请求失败");
            }
        });
    }
}
