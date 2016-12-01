package com.osshare.andos.view.web;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by apple on 16/9/23.
 */
public class BaseWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}
