package com.wang.butterknife;

import android.app.Activity;
import android.util.Log;

public class ButterKnife {

    public static void bind(Activity activity){
        String className = activity.getClass().getName()+"$ViewBinder";
        Log.e("className", className);
        try {
            IViewBinder viewBinder = (IViewBinder) Class.forName(className).newInstance();
            viewBinder.bind(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
