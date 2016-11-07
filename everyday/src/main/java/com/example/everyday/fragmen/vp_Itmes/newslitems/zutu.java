package com.example.everyday.fragmen.vp_Itmes.newslitems;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.everyday.R;
import com.example.everyday.js.newsjs;
import com.example.everyday.js.staticnum;
import com.example.everyday.js.zudata;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

/**
 * Created by 欧宇志 on 2016/10/31.
 */
public class zutu extends Base {

    private ListView lv;
    private GridView gv;
    private ArrayList<zudata.datanews> news;
    private final BitmapUtils bu;
    ImageButton ibb;
    boolean islv=true;

    public zutu(Activity activity, ImageButton ib) {
        super(activity);
        initdata();
        this.ibb=ib;
        bu = new BitmapUtils(activity);
        ibb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (islv){
                    lv.setVisibility(View.GONE);
                    gv.setVisibility(View.VISIBLE);
                    ibb.setImageResource(R.drawable.icon_pic_grid_type);
                    islv=false;
                }else {
                    lv.setVisibility(View.VISIBLE);
                    gv.setVisibility(View.GONE);
                    ibb.setImageResource(R.drawable.icon_pic_list_type);
                    islv=true;
                }
            }
        });
    }

    @Override
    public View initView() {
        View view =View.inflate(activity, R.layout.zu_layout,null);
        lv = (ListView) view.findViewById(R.id.photo_lv);
        gv = (GridView) view.findViewById(R.id.photo_gv);

        return view;
    }

    @Override
    public void initdata() {
        super.initdata();
        proHttp(staticnum.ZTURL);

    }

    private void proHttp(String zturl) {
        HttpUtils hu=new HttpUtils();
       hu.send(HttpRequest.HttpMethod.GET, zturl, new RequestCallBack<Object>() {
           @Override
           public void onSuccess(ResponseInfo<Object> responseInfo) {
               String json= (String) responseInfo.result;
               System.out.println(json.toString());
               prosGson(json);
           }

           @Override
           public void onFailure(HttpException e, String s) {

           }
       });
    }

    private void prosGson(String json) {
        Gson gs=new Gson();
        zudata zudata = gs.fromJson(json, zudata.class);
        news = zudata.data.news;
        MyAdapter ma=new MyAdapter();
        lv.setAdapter(ma);
        gv.setAdapter(ma);
    }
    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return news.size();
        }

        @Override
        public Object getItem(int position) {
            return news.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Hoder hd;
            if (convertView==null){
                hd=new Hoder();
                convertView=View.inflate(activity,R.layout.zu_items,null);
                hd.tv= (TextView) convertView.findViewById(R.id.zu_tv);
                hd.iv= (ImageView) convertView.findViewById(R.id.iv_zu);
                convertView.setTag(hd);
            }else {
                hd= (Hoder) convertView.getTag();
            }
            zudata.datanews datanews = news.get(position);
            hd.tv.setText(datanews.title);
            bu.display(hd.iv,datanews.listimage);
            return convertView;
        }
    }
    class Hoder {
        TextView tv;
        ImageView iv;
    }
}

