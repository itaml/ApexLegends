package com.example.kotlinhandbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class ViewSection extends AppCompatActivity {

    WebView webView;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_section);

        Intent intent = this.getIntent();
        this.fileName = intent.getStringExtra("fileName");

        webView = findViewById(R.id.webView);
        // устанавливаем Zoom control
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/"+fileName);
    }
}