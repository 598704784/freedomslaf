package com.example.everyday.fragmen.vp_Itmes;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by 欧宇志 on 2016/10/30.
 */
public class smart extends Items_Base {
    public smart(Activity activity) {
        super(activity);
    }

    @Override
    public void initdata() {
        super.initdata();
        tv_title.setText("智慧服务");
        iv_menu.setVisibility(View.INVISIBLE);
        TextView tv=new TextView(activity);
        tv.setText("智慧服务");
        fl.addView(tv);
    }
}
