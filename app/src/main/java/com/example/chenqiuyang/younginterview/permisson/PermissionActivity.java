package com.example.chenqiuyang.younginterview.permisson;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.chenqiuyang.younginterview.R;
import com.example.chenqiuyang.younginterview.customView.view.ScaleButton;

/**
 * 2018/11/27
 * from 陈秋阳
 * 功能描述：
 */
public class PermissionActivity extends Activity {
    private Button record;
    private ScaleButton sd;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        record = findViewById(R.id.btn_record);
        sd =  findViewById(R.id.btn_sd);
        initPermission();
        initListener();
    }

    private void initPermission() {
        //同时申请多个权限
//        PermissionManager.getInstance(getApplicationContext()).execute(this, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        //请求单个，显示对话框的方式
        PermissionManager.getInstance(getApplicationContext()).executeDialog(this, Manifest.permission.RECORD_AUDIO,
                PermissionManager.getInstance(getApplicationContext()).new Builder(this)
                        .setMessage("应用需要获取您的录音权限，是否授权？")
                        .setTitle(getString(R.string.app_name))
                        .setIcon(R.mipmap.ic_launcher)
                        .setOk("OK")
                        .setCancel("CANCEL"));
    }

    private void initListener() {
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PermissionManager.getInstance(getApplicationContext()).getGrantedInfo(Manifest.permission.RECORD_AUDIO) ) {
                    Toast.makeText(PermissionActivity.this, "录音权限已经获取", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PermissionActivity.this, "你还没有获取录音权限", Toast.LENGTH_SHORT).show();
                }
            }
        });
        sd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PermissionManager.getInstance(getApplicationContext()).getGrantedInfo(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(PermissionActivity.this, "SD权限已经获取", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PermissionActivity.this, "你还没有获取SD权限", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.getInstance(getApplicationContext()).onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
