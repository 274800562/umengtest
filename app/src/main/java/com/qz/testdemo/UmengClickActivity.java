package com.qz.testdemo;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.google.gson.Gson;
import com.qz.testdemo.bean.ExtraBean;
import com.qz.testdemo.bean.UmengClickBean;
import com.umeng.message.UmengNotifyClickActivity;

import org.android.agoo.common.AgooConstants;

import java.util.List;

public class UmengClickActivity extends UmengNotifyClickActivity {
    private static String TAG = UmengClickActivity.class.getName();

    @Override
    public void onMessage(Intent intent0) {
        super.onMessage(intent0);  //此方法必须调用，否则无法统计打开数

        String body = intent0.getStringExtra(AgooConstants.MESSAGE_BODY);
        Log.e(TAG, "body:" + body);

        Gson gson = new Gson();
        //将接收到的body信息装换成UmengClickBean
        UmengClickBean bean = gson.fromJson(body, UmengClickBean.class);
        //ExtraBean包含三个字段：type,id,title,根据type跳转相应界面
        ExtraBean extraBean = bean.getExtra();
        if (null != bean) {
            int type = extraBean.getType();
            Intent intent = null;
            /**
             * 判断栈中是否有MainActivity，有则意味着程序已启动，否则没有
             */
            if (isExistMainActivity(MainActivity.class)) {
                if (type == 1) {
                    intent = new Intent(this, DetailActivity.class);
                } else {
                    intent = new Intent(this, ListActivity.class);
                }
            } else {
                intent = new Intent(this, SplashActivity.class);
                intent.putExtra("tag", 1);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            intent.putExtra("bean", extraBean);
            startActivity(intent);
            finish();
        }
    }

    @TargetApi(Build.VERSION_CODES.Q)
    private boolean isExistMainActivity(Class<?> activity) {
        Intent intent = new Intent(this, activity);
        ComponentName cmpName = intent.resolveActivity(getPackageManager());
        boolean flag = false;
        if (cmpName != null) { // 说明系统中存在这个activity    
            ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskInfoList =
                    am.getRunningTasks(10);
            //这里获取的是APP栈的数量，一般也就两个
            ActivityManager.RunningTaskInfo runningTaskInfo = taskInfoList.get(0);
            // 只是拿当前运行的栈
            int numActivities = taskInfoList.get(0).numActivities;
            for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
                if (taskInfo.baseActivity.equals(cmpName)) {// 说明它已经启动了
                    flag = true;
                    break;//跳出循环，优化效率
                }
            }
        }
        return flag;

    }

}
