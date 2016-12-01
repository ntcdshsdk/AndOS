package com.osshare.framework.base;

import java.io.Serializable;

/**
 * Created by apple on 16/11/1.
 */
public class BaseResData implements Serializable {
    private int code;
    private String msg;
    private String extra;
    private String data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
