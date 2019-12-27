package com.wang.myapplication.explosion;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

public class FallingParticalFactory extends ParticalFactory {
    public static final int PART_WH = 20;//默认小球区域宽高
    @Override
    public Partical[] generateParticales(Bitmap bitmap, Rect bound) {
        int w = bound.width();
        int h = bound.height();

        int partW_count = w /PART_WH;//列数
        int partH_count = h /PART_WH;//行数
        partW_count = partW_count > 0 ? partW_count : 1;
        partH_count = partH_count > 0 ? partH_count : 1;

        int bitmap_part_w = bitmap.getWidth() /partW_count;//列
        int bitmap_part_h = bitmap.getHeight() /partH_count;//行

        List<Partical> particals = new ArrayList<>();
        for (int row = 0; row < partH_count; row++) {
            for (int column = 0; column < partW_count; column++) {
                //取得当前粒子所在位置的颜色
                int color = bitmap.getPixel(column * bitmap_part_w, row * bitmap_part_h);
                if (Color.alpha(color) ==0) continue;
                float x = bound.left + PART_WH * column;
                float y = bound.top + PART_WH * row;

                particals.add(new FallingPartical(x, y, color, bound));
            }
        }
        Partical partical[] = new Partical[particals.size()];
        return particals.toArray(partical);
    }
}
