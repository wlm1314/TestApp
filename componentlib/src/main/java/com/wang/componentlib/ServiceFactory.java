package com.wang.componentlib;

public class ServiceFactory {

    private static final ServiceFactory instance = new ServiceFactory();
    private ILoginService mLoginService;
    private IMineService mMineService;

    public static ServiceFactory getInstance() {
        return instance;
    }

    public ILoginService getLoginService() {
        return mLoginService;
    }

    public void setLoginService(ILoginService loginService) {
        this.mLoginService = loginService;
    }

    public IMineService getMineService() {
        return mMineService;
    }

    public void setMineService(IMineService mineService) {
        this.mMineService = mineService;
    }
}
