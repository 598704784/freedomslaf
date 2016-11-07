package com.example.network;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.network.Utils.HttpUtils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class logInMain2 extends AppCompatActivity {

    private EditText user;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_main);
        initView();
    }

    private void initView() {
        user = (EditText) findViewById(R.id.et_user);
        password = (EditText) findViewById(R.id.et_pass);
        Button login= (Button) findViewById(R.id.bt_login);
        login.setOnClickListener(new View.OnClickListener() {

            private List<NameValuePair> pairs;

            @Override
            public void onClick(View v) {
                String name=user.getText().toString();
                String pasd=password.getText().toString();
                pairs = new ArrayList<>();
                pairs.add(new BasicNameValuePair("name",name));
                pairs.add(new BasicNameValuePair("password",pasd));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HttpUtils.requesPost("http://10.0.2.2:8080/weblogin/sevlet", pairs, new HttpUtils.callbact() {
                            @Override
                            public void success(BufferedReader br, int statusCode) {
                                String str="";
                                final ArrayList<String> al=new ArrayList<String>();

                                try {
                                    while ((str=br.readLine())!=null) {

                                        al.add(str);
                                        System.out.println(al);
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                int i=getVersionCode();
                                int j=Integer.parseInt(al.get(0));
                                if (i<j){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            showdialog(al.get(2),al.get(3));
                                        }
                                    });
                                }
                                System.out.println(statusCode);
                            }

                            @Override
                            public void error(int statusCode) {
                                System.out.println("错误:"+statusCode);

                            }
                        });
                    }
                }).start();

            }
        });
    }
    public int getVersionCode(){
        int versionCode=999;
        PackageManager pm=getPackageManager();
        try {
            PackageInfo pi=pm.getPackageInfo(getPackageName(),0);
            versionCode=pi.versionCode;
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }
    public void showdialog(String msg, final String url){
        AlertDialog.Builder ab=new AlertDialog.Builder(this);
        ab.setTitle("有新版本!!!");
        ab.setMessage(msg);
        ab.setPositiveButton("马上更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new updataDownLoad().execute(url);
            }
        });
        ab.setNegativeButton("下次再说",null);
        ab.show();
    }
    class updataDownLoad extends AsyncTask<Object,String,Void>{

        private final File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/aa.apk");


        @Override
        protected Void doInBackground(Object... params) {

            String  path= (String) params[0];
            try {
                URL rl=new URL(path);
                HttpURLConnection conn= (HttpURLConnection) rl.openConnection();
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                System.out.println(conn.getResponseCode());
                if (conn.getResponseCode()==200){
                    InputStream is = conn.getInputStream();
                    OutputStream os=new FileOutputStream(file);
                    byte bt[]=new byte[1024];
                    int len=0;
                    while ((len=is.read(bt))!=-1){
                        os.write(bt);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            String i=values[0];
            System.out.println(i);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(logInMain2.this, "下载完成", Toast.LENGTH_SHORT).show();
            Intent it=new Intent("android.intent.action.VIEW");
            it.addCategory("android.intent.category.DEFAULT");
            it.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
            startActivityForResult(it,0);
        }
    }


}
