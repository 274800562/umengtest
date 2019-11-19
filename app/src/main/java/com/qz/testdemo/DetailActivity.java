package com.qz.testdemo;

import android.os.Bundle;
import android.widget.TextView;

import com.qz.testdemo.bean.ExtraBean;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    public static final String TAG = "DetailActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExtraBean extraBean= (ExtraBean) getIntent().getSerializableExtra("bean");
        String id = String.valueOf(extraBean.getId());
        String title = extraBean.getTitle();

        TextView textView = findViewById(R.id.textview);
        textView.setText("id:"+id+"\n"+"title:"+title);
    }
}
