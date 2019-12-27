package com.wang.minecomponent;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.wang.annotations.BindPath;

@BindPath("mine/second")
public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
    }
}
