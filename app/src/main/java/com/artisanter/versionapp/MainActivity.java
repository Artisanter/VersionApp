package com.artisanter.versionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_CODE = 2;
    private static final String PERMISSION = Manifest.permission.READ_PHONE_STATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showVersion();
        showId();
    }
    private boolean isPermissionGranted(String permission) {
        int permissionCheck = ActivityCompat.checkSelfPermission(this, permission);
        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(String permission, int requestCode) {
        ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showId();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void showDialog(){AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Attention!")
                .setMessage("This application requires permission to display your device ID")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        requestPermission(PERMISSION, PERMISSION_CODE);
                    }
                });
        AlertDialog ad = builder.create();
        ad.show();
    }

    private void showId(){
        if(isPermissionGranted(PERMISSION)) {
            TextView idTV = findViewById(R.id.id_tv);
            TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            String id;
            try{
                id = tm.getDeviceId();
            } catch (SecurityException e){
                id = "unknown";
            }
            idTV.setText("Id: " + id);
        }else{
            showDialog();
        }
    }

    private void showVersion(){
        String version;
        try{
            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch(PackageManager.NameNotFoundException e){
            version = "unknown";
        }
        TextView versionTV = findViewById(R.id.version_tv);
        versionTV.setText("Version: " + version);
    }
}
