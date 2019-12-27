package com.wang.minecomponent;

import android.content.Context;
import android.content.Intent;

import com.wang.componentlib.IMineService;

public class MineService implements IMineService {
    @Override
    public void launch(Context context, int userId) {
        Intent intent = new Intent(context, MineActivity.class);
        intent.putExtra("userId", userId);
        context.startActivity(intent);
    }
}
