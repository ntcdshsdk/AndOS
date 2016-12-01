package com.osshare.andos.view.web;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by apple on 16/9/23.
 */
public class BaseWebView extends WebView {
    public BaseWebView(Context context) {
        super(context);
    }

    public BaseWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
