package com.example.mapboxsdktest6;
//maindevrepo

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

public class MainActivity extends AppCompatActivity {
    //Variables Declared
    private WebView mywebView;
    private static final int REQUEST_CODE = 100;
    public static final String imageURL = "https://th.bing.com/th/id/OIP.4DTFm87FSJeMe_7rydeImAHaE8?rs=1&pid=ImgDetMain";
    String imageName = "demoImage.png";

    //Code
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mywebView = findViewById(R.id.webview);
        mywebView.setWebViewClient(new WebViewClient());
        //mywebView.loadUrl("file:///asset/index.htm");
        WebSettings webSetting = mywebView.getSettings();
        webSetting.setBuiltInZoomControls(true);
        mywebView.setWebViewClient(new WebViewClient());
        mywebView.loadUrl("file:///android_asset/index.html");
        WebSettings webSettings = mywebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // storage runtime permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
            }
        }

        Button btnDownloadImage = findViewById(R.id.btnDownloadImage);
        btnDownloadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadImage(imageURL, imageName);
            }
        });

        //Microsoft App Center
        AppCenter.start(getApplication(), "8a4dd9b3-1eef-4a8b-813c-48b0e21fd80f",
                Analytics.class, Crashes.class);
        Analytics.trackEvent("My custom event");
    }
    //https://sites.google.com/student.musd.org/dmp/home
    public void downloadImage(String url, String outputFileName) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(imageName);
        request.setDescription("Downloading " + imageName);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.allowScanningByMediaScanner();
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, outputFileName);
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

    @Override
    public void onBackPressed() {
        if (mywebView.canGoBack()) {
            mywebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}