package com.wang.myapplication.explosion;

import android.graphics.Bitmap;
import android.graphics.Rect;

public abstract class ParticalFactory {
    public abstract Partical[] generateParticales(Bitmap bitmap, Rect bound);
}
