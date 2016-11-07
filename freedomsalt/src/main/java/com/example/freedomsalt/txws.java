package com.example.freedomsalt;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

import services.MyService;
import utils.DateBase;
import utils.people;

public class txws extends AppCompatActivity {
    DateBase db;
    List<people> lip;
    List<people> lip2;
    MyAdapter ma;
    private AlertDialog ab;
    private String i;
    boolean flag=false;
    int count=0;
    private ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_txws);
        db=DateBase.getDateBase(this);
        initTitle();
        lip=db.find(0);
        initView();
    }

    private void initView() {
        count=db.getCount();
        ma=new MyAdapter();
        lv = (ListView) findViewById(R.id.lv);
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                if (lip!=null){
                    if (scrollState== AbsListView.OnScrollListener.SCROLL_STATE_IDLE&& lv.getLastVisiblePosition()>=lip.size()-1&&!flag){
                       if (count>lip.size()){
                           System.out.println("213123123");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                flag=true;
                                lip2=db.find(lip.size());
                                lip.addAll(lip2);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                ma.notifyDataSetChanged();
                                    }
                                });
                                flag=false;
                            }
                        }).start();

                       }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        lv.setAdapter(ma);
    }

    private void initTitle() {
        ActionBar ab=getSupportActionBar();
        ab.setTitle("黑名单管理");
        ab.setDisplayShowHomeEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 100:
                showdialog();
                break;
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem mi1=menu.add(0,100,0,"增加");
        mi1.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return super.onCreateOptionsMenu(menu);
    }
    class Hoder {
        public TextView tv_phone;
        public TextView tv_type;
        public ImageView tv_delete;
    }
    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return lip.size();
        }

        @Override
        public Object getItem(int position) {
            return lip.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Hoder hd=null;
            if (convertView==null){
                convertView=View.inflate(getBaseContext(),R.layout.txitems,null);
                hd=new Hoder();
                hd.tv_phone= (TextView) convertView.findViewById(R.id.tv_number);
                hd.tv_type= (TextView) convertView.findViewById(R.id.tv_type);
                hd.tv_delete= (ImageView) convertView.findViewById(R.id.iv_delete);
                hd.tv_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.delete(lip.get(position).phone);
                        lip.remove(position);
                        ma.notifyDataSetChanged();
                    }
                });
                convertView.setTag(hd);
            }else {
                hd= (Hoder) convertView.getTag();
            }
            hd.tv_phone.setText(lip.get(position).phone);
            switch (lip.get(position).type){
                case "1":
                    hd.tv_type.setText("拦截短信");
                    break;
                case "2":
                    hd.tv_type.setText("拦截电话");
                    break;
                case "3":
                    hd.tv_type.setText("拦截所有");
                    break;
            }
            return convertView;
        }
    }
    public void showdialog(){
        i = "0";
        AlertDialog.Builder ad=new AlertDialog.Builder(this);
        ab = ad.create();
        View v=View.inflate(this,R.layout.insertdialog,null);
        ab.setView(v);
        final EditText ed= (EditText) v.findViewById(R.id.et_phone);
        final RadioGroup rg= (RadioGroup) v.findViewById(R.id.rglz);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rg.getCheckedRadioButtonId()==R.id.rb1){
                    i ="1";
                }else if (rg.getCheckedRadioButtonId()==R.id.rb2){
                    i ="2";
                }else if (rg.getCheckedRadioButtonId()==R.id.rb3){
                    i ="3";
                }
            }
        });

        Button bt1= (Button) v.findViewById(R.id.bt_01);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ab.dismiss();
            }
        });
        Button bt2= (Button) v.findViewById(R.id.bt_02);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String phone=ed.getText().toString();
                if (!TextUtils.isEmpty(phone)){
                    people p=new people();
                    p.type=i;
                    p.phone=phone;
                    lip.add(0,p);
                    ma.notifyDataSetChanged();
                    db.insert(phone, i+"");
                    ab.dismiss();

                }else {
                    Toast.makeText(txws.this, "电话号码不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ab.show();
    }




}
