package com.wang.minecomponent;

import com.wang.router.IRouter;
import com.wang.router.WsyRouter;

public class RouterUtils implements IRouter {
    @Override
    public void putActivity() {
        WsyRouter.getInstance().putActivity("mine/mine", MineActivity.class);
    }
}
