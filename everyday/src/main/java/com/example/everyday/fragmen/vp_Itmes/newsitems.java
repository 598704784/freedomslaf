package com.example.everyday.fragmen.vp_Itmes;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import android.widget.TextView;

import com.example.everyday.MainActivity;
import com.example.everyday.fragmen.MenuFragmen;
import com.example.everyday.fragmen.vp_Itmes.newslitems.Base;
import com.example.everyday.fragmen.vp_Itmes.newslitems.hudong;
import com.example.everyday.fragmen.vp_Itmes.newslitems.menu_news;
import com.example.everyday.fragmen.vp_Itmes.newslitems.zhuanti;
import com.example.everyday.fragmen.vp_Itmes.newslitems.zutu;
import com.example.everyday.js.newsjs;
import com.example.everyday.js.staticnum;
import com.example.everyday.util.spfUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.File;
import java.util.ArrayList;


/**
 * Created by 欧宇志 on 2016/10/30.
 */
public class newsitems extends Items_Base {

    private com.example.everyday.js.newsjs newsjs;
    ArrayList<Base> alb=new ArrayList<>();
    public newsitems(Activity activity) {
        super(activity);
    }

    @Override
    public void initdata() {
        super.initdata();
        tv_title.setText("新闻");
        iv_menu.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {

        initUrl(staticnum.URL);
            }
        }).start();


    }

    private void initUrl(String url) {
        HttpUtils hu = new HttpUtils();
        BitmapUtils bm=new BitmapUtils(activity);

        hu.send(HttpRequest.HttpMethod.GET, url,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String json=responseInfo.result;
                       propessData(json);
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {

                    }
                });
    }
    public void propessData(String json){
        Gson gs=new Gson();
        newsjs = gs.fromJson(json, newsjs.class);
        setLeftMenu();
    }
    public void setLeftMenu(){
        MainActivity mainUi= (MainActivity) activity;
        MenuFragmen leftMenu = mainUi.getLeftMenu();
        leftMenu.getData(newsjs.data);
        initcontent();

    }

    private void initcontent() {
        alb.add(new menu_news(activity,newsjs.data));
        alb.add(new zhuanti(activity));
        alb.add(new zutu(activity,ib));
        alb.add(new hudong(activity));
        setcurrentView(0);
    }
    public void setcurrentView(int postion){
        fl.removeAllViews();
        Base base = alb.get(postion);
        fl.addView(alb.get(postion).rootView);
        tv_title.setText(newsjs.data.get(postion).title);
        if (base instanceof zutu){
            ib.setVisibility(View.VISIBLE);
        }else {
            ib.setVisibility(View.GONE);
        }
    }
}


