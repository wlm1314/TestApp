package com.wang.uiapp.paint;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.wang.uiapp.load.LoadView;

public class PaintActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(new RadarGradientView(this));
//        setContentView(new ZoomImageView(this));
//        setContentView(R.layout.activity_paint);
//        setContentView(new MyGradientView(this));

//        WaveView waveView = new WaveView(this);
//        setContentView(waveView);
//        waveView.startAnimation();
        LoadView loadView = new LoadView(this);
        setContentView(loadView);

        loadView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("loadView","哈哈哈哈哈哈");
            }
        });
    }


}
