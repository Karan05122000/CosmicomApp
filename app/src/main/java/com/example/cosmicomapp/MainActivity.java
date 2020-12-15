package com.example.cosmicomapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView mWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebview = (WebView) findViewById(R.id.fullscreen_content);
        WebSettings webSettings = mWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        mWebview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
                findViewById(R.id.splash_image_view).setVisibility(View.GONE);
                findViewById(R.id.splash_text_view).setVisibility(View.GONE);
                findViewById(R.id.fullscreen_content).setVisibility(View.VISIBLE);
            }
        });
        mWebview.loadUrl("https://cosmicom.netlify.app/");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebview.canGoBack()) {
            mWebview.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

//    public void handleNavItemClick(MenuItem item) {
//        if (item.getItemId() == R.id.logout) {
//            SharedPreferences prefs;
//            SharedPreferences.Editor edit;
//            prefs = getSharedPreferences("authorization", Context.MODE_PRIVATE);
//            edit = prefs.edit();
//            edit.remove("token");
//            edit.apply();
//            Navigation.findNavController(this, R.id.my_nav_host_fragment).navigateUp();
//        }
//    }

}