package com.example.freedomsalt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class sept3 extends Activity {

    private View v;
    EditText et_phone;
    SharedPreferences spf;

    public  sept3(final Context context) {
        v = View.inflate(context, R.layout.activity_sept3,null);
        spf=context.getSharedPreferences("config",MODE_PRIVATE);
        et_phone= (EditText) v.findViewById(R.id.ed_phone);
        String phone=spf.getString("phone","");
        et_phone.setText(phone);

    }




    public View getView(){
        return v;
    }
}
