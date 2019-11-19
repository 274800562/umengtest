package com.qz.fvideo;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;


import com.qz.fvideo.bean.ExtraBean;
import com.qz.fvideo.dao.DaoMaster;
import com.qz.fvideo.dao.DaoSession;
import com.qz.fvideo.dao.MyOpenHelper;

import java.util.List;

public class MyApplacation extends Application {
    public static final String TAG = "MyApplication";
    private static MyApplacation app;
    private DaoSession daoSession;
    private int mActivityCount = 0;


    public static MyApplacation getMyApplication() {
        return app;
    }



    @Override
    public void onCreate() {
        super.onCreate();
        app = this;//
        UmPushHelper.getInstance().init();//初始化友盟推送
        initDb();//初始化数据库
        /**
         * 通过mActivityCount判断当前应用在前台还是在后台
         */
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Log.d(TAG, "onActivityCreated");
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Log.d(TAG, "onActivityStarted");
                mActivityCount++;
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Log.d(TAG, "onActivityResumed");
                /**
                 * 后台切前台的时候，判断是否有未弹框的通知消息，如果有遍历弹框
                 *
                 * 说明：此处设计的是弹框叠加，如果只弹一个框，luanchMode="SingleTop"
                 * 并在NotificationDialogActivity内的onNewIntent()方法内处理逻辑
                 *
                 */
                List<ExtraBean> list = getDaoSession().getExtraBeanDao().queryBuilder().list();
                if (null != list && list.size() > 0) {
                    for (ExtraBean bean : list) {
                        Intent intent = new Intent(activity, NotificationDialogActivity.class);
                        intent.putExtra("bean", bean);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//必须
                        activity.startActivity(intent);
                    }

                    getDaoSession().getExtraBeanDao().deleteAll();
                }



            }

            @Override
            public void onActivityPaused(Activity activity) {
                Log.d(TAG, "onActivityPaused");
            }

            @Override
            public void onActivityStopped(Activity activity) {

                Log.d(TAG, "onActivityStopped");
                mActivityCount--;
                if (mActivityCount == 0) {//此时切后台

                }

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                Log.d(TAG, "onActivitySaveInstanceState");
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.d(TAG, "onActivityDestroyed");
            }
        });
    }

    public int getActivityCount(){
        return mActivityCount;
    }

    public void initDb() {
        MyOpenHelper helper = new MyOpenHelper(this, "test.db", null);
        //对数据库库加密
//        Database db = helper.getEncryptedWritableDb(UUID);
        //不加密,uyi
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }


    public DaoSession getDaoSession() {
        return daoSession;
    }

}
