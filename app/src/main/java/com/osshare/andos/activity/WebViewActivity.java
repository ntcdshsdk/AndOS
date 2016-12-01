package com.osshare.andos.activity;

import android.os.Bundle;
import android.webkit.WebView;

import com.osshare.andos.R;
import com.osshare.andos.view.web.BaseWebView;
import com.osshare.andos.view.web.BaseWebViewClient;
import com.osshare.framework.base.BaseActivity;
import com.osshare.framework.manager.Constant;

/**
 * Created by apple on 16/11/28.
 */
public class WebViewActivity extends BaseActivity {
    private String url;
    private WebView wvContent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        url = getIntent().getStringExtra(Constant.KEY_URL);

        wvContent = (WebView) findViewById(R.id.wv_content);
        wvContent.setWebViewClient(new BaseWebViewClient());
        wvContent.loadUrl(url);
    }


}
