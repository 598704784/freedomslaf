package com.example.freedomsalt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import services.MyService;
import services.MyService2;

public class SettingsActivity extends AppCompatActivity {
    SharedPreferences spf;
    private SettingItems stt;
    private SettingItems st2;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().hide();
        spf=getSharedPreferences("config", Context.MODE_PRIVATE);
        initView();
        intent=new Intent(this, MyService2.class);
    }

    private void initView() {
        stt = (SettingItems) findViewById(R.id.it1);
        boolean check=spf.getBoolean("update",false);
        stt.setCheck(check);
        stt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isCheck=stt.isCheck();
                if (isCheck){
                    stt.setCheck(false);
                    spf.edit().putBoolean("update",false).commit();
                }else {
                    stt.setCheck(true);
                    spf.edit().putBoolean("update",true).commit();
                }
            }
        });
        st2 = (SettingItems) findViewById(R.id.it2);
        boolean ce=spf.getBoolean("guishu",false);
        st2.setCheck(ce);
        st2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isc=st2.isCheck();
                if (isc){
                    st2.setCheck(false);
                    spf.edit().putBoolean("guishu",false).commit();
                    stopService(intent);
                }else {
                    st2.setCheck(true);
                    spf.edit().putBoolean("guishu",true).commit();
                    startService(intent);

                }
            }
        });
    }
}
