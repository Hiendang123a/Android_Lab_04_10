package com.example.videoshort_firebase;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.videoshort_firebase.databinding.ActivityWebviewBinding;

public class WebviewActivity extends AppCompatActivity {
    private ActivityWebviewBinding binding;

    @SuppressLint({"SetJavaScriptEnabled", "WebViewApiAvailability"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityWebviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getSupportActionBar().hide();
        binding.webview2. getSettings().setLoadWithOverviewMode(true);
        binding.webview2.getSettings().setUseWideViewPort(true);
        binding.webview2.getSettings().setJavaScriptEnabled(true);
        binding.webview2.setWebViewClient(new WebViewClient());
        binding.webview2.getSettings().setBuiltInZoomControls(true);
        binding.webview2. getSettings().setDomStorageEnabled(true);
        binding.webview2.getSettings().setDatabaseEnabled(true);
        binding.webview2.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        binding.webview2. setWebChromeClient(new WebChromeClient());
        binding.webview2.loadUrl("http://iotstar.vn");
    }
}