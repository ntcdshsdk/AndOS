package com.osshare.andos.module.manager.bean;

import java.io.Serializable;

/**
 * Created by apple on 16/12/11.
 */
public class WealthInvestment implements Serializable {
    private String title;
    private String detail;
    private String img;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
