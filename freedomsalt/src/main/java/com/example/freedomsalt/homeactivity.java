package com.example.freedomsalt;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import utils.Read;

public class homeactivity extends AppCompatActivity {
    private static final int UPDATE =0x13 ;
    private static final int HTTP_ERROR =0x14 ;
    private static final int IO_ERROR = 0x15;
    private static final int HOME =0x12 ;
    private static final int JSON_ERROR =0x16;
    SharedPreferences spf;
    Handler hd=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case UPDATE:
                    showHome();
                    showDl();
                    break;
                case HOME:
                    showHome();
                    break;
                case HTTP_ERROR:
                    showHome();
                    Toast.makeText(homeactivity.this, "Url错误", Toast.LENGTH_SHORT).show();
                    break;
                case IO_ERROR:
                    showHome();
                    Toast.makeText(homeactivity.this, "读取错误", Toast.LENGTH_SHORT).show();
                    break;
                case JSON_ERROR:
                    showHome();
                    Toast.makeText(homeactivity.this, "JSON解析错误", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private NotificationManager nm;
    Notification.Builder  nb;
    private String updateContent;
    private TextView tv;
    private RelativeLayout rl;
    private String downLoadUrl;
    private GridView gv;
    private String[] name=new String[]{"手机防盗","通讯卫士","软件管理","进程管理","流量统计",
            "手机杀毒","缓存清理","高级工具","设置中心",};
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeactivity);
        getSupportActionBar().hide();
        spf=getSharedPreferences("config", Context.MODE_PRIVATE);
       iniView();
    }

    private void iniView() {
        tv = (TextView) findViewById(R.id.tv_home);
        tv.setText("当前版本是:"+getVersionname());
        rl = (RelativeLayout) findViewById(R.id.rl_sp);
        gv= (GridView) findViewById(R.id.gv);
        gv.setAdapter(new MyAdapater());

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 8:
                        Intent it=new Intent(getBaseContext(),SettingsActivity.class);
                        startActivity(it);
                        break;
                    case 0:String pwd=spf.getString("pass","");
                        if(!TextUtils.isEmpty(pwd)){
                        showpwdsec();
                        }else {
                        showpwdfirst();

                        }
                        break;
                    case 1:startActivity(new Intent(getBaseContext(),txws.class));
                }
            }
        });
        if(spf.getBoolean("update",false)){
        innHttp("http://10.0.2.2:8080/freedom.json");
        }else {
            hd.sendEmptyMessageDelayed(HOME,4000);
        }
    }

    private void showHome() {

        rl.setVisibility(View.GONE);
     //   tv.setVisibility(View.VISIBLE);
        gv.setVisibility(View.VISIBLE);
        getSupportActionBar().show();
    }

    private void innHttp(final String str) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long startTime=System.currentTimeMillis();
                Message me=Message.obtain();
                try {
                    URL url =new URL(str);
                    HttpURLConnection conn= (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(3000);
                    conn.setReadTimeout(3000);
                    conn.setRequestMethod("GET");
                    int coad=conn.getResponseCode();
                    if(coad==200){
                        InputStream is=conn.getInputStream();
                        String strjs=Read.getStr(is);
                        JSONObject jsob = new JSONObject(strjs);
                        String versionNeme=jsob.getString("versionName");
                        String versionCoad=jsob.getString("versionCoad");
                        updateContent=  jsob.getString("updateContent");
                        downLoadUrl = jsob.getString("downLoadUrl");
                        if (getVersionCoad()<Integer.parseInt(versionCoad)){
                            me.what= UPDATE;
                        }else {
                            me.what=HOME;
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    me.what=HTTP_ERROR;
                } catch (IOException e) {
                    e.printStackTrace();
                    me.what=IO_ERROR;
                } catch (JSONException e) {
                    e.printStackTrace();
                    me.what=JSON_ERROR;
                }finally {
                    long endTime=System.currentTimeMillis();
                    if(endTime-startTime<4000){
                        try {
                            Thread.sleep(4000-(endTime-startTime));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    hd.sendMessage(me);
                }
            }
        }).start();

    }
    private String getVersionname(){
        PackageManager pm=getPackageManager();
        PackageInfo pi= null;
        try {
            pi = pm.getPackageInfo(getPackageName(),0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    private int getVersionCoad(){
        PackageManager pm=getPackageManager();
        try {
            PackageInfo pi=pm.getPackageInfo(getPackageName(),0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
    private void showDl() {
        AlertDialog.Builder ab=new AlertDialog.Builder(this);
        ab.setIcon(R.mipmap.ic_launcher);
        ab.setTitle("有新版本");
        ab.setMessage(updateContent);
        ab.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                    nm= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    nb=new Notification.Builder(homeactivity.this);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String path=Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"freedom.apk";
                            HttpUtils hu=new HttpUtils();
                            hu.download(downLoadUrl, path, new RequestCallBack<File>() {
                                @Override
                                public void onStart() {
                                    super.onStart();

                                    nb.setContentTitle("开始下载新版本");
                                    nb.setSmallIcon(R.mipmap.ic_launcher);
                                    nb.setProgress(100,0,true);
                                    Notification nbb = nb.build();
                                    nm.notify(16,nbb);
                                }

                                @Override
                                public void onLoading(long total, long current, boolean isUploading) {
                                    Log.d("dsad","~~~~~~~~~~~~~~~~3463463463`````");
                                    super.onLoading(total, current, isUploading);
                                    nb.setContentTitle("正在下载");
                                    nb.setProgress((int) total, (int) current,false);
                                    int va= (int) (current*100/total);
                                    nb.setContentText("当前进度是"+va+"%");
                                    Notification nbb = nb.build();
                                    nbb.flags=Notification.FLAG_NO_CLEAR;//不让用户删除通知信息
                                    nm.notify(16,nbb);

                                }

                                @Override
                                public void onSuccess(ResponseInfo<File> responseInfo) {
                                    nb.setContentTitle("下载完成");
                                    nb.setProgress(100,100,false);
                                    Notification nbb = nb.build();
                                    nm.notify(16,nbb);
                                    Toast.makeText(homeactivity.this, "下载完成", Toast.LENGTH_SHORT).show();
                                    File file=responseInfo.result;
                                    insertApk(file);

                                }

                                @Override
                                public void onFailure(HttpException e, String s) {
                                    Log.d("3453",e+"");
                                    Log.d("3453",s);
                                    nm.cancel(16);
                                    Toast.makeText(homeactivity.this, "下载失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).start();


            }
        });
        ab.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        ab.show();
    }

    private void insertApk(File file) {
        Intent it=new Intent("android.intent.action.VIEW");
        it.addCategory("android.intent.category.DEFAULT");
        it.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
        startActivityForResult(it,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        showHome();
    }
    class MyAdapater extends BaseAdapter{

        @Override
        public int getCount() {
            return name.length;
        }

        @Override
        public Object getItem(int i) {
            return name[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view=View.inflate(homeactivity.this,R.layout.grifview,null);
            ImageView im= (ImageView) view.findViewById(R.id.iv_gr);
            TextView tv= (TextView) view.findViewById(R.id.tv_gr);
            im.setImageResource(R.mipmap.ic_launcher);
            tv.setText(name[i]);
            return view;
        }
    }
    private void showpwdfirst(){
        Log.d("331","~~~~~~~~~~~adsdasdasd");
        AlertDialog.Builder ab=new AlertDialog.Builder(this);
        dialog = ab.create();
        View v= View.inflate(this,R.layout.pwd,null);
        dialog.setView(v,0,0,0,0);
        final EditText et_sr = (EditText) v.findViewById(R.id.ed_sr);
        final EditText et_zc = (EditText) v.findViewById(R.id.ed_zc);
        Button bt_qx= (Button) v.findViewById(R.id.bt_qx);
        Button bt_qd= (Button) v.findViewById(R.id.bt_qd);
        bt_qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        bt_qd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fir=et_sr.getText().toString();
                String sec=et_zc.getText().toString();
                if (!TextUtils.isEmpty(fir)){
                if (fir.equals(sec)){
                    spf.edit().putString("pass",fir).commit();
                    startActivity(new Intent(getBaseContext(),phone_takeActivity.class));
                    dialog.dismiss();
                }else {
                    Toast.makeText(homeactivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                }
                }else {
                    Toast.makeText(homeactivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }

            }
        });
        dialog.show();
    }
    private void showpwdsec(){
        Log.d("1","2222222222222222222");
        AlertDialog.Builder ab=new AlertDialog.Builder(this);
        dialog = ab.create();
        View v= View.inflate(this,R.layout.pwd,null);
        dialog.setView(v,0,0,0,0);
         EditText et_sr = (EditText) v.findViewById(R.id.ed_sr);
        et_sr.setVisibility(View.GONE);
         final EditText et_zc = (EditText) v.findViewById(R.id.ed_zc);
        Button bt_qx= (Button) v.findViewById(R.id.bt_qx);
        Button bt_qd= (Button) v.findViewById(R.id.bt_qd);
        bt_qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        bt_qd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass=spf.getString("pass","");
                String zc=et_zc.getText().toString();
                if (!TextUtils.isEmpty(zc)){
                    if(zc.equals(pass)){
                        startActivity(new Intent(getBaseContext(),finshTake.class));
                        dialog.dismiss();
                    }
                }else {
                    Toast.makeText(homeactivity.this, "你输入的密码为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();

    }

}
