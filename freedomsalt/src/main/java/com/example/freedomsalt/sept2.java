package com.example.freedomsalt;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;

public class sept2  {

    private SettingItems si;
    SharedPreferences spf;
    Context context;
    private final View v;
    private TelephonyManager tm;

    public  sept2(final Context context) {
        this.context=context;
        v = View.inflate(context, R.layout.activity_sept2,null);
        spf=context.getSharedPreferences("config",Context.MODE_PRIVATE) ;
        si = (SettingItems) v.findViewById(R.id.sim);
       String sim= spf.getString("sim","");
        if (!TextUtils.isEmpty(sim)){
            si.setCheck(true);
        }
        si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean bl=si.isCheck();
                if (bl){
                    si.setCheck(false);
                   spf.edit().remove("sim").commit();
                }else {
                    si.setCheck(true);
                    tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    String sim= tm.getSimSerialNumber();
                    spf.edit().putString("sim",sim).commit();
               }
            }
        });

    }
    public View getView(){
        return v;
    }
}
