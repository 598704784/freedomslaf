package services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        SharedPreferences spf = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        String sim = spf.getString("sim", "");
        if (!TextUtils.isEmpty(sim)) {
            Toast.makeText(context, "2222222222222222222222222", Toast.LENGTH_SHORT).show();
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String str = tm.getSimSerialNumber();
            if (sim.equals(str)) {
                Log.d("sim","SIM卡没变");
            }else {
                Log.d("sidasdasdm","SIM卡没dsadasdsadsadsadsadadasdsadas变");
                String phone=spf.getString("phone","");
                SmsManager sm=SmsManager.getDefault();
                sm.sendTextMessage(phone,null,"sim被换了",null,null);
            }
        }
    }
}
