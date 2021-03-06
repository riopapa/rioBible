package com.urrecliner.andriod.riobible;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    long backKeyPressedTime;
    private final static int MY_PERMISSIONS_WRITE_FILE = 101;
    private final static int MY_PERMISSIONS_INTERNET = 102;
    public boolean Permission_Write = false;
    public boolean Permission_Internet = false;

    WebView webView;
    String bibleFolder = getPublicAlbumStorageDir("rioHolyBible");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        backKeyPressedTime = System.currentTimeMillis();

        getExternalWritePermission();
        getInternetPermission();
        if ( Permission_Write == true &&  Permission_Internet == true) {
            webView.loadUrl("file:///" + bibleFolder + "index.html");
        }
        else {
            Toast.makeText(getApplicationContext(),"안드로이드 허가 관계를 확인해 주세요",
                    Toast.LENGTH_LONG).show();

        }
    }

    public String getPublicAlbumStorageDir(String bibleFolder) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStorageDirectory().getPath());

        return file.toString() + "/" + bibleFolder + "/";
    }

    public void getInternetPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.INTERNET)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET},
                        MY_PERMISSIONS_INTERNET);
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.INTERNET)
                    == PackageManager.PERMISSION_GRANTED) {
                Permission_Internet = true;
            } else {
                Toast.makeText(getApplicationContext(),
                    "인터넷에 접근할 수 있도록 허락하고 다시 실행해 주세요.", Toast.LENGTH_LONG).show();
            }
        }
        else Permission_Internet = true;
    }
    public void getExternalWritePermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_WRITE_FILE);
                if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                    Permission_Write = true;
                } else {
                    Toast.makeText(getApplicationContext(),
                        "파일을 읽고 쓸 수 있도록 허락되어야 사용할 수 있습니다.", Toast.LENGTH_LONG).show();
                    finish();
                    System.exit(0);
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
        }
        else Permission_Write = true;
    }

    @Override
    public void onBackPressed() {
        //1번째 백버튼 클릭
        if(System.currentTimeMillis()>backKeyPressedTime+2000){ // 2초 내에 back click ?
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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_WRITE_FILE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),
                            "파일 읽고 쓰기가 허락되었습니다.\n다시 시작해 주세요", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "파일 읽고 쓸 수가 없군요.", Toast.LENGTH_LONG).show();
                    finish();
                    System.exit(0);
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
                return;
            }
            case MY_PERMISSIONS_INTERNET: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),
                            "인터넷 접근이 허락되었습니다.\n다시 시작해 주세요", Toast.LENGTH_LONG).show();
                } else {

                    Toast.makeText(getApplicationContext(),
                            "인터넷에 접근할 수가 없군요.", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}

