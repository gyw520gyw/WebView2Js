package com.gyw.webview2js;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.webview)
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
    }

    @SuppressLint("JavascriptInterface")
    private void initData() {

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultFontSize(16);

        //此为原生调用js的关键
        mWebView.setWebChromeClient(new MyWebChromeClient());

        //此为的js调用的原生的关键
        mWebView.addJavascriptInterface(this, "demo");


        mWebView.loadUrl("file:///android_asset/index.html");

    }


    @JavascriptInterface
    public void js2android() {
        Toast.makeText(this, "js 调用android方法", Toast.LENGTH_SHORT).show();
    }


    @OnClick(R.id.textview)
    public void click() {
        mWebView.loadUrl("javascript:android2js()");
    }




    class MyWebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            //这里是js中alert中的信息
            Log.d("GYW", message);
            return super.onJsAlert(view, url, message, result);
        }
    }
}
