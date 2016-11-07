package services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freedomsalt.R;

public class MyService2 extends Service {

    private TelephonyManager tm;
    private WindowManager mWM;
    private View view;
    private MyListen ml;
    private int startY;
    private int startX;
    private int endY;
    private int endX;
    private SharedPreferences spf;
    private WindowManager.LayoutParams params;

    public MyService2() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        ml = new MyListen();
        tm.listen(ml,PhoneStateListener.LISTEN_CALL_STATE);
        spf=getSharedPreferences("config",MODE_PRIVATE);
    }
    class MyListen extends PhoneStateListener{
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state){
                case TelephonyManager.CALL_STATE_RINGING:
                    showWindow();
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    if (mWM!=null&&view!=null){
                        mWM.removeView(view);
                        view=null;
                    }
            }
            super.onCallStateChanged(state, incomingNumber);
        }
    }
    public void showWindow(){
        mWM = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        params = new WindowManager.LayoutParams();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        params.format = PixelFormat.TRANSLUCENT;
        params.type = WindowManager.LayoutParams.TYPE_TOAST;
        params.setTitle("Toast");
       // int lastx=spf.getInt("lastX",0);
       // int lasty=spf.getInt("lastY",0);
        view = View.inflate(this, R.layout.guishu,null);
        ImageView iv= (ImageView) view.findViewById(R.id.iv2);
//        LinearLayout.LayoutParams llp= (LinearLayout.LayoutParams) view.getLayoutParams();
//        llp.leftMargin=lastx;
//        llp.topMargin=lasty;
//        view.setLayoutParams(llp);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyService2.this, "2222222222", Toast.LENGTH_SHORT).show();
            }
        });
        iv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Log.d("das","ds1111111111111111d");
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.d("das","ds6666666666666666666666ad");
                        endX = (int) event.getRawX();
                        endY = (int) event.getRawY();
                        int dx=endX-startX;
                        int dy=endY-startY;

                        params.x=params.x+dx;
                        params.y=params.y+dy;
                        mWM.updateViewLayout(view,params);
                        startX= (int) event.getRawX();
                        startY= (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d("das","dsa888888888888888888888888888888d");
                        int lastX=view.getLeft();
                        int lastY=view.getTop();
                        spf.edit().putInt("lastX",lastX);
                        spf.edit().putInt("lastY",lastY).commit();
                        break;
                }
                return false;
            }
        });
        mWM.addView(view, params);


    }

    @Override
    public void onDestroy() {
        tm.listen(ml,PhoneStateListener.LISTEN_NONE);
        super.onDestroy();
    }
}
