package com.qz.fvideo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.qz.fvideo.bean.ExtraBean;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int tag = getIntent().getIntExtra("tag", 0);
        if (tag == 1) {//点击通知跳转过来的，有携带数据，需要执行跳转逻辑，根据typeitao跳对应界面
            ExtraBean extraBean = (ExtraBean) getIntent().getSerializableExtra("bean");
            int type = extraBean.getType();
            Log.e(TAG, "umeng  type:" + type);
            Intent intent = null;
            if (type == 1) {
                intent = new Intent(this, DetailActivity.class);
            } else {
                intent = new Intent(this, ListActivity.class);
            }
            intent.putExtra("bean", extraBean);
            startActivity(intent);
        }


    }
}
