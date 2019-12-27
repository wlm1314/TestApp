package com.wang.uiapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

import com.wang.uiapp.menu.MenuBgView;

public class MainActivity extends AppCompatActivity {

    MenuBgView menuBgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        final SplashView2 splashView2 = new SplashView2(this);
//
//        FrameLayout frameLayout = findViewById(R.id.root);
//        frameLayout.addView(splashView2);
//
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                splashView2.splashDisappear();
//            }
//        }, 5000);

        menuBgView = new MenuBgView(this);
        menuBgView.setColor(Color.RED);

        setContentView(menuBgView);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        menuBgView.setTouchY(event, 0.8f);
        return super.onTouchEvent(event);
    }
}
