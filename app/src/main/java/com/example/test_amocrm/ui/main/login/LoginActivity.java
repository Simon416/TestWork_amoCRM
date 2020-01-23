package com.example.test_amocrm.ui.main.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.test_amocrm.R;
import com.example.test_amocrm.ui.main.main.MainActivity;

public class LoginActivity extends AppCompatActivity {

    private static final String REDIRECT_URL = "redirectUrl";
    private static final String REDIRECT_HOST = "takeumbrella.ru";

    public static Intent getStartIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        startPageAmoCRM();
    }

    public static String getRedirectUrl() {
        return REDIRECT_URL;
    }


    private String getUrlAmoCRM() {
        Bundle bundle = getIntent().getExtras();
        return bundle.getString(MainActivity.getUrlAmoCrm());
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void startPageAmoCRM() {
        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                Uri data = Uri.parse(url);
                if (data.getHost().equals(REDIRECT_HOST)) {
                    setResult(RESULT_OK, new Intent().putExtra(REDIRECT_URL, url));
                    finish();
                }
                return false;
            }
        });
        webView.loadUrl(getUrlAmoCRM());
    }
}

