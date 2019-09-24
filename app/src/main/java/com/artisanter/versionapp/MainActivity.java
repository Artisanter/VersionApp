package com.artisanter.versionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String version;
        try{
            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch(PackageManager.NameNotFoundException e){
            version = "unknown";
        }
        TextView tv = findViewById(R.id.version_tv);
        tv.setText("Version: " + version);
    }
}
