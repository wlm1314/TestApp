package com.wang.myapplication.app;

import android.app.Application;

import com.wang.componentlib.AppConfig;
import com.wang.componentlib.IAppComponent;
import com.wang.network.base.NetWorkApi;
import com.wang.router.WsyRouter;

public class MyApp extends Application implements IAppComponent {

    @Override
    public void onCreate() {
        super.onCreate();
        NetWorkApi.init(new MyNetWork());
        initialize(this);

        WsyRouter.getInstance().init(this);
    }

    @Override
    public void initialize(Application app) {
        for (String compent: AppConfig.COMPONENTS){
            try {
                Class<?> clazz = Class.forName(compent);
                Object object = clazz.newInstance();
                if (object instanceof IAppComponent){
                    ((IAppComponent) object).initialize(app);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
