package com.example.network;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.network.Utils.HttpUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private String Tag = "dasdas";
    private TextView tv;
    private StringBuffer sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv_http);
    }

    public void requsetGet(String url) {
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        get.addHeader("Connection", "keep_alive");

        try {
            HttpResponse response = client.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();
            //获取实体数据
            HttpEntity entity = response.getEntity();
            //获取到数据的输入流
            InputStream is = entity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            sb = new StringBuffer();
            String line = "";
          //  File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/a.txt");
          //  BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            while ((line = br.readLine()) != null) {
                sb.append("请求码："+statusCode+"~~~~~~~~"+line + "\n");
            //    bw.write(line + "\n");
                System.out.println("231");

            }
            is.close();
            Log.d(Tag, String.valueOf(sb));
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv.setText(sb);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void requesPost(String url){
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost=new HttpPost(url);
        httpPost.addHeader("Connection", "keep_alive");
        //name:oyz,password:123456



        try {
            //组装post请求的参数
            List<NameValuePair>  pairs=new ArrayList<>();
            pairs.add(new BasicNameValuePair("name","oyz"));
            pairs.add(new BasicNameValuePair("password","123456"));
            httpPost.setEntity(new UrlEncodedFormEntity(pairs));

            HttpResponse response = client.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            //获取实体数据
            HttpEntity entity = response.getEntity();
            //获取到数据的输入流
            InputStream is = entity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            sb = new StringBuffer();
            String line = "";
         //   File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/a.txt");
          //  BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            while ((line = br.readLine()) != null) {
                sb.append("请求码："+statusCode+"~~~~~~~~"+line + "\n");
               // bw.write(line + "\n");
                System.out.println("231");
            }
            is.close();runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv.setText(sb);
                }
            });
            Log.d(Tag, String.valueOf(sb));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.bt_resGet:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                       // requsetGet("http://www.jd.com");
                        utilsget();
                    }
                }).start();

                break;
            case R.id.bt_resPost:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                      // requesPost("http://www.jd.com")
                        utilspost();
                    }
                }).start();
                break;
        }
    }
    public void utilsget(){
        Map<String ,String> mp=new HashMap<>();
        mp.put("name","oyz");
        HttpUtils.requsetGet("http://www.jd.com",  mp,new HttpUtils.callbact() {
            @Override
            public void success(BufferedReader br,int statusCode) {
                StringBuffer sb=new StringBuffer();
                String str="";
                try {
                  while ((str=br.readLine())!=null){
                      sb.append(str+"\n");
                  }
                    System.out.println("请求码:"+statusCode+sb);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void error(int statusCode) {
                Toast.makeText(MainActivity.this, "请求失败:"+statusCode, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void utilspost(){
        List<NameValuePair> pairs=new ArrayList<>();
            pairs.add(new BasicNameValuePair("name","oyz"));
            pairs.add(new BasicNameValuePair("password","123456"));
      HttpUtils.requesPost("http://www.jd.com", pairs, new HttpUtils.callbact() {
          @Override
          public void success(BufferedReader br, int statusCode) {
              sb = new StringBuffer();
              String line = "";
              //   File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/a.txt");
              //  BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
              try {
                  while ((line = br.readLine()) != null) {
                      sb.append(line + "\n");
                      // bw.write(line + "\n");

                  }
                  System.out.println(sb);
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }

          @Override
          public void error(int statusCode) {
              Toast.makeText(MainActivity.this, "请求失败:"+statusCode, Toast.LENGTH_SHORT).show();
          }
      });
    }
}
