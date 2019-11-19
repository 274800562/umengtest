package com.qz.fvideo;

import android.content.Intent;
import android.os.Bundle;

import com.qz.fvideo.bean.ExtraBean;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    public static final String TAG = "SplashActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int tag = getIntent().getIntExtra("tag", 0);

        //1.其他操作 省略


        //2.跳主界面
        Intent intent = new Intent(this,MainActivity.class);
        /**
         * tag==1时说明是点击离线通知过来的，需要携带数据跳转
         */
        if (tag == 1) {
            ExtraBean extraBean= (ExtraBean) getIntent().getSerializableExtra("bean");
            intent.putExtra("bean", extraBean);
            intent.putExtra("tag", tag);
        }
        startActivity(intent);

    }
}
