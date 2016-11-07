package com.example.freedomsalt;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class phone_takeActivity extends AppCompatActivity {
    Handler hd=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(i==0){
                bt_pr.setVisibility(View.INVISIBLE);
            }else {
                bt_pr.setVisibility(View.VISIBLE);
            }
            if (i==3){
                bt_next.setText("完成设置");
            }else {
                bt_next.setText("下一页");
            }
        }
    };
    ViewPager vp;
    List<View> liv = new ArrayList<>();
    View v1, v2, v3, v4;
    Button bt_next, bt_pr;
    Button bt_lx;
    EditText ed_phone;
    SharedPreferences spf;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_take);
        getSupportActionBar().hide();
        spf=getSharedPreferences("config",MODE_PRIVATE) ;
        initView();
    }

    private void initView() {
        vp = (ViewPager) findViewById(R.id.vp);
        bt_next = (Button) findViewById(R.id.bt_next);
        bt_pr = (Button) findViewById(R.id.bt_pr);

        v1 = new sept1(this).getView();
        v2 = new sept2(this).getView();
        v3 = new sept3(this).getView();
        ed_phone= (EditText) v3.findViewById(R.id.ed_phone);
        bt_lx= (Button) v3.findViewById(R.id.bt_lx);
        bt_lx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(new Intent(phone_takeActivity.this,contancts.class));
                startActivityForResult(it,0);
            }
        });
        v4 = new sept4(this).getView();
        liv.add(v1);
        liv.add(v2);
        liv.add(v3);
        liv.add(v4);
        vp.setAdapter(new MyAdapter());
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        i=0;
                        hd.sendEmptyMessage(0);
                        break;
                    case 1:
                        i=1;
                        hd.sendEmptyMessage(0);
                        break;
                    case 2:
                        i=2;
                        hd.sendEmptyMessage(0);
                        break;
                    case 3:
                        i=3;
                        hd.sendEmptyMessage(0);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i<=3){
                    i++;
                }
                if (i==4){
                    String phone=ed_phone.getText().toString();
                    spf.edit().putString("phone",phone).commit();
                    startActivity(new Intent(phone_takeActivity.this,finshTake.class));
                    finish();

                }
                vp.setCurrentItem(i);
            }
        });
        bt_pr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i--;
                vp.setCurrentItem(i);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==99){
           String lx= data.getStringExtra("lian");
            if (!TextUtils.isEmpty(lx))  {
            ed_phone.setText(lx);

            }
        }
    }

    class MyAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return liv.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(liv.get(position));
            return liv.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(liv.get(position));
        }
    }
}
