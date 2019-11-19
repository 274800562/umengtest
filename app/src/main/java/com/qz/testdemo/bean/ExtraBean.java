package com.qz.testdemo.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @ CopyRight (C), 2015-2019, 信息技术有限公司
 * Created by wychang on 2019-11-12.
 * @Description:
 */
@Entity
public class ExtraBean implements Serializable {
    public static final long serialVersionUID=536871008;
    @Id
    private Long id;
    private String title;
    private int type;

    public ExtraBean() {
    }

    @Generated(hash = 203183440)
    public ExtraBean(Long id, String title, int type) {
        this.id = id;
        this.title = title;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
