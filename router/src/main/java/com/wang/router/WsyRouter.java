package com.wang.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WsyRouter {
    private Map<String, Class<? extends Activity>> activityList;
    private static WsyRouter wsyRouter;
    private Context mContext;

    private WsyRouter(){
        activityList = new HashMap<>();
    }

    public void init(Context context){
        this.mContext = context;
        try {
            Set<String> classNames = ClassUtils.getFileNameByPackageName(mContext, "com.wang.util");
            for (String className:classNames){
                Class<?> clazz = Class.forName(className);
                Object object = clazz.newInstance();
                if (object instanceof IRouter){
                    ((IRouter) object).putActivity();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static WsyRouter getInstance(){
        if (wsyRouter == null){
            wsyRouter = new WsyRouter();
        }
        return wsyRouter;
    }

    public void putActivity(String path, Class<? extends Activity> activity){
        activityList.put(path, activity);
    }

    /**
     * 跳转
     * @param path
     * @param bundle
     */
    public void jumpActivity(String path, Bundle bundle){
        Class<? extends Activity> clazz = activityList.get(path);
        if (clazz == null) return;
        Intent intent = new Intent(mContext, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (bundle != null)
            intent.putExtra("bundle", bundle);
        mContext.startActivity(intent);
    }
}
