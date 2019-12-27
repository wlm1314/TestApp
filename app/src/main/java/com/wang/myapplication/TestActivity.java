package com.wang.myapplication;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wang.myapplication.animator.AnimatorPath;
import com.wang.myapplication.explosion.ExplosionView;
import com.wang.myapplication.explosion.FallingParticalFactory;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        findViewById(R.id.btn_anim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();
            }
        });

        ExplosionView explosionView = new ExplosionView(this, new FallingParticalFactory());
        explosionView.addListener(findViewById(R.id.image));
    }

    private void anim(){
        AnimatorPath mPath = new AnimatorPath();
        mPath.lineTo(100, 100);
        mPath.cubicTo(200, 200, 300, 0, 300, 100);
        mPath.lineTo(800, 200);
        mPath.cubicTo(200, 200, 300, 0, 0, 0);

        mPath.startAnimation(findViewById(R.id.iv_img), 5000);
    }
}
