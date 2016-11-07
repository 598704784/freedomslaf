package services;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

public class MyService extends Service {
    SharedPreferences spf;
    private LocationManager lm;
    private MyLocationlisten ml;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        spf = getSharedPreferences("config", MODE_PRIVATE);
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setCostAllowed(true);//是否允许使用付费功能，上网等
        String bast = lm.getBestProvider(criteria, true);//获取最佳的位置提供者
        ml = new MyLocationlisten();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(bast, 0, 0, ml);// 参1表示位置提供者,参2表示最短更新时间,参3表示最短更新距离
    }

    class MyLocationlisten implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {//位置发生变化是调用的方法
            spf.edit().putString("location", "j" + location.getLongitude() + ";w" + location.getLatitude()).commit();
            Log.d("jw", "j" + location.getLongitude() + ";w" + location.getLatitude());
            stopSelf();//停止服务，不然会一直获取地址

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {//// 位置提供者状态发生变化

        }

        @Override
        public void onProviderEnabled(String provider) {//// 用户打开gps

        }

        @Override
        public void onProviderDisabled(String provider) {//// 用户关闭gps

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.removeUpdates(ml);;// 当activity销毁时,停止更新位置, 节省电量
    }
}
