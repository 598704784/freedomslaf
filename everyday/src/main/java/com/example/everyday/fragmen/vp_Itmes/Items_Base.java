package com.example.everyday.fragmen.vp_Itmes;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.everyday.MainActivity;
import com.example.everyday.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * Created by 欧宇志 on 2016/10/30.
 */
public class Items_Base {
    Activity activity;
    public View rootView;
    ImageView iv_menu;
    TextView tv_title;
    FrameLayout fl;
    public ImageButton ib;

    public Items_Base(Activity activity) {
        this.activity = activity;
        rootView = initView();
    }

    public View initView() {
        View v = View.inflate(activity, R.layout.content, null);
        iv_menu = (ImageView) v.findViewById(R.id.iv_title);
        tv_title = (TextView) v.findViewById(R.id.tv_title);
        ib = (ImageButton) v.findViewById(R.id.lv_gv);
        fl = (FrameLayout) v.findViewById(R.id.fl_content);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totgl();
            }
        });
        return v;

    }

    private void totgl() {
        MainActivity mainUi= (MainActivity) activity;
        SlidingMenu slidingMenu=mainUi.getSlidingMenu();
        slidingMenu.toggle();

    }

    public void initdata() {

    }
}
