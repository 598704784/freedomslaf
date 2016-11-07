package com.example.everyday.fragmen;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.everyday.MainActivity;
import com.example.everyday.R;
import com.example.everyday.fragmen.vp_Itmes.Items_Base;
import com.example.everyday.fragmen.vp_Itmes.govaa;
import com.example.everyday.fragmen.vp_Itmes.home;
import com.example.everyday.fragmen.vp_Itmes.newsitems;
import com.example.everyday.fragmen.vp_Itmes.settings;
import com.example.everyday.fragmen.vp_Itmes.smart;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;


public class ContenFragment extends BaseFragment {
    ArrayList<Items_Base> al;
    private ViewPager vp;
    private RadioGroup radioGroup;


    @Override
    public View initView() {
        View view=View.inflate(mActivity, R.layout.base_content,null);
        vp = (ViewPager) view.findViewById(R.id.vp);
        radioGroup = (RadioGroup) view.findViewById(R.id.rg);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb1:
                        vp.setCurrentItem(0,false);
                        break;
                    case R.id.rb2:
                        vp.setCurrentItem(1,false);
                        break;
                    case R.id.rb3:
                        vp.setCurrentItem(2,false);
                        break;
                    case R.id.rb4:
                        vp.setCurrentItem(3,false);
                        break;
                    case R.id.rb5:
                        vp.setCurrentItem(4,false);
                        break;
                }
            }
        });
        return view;
    }

    @Override
    public void initdata() {
        al=new ArrayList<>();
        al.add(new home(mActivity));
        al.add(new newsitems(mActivity));
        al.add(new smart(mActivity));
        al.add(new govaa(mActivity));
        al.add(new settings(mActivity));
        vp.setAdapter(new MyAdapter());
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               al.get(position).initdata();
                boolean sl=false;
                if (position==1){
                    sl=true;
                }
                Enable(sl);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        al.get(0).initdata();

    }
    class MyAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return al.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Items_Base items_base = al.get(position);
                View view=items_base.rootView;
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
    public void Enable(boolean enable){
        MainActivity mainUi= (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUi.getSlidingMenu();
        if (enable){
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        }else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);

        }
    }
}
