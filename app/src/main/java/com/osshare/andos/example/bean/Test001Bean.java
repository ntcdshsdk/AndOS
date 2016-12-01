package com.osshare.andos.example.bean;

import java.io.Serializable;

/**
 * Created by apple on 16/9/13.
 */
public class Test001Bean implements Serializable {
    private String bodyText;
    private String buttonText;

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }
}
