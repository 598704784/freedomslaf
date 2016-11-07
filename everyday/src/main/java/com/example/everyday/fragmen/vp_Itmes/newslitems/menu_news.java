package com.example.everyday.fragmen.vp_Itmes.newslitems;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.everyday.MainActivity;
import com.example.everyday.R;
import com.example.everyday.js.newsjs;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

/**
 * Created by 欧宇志 on 2016/10/31.
 */
public class menu_news extends Base {
    ArrayList<newsjs.childrens> data;
    private ViewPager menu_vp;
    private TabPageIndicator tpi;
    private ImageView iv;

    public menu_news(Activity activity, ArrayList<newsjs.newsdata> newsjs) {
        super(activity);
       data= newsjs.get(0).children;
        initdata();
    }

    @Override
    public View initView() {
        View view=View.inflate(activity, R.layout.menu_news,null);
        menu_vp = (ViewPager) view.findViewById(R.id.menu_vp);
        tpi = (TabPageIndicator) view.findViewById(R.id.vptp);
        iv = (ImageView) view.findViewById(R.id.iv_next);
        return view;
    }

    @Override
    public void initdata() {
        super.initdata();
        menu_vp.setAdapter(new MyAdapter());
        tpi.setViewPager(menu_vp);
        tpi.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==0||position==data.size()-1){
                    postMenu(true);
                }else {
                    postMenu(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int i= menu_vp.getCurrentItem();
                menu_vp.setCurrentItem(i+1);
            }
        });
    }

    class MyAdapter extends PagerAdapter{
        @Override
        public CharSequence getPageTitle(int position) {

                return  data.get(position).title;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            menu_news_items menu_news_items = new menu_news_items(activity,data.get(position).url);
            container.addView(menu_news_items.rootView);
            return menu_news_items.rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
    public void postMenu(boolean flag){
        MainActivity mainUi= (MainActivity) activity;
        SlidingMenu slidingMenu=mainUi.getSlidingMenu();
        if (flag){
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        }else {
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);

        }
    }


}
