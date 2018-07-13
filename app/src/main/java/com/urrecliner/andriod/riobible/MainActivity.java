package com.urrecliner.andriod.riobible;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    long backKeyPressedTime;

    WebView webView;
    String bibleFolder = getPublicAlbumStorageDir("rioHolyBible");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.webView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        backKeyPressedTime = System.currentTimeMillis();

//        Toast.makeText(getApplicationContext(),"[" + bibleFolder + "]", Toast.LENGTH_LONG).show();
        webView.loadUrl("file:///" + bibleFolder + "index.html");
    }
    public String getPublicAlbumStorageDir(String bibleFolder) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStorageDirectory().getPath());

        return file.toString() + "/" + bibleFolder + "/";
    }

    @Override
    public void onBackPressed() {
        //1번째 백버튼 클릭
        if(System.currentTimeMillis()>backKeyPressedTime+2000){
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "뒤로버튼을 한번 더 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
        //2번째 백버튼 클릭 (종료)
        else{
            finish();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

}

