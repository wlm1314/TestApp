package com.wang.myapplication.app;

import com.wang.network.BuildConfig;
import com.wang.network.base.INetworkRequiredInfo;

public class MyNetWork implements INetworkRequiredInfo {
    @Override
    public String getVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    @Override
    public int getVersionCode() {
        return BuildConfig.VERSION_CODE;
    }

    @Override
    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }
}
