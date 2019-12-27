package com.wang.myapplication;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wang.annotations.BindView;
import com.wang.butterknife.ButterKnife;
import com.wang.componentlib.ServiceFactory;
import com.wang.router.WsyRouter;

public class ComponentActivity extends AppCompatActivity {

    @BindView(R.id.login)
    TextView tvLogin;

    @BindView(R.id.gozujian)
    TextView tvGZJ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component);
        ButterKnife.bind(this);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager packageManager = getPackageManager();
                try {
                    ApplicationInfo applicationInfo = packageManager.getApplicationInfo("com.wang.myapplication", PackageManager.GET_META_DATA);
                    String channel_name = applicationInfo.metaData.getString("CHANNLE_NAME");
                    Toast.makeText(ComponentActivity.this, channel_name, Toast.LENGTH_SHORT).show();
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                //跳转登录组件
//                ServiceFactory.getInstance().getLoginService().launch(ComponentActivity.this, "login");
            }
        });

        tvGZJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                //跳转我的组件
//                ServiceFactory.getInstance().getMineService().launch(ComponentActivity.this, 1);
                WsyRouter.getInstance().jumpActivity("mine/mine", null);
            }
        });

        findViewById(R.id.showUI).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示登录返回的fragment
                Bundle bundle = new Bundle();
                ServiceFactory.getInstance().getLoginService().newUserInfoFragment(getSupportFragmentManager(), R.id.container, bundle);
            }
        });
    }
}
