package services;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {

    private DevicePolicyManager dpm;

    public SmsReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("收到短信");
        Object[] object = (Object[]) intent.getExtras().get("pdus");
        for (Object ob:object) {
            SmsMessage sms = SmsMessage.createFromPdu((byte[]) ob);
            String address = sms.getOriginatingAddress();
            String body=sms.getMessageBody();
            Log.d("sms",address+"::::"+body);
            if (address.equals("5556")){
                if (body.equals("1")){
                    Log.d("music","播放报警音乐");
                }else if (body.equals("location")){
                    context.startService(new Intent(context,MyService.class));
                    SharedPreferences spf=context.getSharedPreferences("config",Context.MODE_PRIVATE);
                   String str= spf.getString("location","");
                    Log.d("loc",str);
                }else if(body.equals("2")){

                    dpm = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
                    ComponentName cn= new ComponentName(context,Receiver2.class);
                    if (dpm.isAdminActive(cn)){
                    dpm.lockNow();
                    }else {
                        Intent intent1=new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                        intent1.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,cn);
                        intent1.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"一定要开启哟");
                        context.startActivity(intent);
                    }
                }else if (body.equals("5")){
                    dpm.wipeData(0);
                }
            }
        }
    }
}
