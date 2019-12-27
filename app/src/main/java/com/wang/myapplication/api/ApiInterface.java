package com.wang.myapplication.api;

import com.wang.network.beans.JHBaseResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("historyWeather/province")
    Observable<JHBaseResponse<List<ProvinceBean>>> getProvince();
}
