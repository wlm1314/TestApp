package com.wang.minecomponent;

import android.app.Application;

import com.wang.componentlib.IAppComponent;
import com.wang.componentlib.ServiceFactory;

public class MineApplication extends Application implements IAppComponent {
    private static Application application;

    public static Application getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initialize(this);
    }

    @Override
    public void initialize(Application app) {
        application = app;
        ServiceFactory.getInstance().setMineService(new MineService());
    }
}
