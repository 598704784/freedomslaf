package com.example.everyday.fragmen.vp_Itmes.newslitems;

import android.app.Activity;
import android.view.View;

/**
 * Created by 欧宇志 on 2016/10/31.
 */
public abstract class Base {
   public Activity activity;
   public View rootView;

    public Base(Activity activity) {
        this.activity = activity;
        rootView=initView();
    }
    public abstract View initView();
    public  void initdata(){

    }
}
