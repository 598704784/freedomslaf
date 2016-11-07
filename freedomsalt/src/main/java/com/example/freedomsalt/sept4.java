package com.example.freedomsalt;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class sept4  {
    SharedPreferences spf;
    private CheckBox cb;
    private final View v;

    public  sept4( Context context) {
        v = View.inflate(context, R.layout.activity_sept4,null);
        spf=context.getSharedPreferences("config",Context.MODE_PRIVATE);
        cb = (CheckBox) v.findViewById(R.id.cb1);
        cb.setChecked(spf.getBoolean("fd",false));
        if (cb.isChecked()){
            cb.setText("你已经开启了手机防盗");

        }else {
            cb.setText("你没有开启手机防盗");

        }
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    spf.edit().putBoolean("fd",true).commit();
                    cb.setText("你已经开启了手机防盗");
                    Log.d("dsad","3123123123");

                }else {
                    spf.edit().putBoolean("fd",false).commit();
                    cb.setText("你没有开启手机防盗");
                    Log.d("dsad","gdfgdfgdfgd");

                }
            }
        });


    }
    public View getView(){
        return v;
    }
}
