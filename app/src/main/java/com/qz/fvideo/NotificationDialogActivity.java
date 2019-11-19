package com.qz.fvideo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qz.fvideo.bean.ExtraBean;

import androidx.appcompat.app.AppCompatActivity;

public class NotificationDialogActivity extends AppCompatActivity {

    private ExtraBean extraBean;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_show_notification);


        textView = findViewById(R.id.textview);
        TextView tvCancel = findViewById(R.id.tv_cancel);
        TextView tvConfirm = findViewById(R.id.tv_confirm);


        extraBean = (ExtraBean) getIntent().getSerializableExtra("bean");

        textView.setText(extraBean.getTitle());
        final int type = extraBean.getType();

       tvConfirm.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=null;
               if (type == 1) {
                   intent = new Intent(NotificationDialogActivity.this, DetailActivity.class);
               } else {
                   intent = new Intent(NotificationDialogActivity.this, ListActivity.class);
               }
               intent.putExtra("bean", extraBean);
               startActivity(intent);
               finish();
           }
       });
       tvCancel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });

    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        extraBean = (ExtraBean) intent.getSerializableExtra("bean");
        textView.setText(extraBean.getTitle());
    }
}
