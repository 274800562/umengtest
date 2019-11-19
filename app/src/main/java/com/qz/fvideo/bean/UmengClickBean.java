package com.qz.fvideo.bean;



/**
 * @ CopyRight (C), 2015-2019, 信息技术有限公司
 * Created by wychang on 2019-10-22.
 * @Description:
 *
 * 通知携带数据
 *
 * 主要根据payload->extra字段内部的type数值跳转对应界面
 *
 * 当after_open值为：go_activity时，直接跳转（"activity":"com.qz.testdemo.MainActivity"）这个类也是可以的
 * 根据自己需求选择
 *
 *
 *
 * 当集成umeng的离线推送时，应用离线收到通知，点击通知时，会执行
 * mi_activity : com.qz.testdemo.UmengClickActivity 这个类里边的onMessage()方法
 *
 */
public class UmengClickBean {


    /**
     * display_type : notification
     * extra : {"id":"2","type":"2","title":"ListActivity"}
     * msg_id : uu8aqw4157415146683811
     * body : {"after_open":"go_activity","play_lights":"false","ticker":"标题list","play_vibrate":"false","activity":"com.qz.testdemo.MainActivity","text":"内容list","title":"标题list","play_sound":"true"}
     * random_min : 0
     */

    private String display_type;
    private ExtraBean extra;
    private String msg_id;
    private BodyBean body;
    private int random_min;

    public String getDisplay_type() {
        return display_type;
    }

    public void setDisplay_type(String display_type) {
        this.display_type = display_type;
    }

    public ExtraBean getExtra() {
        return extra;
    }

    public void setExtra(ExtraBean extra) {
        this.extra = extra;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public int getRandom_min() {
        return random_min;
    }

    public void setRandom_min(int random_min) {
        this.random_min = random_min;
    }


    public static class BodyBean {
        /**
         * after_open : go_activity
         * play_lights : false
         * ticker : 标题list
         * play_vibrate : false
         * activity : com.qz.fvideo.MainActivity
         * text : 内容list
         * title : 标题list
         * play_sound : true
         */

        private String after_open;
        private String play_lights;
        private String ticker;
        private String play_vibrate;
        private String activity;
        private String text;
        private String title;
        private String play_sound;

        public String getAfter_open() {
            return after_open;
        }

        public void setAfter_open(String after_open) {
            this.after_open = after_open;
        }

        public String getPlay_lights() {
            return play_lights;
        }

        public void setPlay_lights(String play_lights) {
            this.play_lights = play_lights;
        }

        public String getTicker() {
            return ticker;
        }

        public void setTicker(String ticker) {
            this.ticker = ticker;
        }

        public String getPlay_vibrate() {
            return play_vibrate;
        }

        public void setPlay_vibrate(String play_vibrate) {
            this.play_vibrate = play_vibrate;
        }

        public String getActivity() {
            return activity;
        }

        public void setActivity(String activity) {
            this.activity = activity;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPlay_sound() {
            return play_sound;
        }

        public void setPlay_sound(String play_sound) {
            this.play_sound = play_sound;
        }
    }
}
