package com.qz.fvideo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.qz.fvideo.bean.ExtraBean;
import com.qz.fvideo.dao.ExtraBeanDao;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import org.android.agoo.huawei.HuaWeiRegister;
import org.android.agoo.mezu.MeizuRegister;
import org.android.agoo.xiaomi.MiPushRegistar;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Self on 2019-08-23.
 */
public class UmPushHelper {
    public static final String TAG = "UmPushHelper";
    static UmPushHelper umPushHelper;
    Context context = MyApplacation.getMyApplication();
    private PushAgent mPushAgent;

    public static UmPushHelper getInstance() {
        if (null == umPushHelper) {
            umPushHelper = new UmPushHelper();
        }
        return umPushHelper;
    }

    /**
     * 友盟初始化
     * <p>
     * 友盟初始化及相关配置
     * 注册
     * 通道配置
     */

    public void init() {
//
        UMConfigure.init(context, "Appkey", "Umeng",
                UMConfigure.DEVICE_TYPE_PHONE, "Umeng Message Secret");
        UMConfigure.setLogEnabled(true);//日志输出，上线关闭
        mPushAgent = PushAgent.getInstance(context);

        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                Log.i(TAG, "注册成功：deviceToken：-------->  " + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.e(TAG, "注册失败：-------->  " + "s:" + s + ",s1:" + s1);
            }
        });


        //小米通道
        MiPushRegistar.register(context, "xiaomiId", "xiaomiKey");
        //华为通道
        HuaWeiRegister.register(MyApplacation.getMyApplication());
        //魅族通道
        MeizuRegister.register(context, "appId", "appKey");

        mPushAgent.onAppStart();
        //最多展示条数
        mPushAgent.setDisplayNotificationNumber(10);

        //通知或消息接收
        setMessageHandler();
        //通知点击
        setNotificationClickHandler();

    }
    /**
     * MessageHandler有很多回调方法，根据自己需要选择
     */
    private void setMessageHandler() {
        UmengMessageHandler messageHandler = new UmengMessageHandler() {


            @Override
            public void dealWithNotificationMessage(Context context, UMessage uMessage) {

                super.dealWithNotificationMessage(context, uMessage);

                Log.e(TAG, "um notification msg.extra" + uMessage.extra);
                try {
                    JSONObject object = new JSONObject(uMessage.extra);

                    int type = object.getInt("type");
                    Long id = object.getLong("id");
                    String title = object.getString("title");

                    ExtraBean bean = new ExtraBean();
                    bean.setId(id);
                    bean.setType(type);
                    bean.setTitle(title);

                    /**
                     * 应用不在前台时，不弹框，并把推送的数据存起来
                     *
                     * 在前台时直接弹框
                     */

                    if (MyApplacation.getMyApplication().getActivityCount() == 0) {
                        ExtraBeanDao dao = MyApplacation.getMyApplication().getDaoSession().getExtraBeanDao();
                        dao.insertOrReplace(bean);
                        return;
                    }

                    Intent intent = new Intent(context, NotificationDialogActivity.class);
                    intent.putExtra("bean", bean);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        };

        PushAgent.getInstance(context).setMessageHandler(messageHandler);
    }

    private void setNotificationClickHandler() {
        /**
         * 自定义行为的回调处理，参考文档：高级功能-通知的展示及提醒-自定义通知打开动作
         * UmengNotificationClickHandler是在BroadcastReceiver中被调用，故
         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
         * */
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {

            @Override
            public void launchApp(Context context, UMessage msg) {
                Log.e("umenguuu", "helper launchApp extra:" + msg.extra.toString());

                super.launchApp(context, msg);
            }

            @Override
            public void openUrl(Context context, UMessage msg) {
                Log.e("umenguuu", "helper openUrl extra:" + msg.extra.toString());

                super.openUrl(context, msg);
            }

            @Override
            public void openActivity(Context context, UMessage msg) {
                //获取extra字段值
                Log.e("umenguuu", "helper openActivity extra:" + msg.extra.toString());
                try {

                    JSONObject object = new JSONObject(msg.extra);
                    int type = object.getInt("type");
                    long id = object.getLong("id");
                    String title = object.getString("title");

                    ExtraBean extraBean = new ExtraBean();
                    extraBean.setId(id);
                    extraBean.setType(type);
                    extraBean.setTitle(title);

                    Intent intent;
                    Log.e("umenguuu", "helper type:" + type);

                    /**
                     * type=1 跳详情界面
                     * type=2 跳列表界面
                     */
                    if (type == 1) {
                        intent = new Intent(context, DetailActivity.class);
                    } else {
                        intent = new Intent(context, ListActivity.class);
                    }
                    intent.putExtra("bean", extraBean);

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                Log.e("umenguuu", "helper dealWithCustomAction extra:" + msg.extra.toString());

            }
        };
        //使用自定义的NotificationHandler
        PushAgent.getInstance(context).setNotificationClickHandler(notificationClickHandler);

    }

}
