package com.example.everyday.fragmen;

import android.location.LocationManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.everyday.MainActivity;
import com.example.everyday.R;
import com.example.everyday.fragmen.vp_Itmes.newsitems;
import com.example.everyday.js.newsjs;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

/**
 * Created by 欧宇志 on 2016/10/30.
 */
public class MenuFragmen extends BaseFragment {

    private ListView lv;
    ArrayList<newsjs.newsdata> an;
    int currMenu;
    private MyAdapter ma;

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.left_menu, null);
        lv = (ListView) view.findViewById(R.id.lv_menu);
        return view;
    }

    @Override
    public void initdata() {

    }

    public void getData(ArrayList<newsjs.newsdata> data) {
        currMenu=0;
        an = data;
        ma = new MyAdapter();
        lv.setAdapter(ma);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currMenu=position;
                ma.notifyDataSetChanged();
                toggle();
                getNews(position);
            }
        });

    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return an.size();
        }

        @Override
        public Object getItem(int position) {
            return an.get(position).title;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(mActivity, R.layout.lv_items, null);
            TextView tv = (TextView) convertView.findViewById(R.id.lv_tv);
            tv.setText(an.get(position).title);
            if (position==currMenu){
                tv.setEnabled(true);
            }else {
                tv.setEnabled(false);
            }
            return convertView;

        }
    }
    public void toggle(){
       MainActivity mainUi= (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUi.getSlidingMenu();
        slidingMenu.toggle();
    }
    public void getNews(int postion){

        MainActivity mainUi= (MainActivity) mActivity;
        ContenFragment content = mainUi.getContent();
        newsitems n= (newsitems) content.al.get(1);
        n.setcurrentView(postion);
    }
}
