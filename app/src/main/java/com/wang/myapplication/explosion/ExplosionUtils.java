package com.wang.myapplication.explosion;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

import java.util.Random;

public class ExplosionUtils {

    public static final Random RANDOM = new Random(System.currentTimeMillis());
    private static final Canvas CANVAS = new Canvas();
    private static final float DENSITY = Resources.getSystem().getDisplayMetrics().density;

    public static int dp2px(int dp) {
        return Math.round(dp * DENSITY);
    }

    public static Bitmap createBItmapFromView(View view){
        view.clearFocus();//使View失去焦点，回复原本样式
        Bitmap bitmap = createBitmapSafely(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888, 1);
        if (bitmap != null){
            synchronized (CANVAS){
                CANVAS.setBitmap(bitmap);
                view.draw(CANVAS);//使用view的回执方法生成View的备份
                CANVAS.setBitmap(null);
            }
        }
        return bitmap;
    }

    public static Bitmap createBitmapSafely(int width, int height, Bitmap.Config config, int retryCount){
        try {
            return Bitmap.createBitmap(width, height, config);
        } catch (OutOfMemoryError e){
            e.printStackTrace();
            if (retryCount >0){
                System.gc();
                return createBitmapSafely(width, height, config, retryCount -1);
            }
            return null;
        }
    }
}
