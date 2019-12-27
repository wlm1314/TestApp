package com.wang.logincomponent;

import android.app.Application;

import com.wang.componentlib.IAppComponent;
import com.wang.componentlib.ServiceFactory;

public class LoginApplication extends Application implements IAppComponent {
    private static Application application;

    public static Application getApplication(){
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
        ServiceFactory.getInstance().setLoginService(new LoginService());
    }
}
