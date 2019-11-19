package com.qz.fvideo;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.google.gson.Gson;
import com.qz.fvideo.bean.ExtraBean;
import com.qz.fvideo.bean.UmengClickBean;
import com.umeng.message.UmengNotifyClickActivity;

import org.android.agoo.common.AgooConstants;

import java.util.List;

public class UmengClickActivity extends UmengNotifyClickActivity {
    private static String TAG = UmengClickActivity.class.getName();

    /**
     * app离线时推送的通知，无论当前app是否在线，在点击离线通知时，都会回调此方法
     * <p>
     * <p>
     * 所以就需要根据需要
     * 1：是调用SplashActivity->MainActivity：然后根据type跳转DetailActivity还是ListActivity
     * 此种场景是app离线时点击离线通知，会走此流程
     * <p>
     * 2：省略！！！调用SplashActivity->MainActivity，直接！！！根据type跳转DetailActivity或者ListActivity
     * 此种场景是app离线时接收到通知，启动app,然后再去点击离线时收到的通知。
     * <p>
     * <p>
     * 以上场景运用哪种，判断MainActivity是否存在，如果存在就不需要重新启动，如果不存在就走完整流程，即第1中场景
     */
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
            List<ActivityManager.RunningTaskInfo> taskInfoList = am.getRunningTasks(10);
            //这里获取的是APP栈的数量，一般也就两个
            ActivityManager.RunningTaskInfo runningTaskInfo = taskInfoList.get(0);// 只是拿当前运行的栈
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
