package com.example.everyday;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.example.everyday.R;
import com.example.everyday.fragmen.ContenFragment;
import com.example.everyday.fragmen.MenuFragmen;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.lidroid.xutils.HttpUtils;

public class MainActivity extends SlidingFragmentActivity {

    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = View.inflate(getBaseContext(), R.layout.left_menu, null);
        setBehindContentView(view);
        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        slidingMenu.setBehindOffset(300);
        initFragment();
    }

    private void initFragment() {
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        fragmentTransaction.replace(R.id.ff_content, new ContenFragment(),"content_Fragment");
        fragmentTransaction.replace(R.id.ff_left, new MenuFragmen(),"left_menu");
        fragmentTransaction.commit();

    }
    public MenuFragmen getLeftMenu(){
        FragmentManager fm=getSupportFragmentManager();
        MenuFragmen left_menu = (MenuFragmen) fm.findFragmentByTag("left_menu");
        return left_menu;
    }
    public ContenFragment getContent() {
        FragmentManager fm = getSupportFragmentManager();
        ContenFragment content_fragment = (ContenFragment) fm.findFragmentByTag("content_Fragment");
        return content_fragment;
    }

}
