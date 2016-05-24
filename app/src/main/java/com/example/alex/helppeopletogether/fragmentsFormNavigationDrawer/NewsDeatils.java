package com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer;

import android.support.v7.app.AppCompatActivity;



        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.webkit.WebView;

        import com.example.alex.helppeopletogether.R;

public class NewsDeatils extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_deatils);
        webView = (WebView) findViewById(R.id.webview);
        Bundle bundle = getIntent().getExtras();
        webView.loadUrl(bundle.getString("Link"));
    }
}