package com.example.everyday.fragmen.vp_Itmes;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by 欧宇志 on 2016/10/30.
 */
public class home extends Items_Base {
    public home(Activity activity) {
        super(activity);
    }

    @Override
    public void initdata() {
        super.initdata();
        tv_title.setText("首页");
        iv_menu.setVisibility(View.INVISIBLE);
        TextView tv=new TextView(activity);
        tv.setText("首页");
        fl.addView(tv);
    }
}
