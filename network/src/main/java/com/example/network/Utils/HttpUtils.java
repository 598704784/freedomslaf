package com.example.network.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by 欧宇志 on 2016/11/6.
 */

public class HttpUtils {


    public static void requsetGet(String url, Map<String, String> mp, callbact cb) {

        if (!url.endsWith("?")){
            url=url+"?";
        }
        StringBuffer sb=new StringBuffer(url);
        Set se=mp.entrySet();
        Iterator i=se.iterator();
        while (i.hasNext()){
            String key;
            String value;
            key=i.next().toString();
            value=mp.get(key);
            sb.append(value+"&");
        }
        System.out.println(sb);
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(sb.toString());
        get.addHeader("Connection", "keep_alive");

        try {
            HttpResponse response = client.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();
            //获取实体数据
            if (statusCode==200) {
                HttpEntity entity = response.getEntity();
                //获取到数据的输入流
                InputStream is = entity.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
//                sb = new StringBuffer();
//                String line = "";
                //  File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/a.txt");
                //  BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
//                while ((line = br.readLine()) != null) {
//                    sb.append("请求码：" + statusCode + "~~~~~~~~" + line + "\n");
//                    //    bw.write(line + "\n");
//                    System.out.println("231");
//                }

                    cb.success(br,statusCode);

                is.close();
            }else {

                    cb.error(statusCode);

            }



        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void requesPost(String url ,List<NameValuePair> pairs,callbact cb){
      //  StringBuffer sb;
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost=new HttpPost(url);
        httpPost.addHeader("Connection", "keep_alive");




        try {
            //name:oyz,password:123456
            //组装post请求的参数
//            List<NameValuePair> pairs=new ArrayList<>();
//            pairs.add(new BasicNameValuePair("name","oyz"));
//            pairs.add(new BasicNameValuePair("password","123456"));
            httpPost.setEntity(new UrlEncodedFormEntity(pairs));

            HttpResponse response = client.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode==200) {

                //获取实体数据
                HttpEntity entity = response.getEntity();
                //获取到数据的输入流
                InputStream is = entity.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
//            sb = new StringBuffer();
//            String line = "";
                //   File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/a.txt");
                //  BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
//            while ((line = br.readLine()) != null) {
//                sb.append("请求码："+statusCode+"~~~~~~~~"+line + "\n");
//                // bw.write(line + "\n");
//                System.out.println("231");

                    cb.success(br,statusCode);

//            }
            }else {

                    cb.error(statusCode);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public interface callbact {
        public void success(BufferedReader br,int statusCode);
        public void error(int statusCode);

    }

}
