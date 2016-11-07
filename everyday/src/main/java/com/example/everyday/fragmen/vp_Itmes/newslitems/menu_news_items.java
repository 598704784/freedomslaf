package com.example.everyday.fragmen.vp_Itmes.newslitems;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.everyday.R;
import com.example.everyday.Views.PullToRefreshListView;
import com.example.everyday.Views.RefreshListView;
import com.example.everyday.js.newsjsdata;
import com.example.everyday.js.staticnum;
import com.example.everyday.util.spfUtil;
import com.example.everyday.webActivity;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

/**
 * Created by 欧宇志 on 2016/11/1.
 */
public class menu_news_items extends Base {
    String Url;
    private ListView lv;
    private ArrayList<newsjsdata.itemsnews> arrayList;
    private ArrayList<newsjsdata.titlenews> al;
    private final BitmapUtils bu;
    private ViewPager vp2;
    private View v2;
    private CirclePageIndicator cpi;
    private TextView tv_vp;
    private SwipeRefreshLayout srl;


    public menu_news_items(Activity activity, String url) {
        super(activity);
        Url=staticnum.HTTP+url;
        bu = new BitmapUtils(activity);
        bu.configDefaultLoadFailedImage(R.drawable.news_pic_default);
        initdata();
    }

    @Override
    public View initView() {
        View view = View.inflate(activity, R.layout.menu_news_items, null);
        v2 = View.inflate(activity, R.layout.menu_titele_,null);
        vp2 = (ViewPager) v2.findViewById(R.id.vp_title);
        tv_vp = (TextView) v2.findViewById(R.id.tv_t3);
        cpi = (CirclePageIndicator) v2.findViewById(R.id.vp_cpi);
        lv = (ListView) view.findViewById(R.id.lv_menu_news_items);
        srl = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        return view;
    }

    @Override
    public void initdata() {
        super.initdata();
        String json = spfUtil.getString(activity, "newsmen", "");
        if (!TextUtils.isEmpty(json)){
            System.out.println("缓存");
            prossJson(json);
        }else {

            proseHttp(Url);
        }


    }

    private void proseHttp(String url) {
        HttpUtils hu = new HttpUtils();
        hu.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {
                String json = (String) responseInfo.result;
                prossJson(json);
                srl.setRefreshing(false);
                Toast.makeText(activity, "加载成功", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(HttpException e, String s) {
                srl.setRefreshing(false);
                Toast.makeText(activity, "加载失败", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void prossJson(String json) {
        Gson gs = new Gson();
        newsjsdata newsjsdata = gs.fromJson(json, newsjsdata.class);
        arrayList = newsjsdata.data.news;
        al=newsjsdata.data.topnews;
        vp2.setAdapter(new Pager());
        cpi.setViewPager(vp2);
        cpi.setSnap(true);
        lv.addHeaderView(v2);
        lv.setAdapter(new MyAdapter());
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int headecount=lv.getHeaderViewsCount();
                position=position-headecount;
                String url=arrayList.get(position).url;
                Intent intent=new Intent(activity,webActivity.class);
                intent.putExtra("url",url);
                activity.startActivity(intent);
                TextView tv= (TextView) view.findViewById(R.id.tv_t1);
                tv.setTextColor(Color.GRAY);
                String str=spfUtil.getString(activity,"is_read","");
                if (!str.contains(arrayList.get(position).id)){
                str=str+arrayList.get(position).id;
                spfUtil.putString(activity,"is_read",str+",");

                }
            }
        });
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                proseHttp(Url);

            }
        });
        cpi.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                com.example.everyday.js.newsjsdata.titlenews titlenews = al.get(position);
               String str= titlenews.title;
                tv_vp.setText(str);
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });tv_vp.setText(al.get(0).title);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Hoder hd;
            if (convertView == null) {
                hd = new Hoder();
                convertView = View.inflate(activity, R.layout.menu_news_lv, null);
                hd.iv = (ImageView) convertView.findViewById(R.id.rl_iv);
                hd.tv1 = (TextView) convertView.findViewById(R.id.tv_t1);
                hd.tv2 = (TextView) convertView.findViewById(R.id.tv_t2);
                convertView.setTag(hd);
            } else {
                hd = (Hoder) convertView.getTag();
            }
            newsjsdata.itemsnews news = arrayList.get(position);
            hd.tv1.setText(news.title);
            hd.tv2.setText(news.pubdate);
            HttpUtils hu = new HttpUtils();
            bu.display(hd.iv, arrayList.get(position).listimage);
            String id=spfUtil.getString(activity,"is_read","");
            System.out.println(id);
            if (id.contains(news.id+"")){
                hd.tv1.setTextColor(Color.GRAY);
            }else {
                hd.tv1.setTextColor(Color.BLACK);
            }

            return convertView;
        }
    }

    class Hoder {
        ImageView iv;
        TextView tv1;
        TextView tv2;
    }
    public class Pager extends PagerAdapter{

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
            ImageView iv=new ImageView(activity);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            bu.display(iv,al.get(position).topimage);
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
