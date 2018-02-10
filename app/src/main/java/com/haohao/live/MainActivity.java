package com.haohao.live;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final int PM_REQUEST_CODE = 0;
    public static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS};
    private EditText mEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = (EditText) findViewById(R.id.et_rtmp_uri);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermissions();
        }


    }

    public void onClick(View view){
        String rtmp_uri = mEditText.getText().toString().trim();
        SPUtils.getInstance(this).putString(SPUtils.KEY_NGINX_SER_URI, rtmp_uri);
        switch (view.getId()) {
            case R.id.btn_push:
                startActivity(new Intent(this, LivePushActivity.class));
                break;
            case R.id.btn_pull:
                startActivity(new Intent(this, LivePullActivity.class));
                break;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (PM_REQUEST_CODE != requestCode) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        for (int i = 0; i < grantResults.length; i++){
            int result = grantResults[i];
            if (PackageManager.PERMISSION_GRANTED != result) {
                Log.e(TAG, "failed to request permission : " + permissions[i]);
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPermissions(){
        for (String permission : PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                requestPermission(permission);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestPermission(String permission){
        requestPermissions(new String[]{permission}, PM_REQUEST_CODE);
    }
}
