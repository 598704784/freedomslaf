package com.example.signaldetection;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class lishi extends AppCompatActivity {
    ListView lv;
    List<File> lii;
    private MyAdapter ma;
    List<File> lif;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lishi);
        ActionBar ab=getSupportActionBar();
        ab.setTitle("截屏记录");
        ab.setDisplayShowHomeEnabled(true);
        lii=new ArrayList<>();
        lif=new ArrayList<>();
        File fi=new File("sdcard/download");
        initFile(fi);
        initView();
    }

    private void initFile(File path) {
        if (path!=null){
            if (path.isDirectory()){
                File[] files=path.listFiles();
                for (File file:files){
                    initFile(file);
                }
            }else {
                lif.add(path);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 100:
                for (File ii:lii){
                    lif.remove(ii);
                    ii.delete();

                ma.notifyDataSetChanged();
                } lii.removeAll(lii);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem mi1=menu.add(0,100,0,"删除");
        mi1.setIcon(android.R.drawable.ic_menu_delete);
        mi1.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return super.onCreateOptionsMenu(menu);
    }

    private void initView() {
        lv= (ListView) findViewById(R.id.lv);
        ma = new MyAdapter();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                File fi=lif.get(position);
                Intent intent =new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(fi),"image/*");
                startActivity(intent);
            }
        });

        lv.setAdapter(ma);

    }
    class Hoder {
        public ImageView iv1;
        public TextView tv1;
        public TextView tv2;
        public CheckBox cb1;
        public boolean flag;

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }
    }
    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return lif.size();
        }

        @Override
        public Object getItem(int position) {
            return lif.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Hoder hd=  null;
            if (convertView==null){
                convertView=View.inflate(getBaseContext(),R.layout.lisitems,null);
                hd=new Hoder();
                hd.iv1= (ImageView) convertView.findViewById(R.id.iv1);
                hd.tv1= (TextView) convertView.findViewById(R.id.tv);
                hd.tv2= (TextView) convertView.findViewById(R.id.tv_data);
                hd.cb1 = (CheckBox) convertView.findViewById(R.id.cb1);
                final Hoder finalHd = hd;

                hd.cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (isChecked){
                            lii.add(lif.get(position));
                            finalHd.setFlag(false);
                        }else {
                            lii.remove(lif.get(position));
                            finalHd.setFlag(true);
                        }
                    }
                });
                if (hd.isFlag()){
                    hd.cb1.setChecked(true);
                }else {
                    hd.cb1.setChecked(false);
                }
               convertView.setTag(hd);
            }else {
                hd= (Hoder) convertView.getTag();
            }
            File fi=lif.get(position);
            String str=fi.toString();
            int start=str.lastIndexOf("/");
            int end=str.indexOf(".png");
            long time=Long.parseLong(str.substring(start+1,end));
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d1=new Date(time);
            String timer=sdf.format(d1);
            hd.tv2.setText(timer);
           String text= str.substring(start+1,str.length());
           hd.tv1.setText(text);
            try {
                FileInputStream fis=new FileInputStream(fi);
                Bitmap bm= BitmapFactory.decodeStream(fis);
                hd.iv1.setImageBitmap(bm);
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (hd.isFlag()){
                hd.cb1.setChecked(true);
            }else {
                hd.cb1.setChecked(false);
            }
            return convertView;
        }
    }
}
