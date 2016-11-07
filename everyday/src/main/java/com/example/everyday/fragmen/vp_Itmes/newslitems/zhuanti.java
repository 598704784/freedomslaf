package com.example.everyday.fragmen.vp_Itmes.newslitems;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by 欧宇志 on 2016/10/31.
 */
public class zhuanti extends Base {
    public zhuanti(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        TextView tv=new TextView(activity);
        tv.setText("专题");
        tv.setGravity(Gravity.CENTER);
        return tv;
    }
}
