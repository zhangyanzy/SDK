package com.tarenwang.trwgamesdk.account.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tarenwang.trwgamesdk.utils.ResourceUtils;
import com.tarenwang.trwgamesdk.utils.SDKHelper;

public class WebForgetPassword extends Activity {

    private WebView mWv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(ResourceUtils.getLayoutId(SDKHelper.getmInstance().getContext(), "trw_activity_web_forget_password"));
        mWv = (WebView) findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "webView"));
        WebSettings webSettings = mWv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mWv.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        mWv.loadUrl("http://my.zhaouc.com/index.php?g=user&m=login&a=forgot_password");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
