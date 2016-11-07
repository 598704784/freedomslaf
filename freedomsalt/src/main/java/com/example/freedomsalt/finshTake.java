package com.example.freedomsalt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class finshTake extends AppCompatActivity {
    TextView tv_phone,tv_off;
    ImageView iv;
    SharedPreferences spf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finsh_take);
        getSupportActionBar().hide();
        spf=getSharedPreferences("config",MODE_PRIVATE);
        initView();

    }

    private void initView() {
        tv_phone= (TextView) findViewById(R.id.tv_phone);
        iv= (ImageView) findViewById(R.id.iv_lock);
        tv_off= (TextView) findViewById(R.id.tv_off);
        String phone=spf.getString("phone","");
        tv_phone.setText(phone);
        boolean lock=spf.getBoolean("fd",false);
        if (lock){
            iv.setImageResource(R.drawable.lock);
            tv_off.setText("手机防盗已开启");
        }else {
            iv.setImageResource(R.drawable.unlock);
            tv_off.setText("手机防盗未开启");
        }
    }
    public void click(View view){
        startActivity(new Intent(this,phone_takeActivity.class));
        finish();
    }
}
