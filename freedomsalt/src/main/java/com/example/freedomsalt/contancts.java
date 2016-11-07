package com.example.freedomsalt;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class contancts extends AppCompatActivity {
    ListView lv;
    List<Map<String,String>> li=new ArrayList<>();
    Map<String,String> mp=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contancts);
        Log.d("dsad","asdas");
        intiResover();
        initView();
    }

    private void initView() {
        lv= (ListView) findViewById(R.id.lv);
        lv.setAdapter(new SimpleAdapter(this,li,R.layout.listitems,
                new String[]{"name","phone"},new int[]{R.id.tv_name,R.id.tv_phone} ));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String phone=li.get(position).get("phone");
                Intent it=new Intent();
                it.putExtra("lian",phone);
                setResult(99,it);
                finish();
            }
        });
    }

    private void intiResover() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ContentResolver cr=getContentResolver();
                Cursor idcursor = cr.query(Uri.parse("content://com.android.contacts/raw_contacts"), new String[]{"contact_id"}, null, null, null);
                if(idcursor!=null){
                    while (idcursor.moveToNext()){
                        mp=new HashMap<String, String>();
                        String id=idcursor.getString(idcursor.getColumnIndex("contact_id"));
                        Cursor cursor = cr.query(Uri.parse("content://com.android.contacts/data"), new String[]{"data1", "mimetype"}, "contact_id=?", new String[]{id}, null);
                        if (cursor!=null){
                            while (cursor.moveToNext()){
                                String data=cursor.getString(cursor.getColumnIndex("data1"));
                                String type=cursor.getString(cursor.getColumnIndex("mimetype"));
                                Log.d("dsad","name"+data+"::::pp:");
                                if (type.equals("vnd.android.cursor.item/phone_v2")){
                                    mp.put("phone",data);
                                }else if (type.equals("vnd.android.cursor.item/name")){
                                    mp.put("name",data);
                                }

                            }
                        }
                        li.add(mp);
                    }
                }
            }
        }).start();
    }

}
