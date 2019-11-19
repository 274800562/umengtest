package com.qz.testdemo;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.qz.testdemo.bean.ExtraBean;

import androidx.appcompat.app.AppCompatActivity;

public class ListActivity extends AppCompatActivity {

    public static final String TAG = "ListActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExtraBean extraBean= (ExtraBean) getIntent().getSerializableExtra("bean");
        String id = String.valueOf(extraBean.getId());
        String title = extraBean.getTitle();
        Log.e(TAG, "umeng  id:" + id + "-" + "title:" + title);

        TextView textView = findViewById(R.id.textview);
        textView.setText("id:"+id+"\n"+"title:"+title);
    }
}
