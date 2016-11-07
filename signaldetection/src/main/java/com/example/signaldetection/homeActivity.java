package com.example.signaldetection;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class homeActivity extends AppCompatActivity {

    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ActionBar ab=getSupportActionBar();
        ab.setDisplayShowHomeEnabled(false);
        ab.setTitle("实时监测");
        items it= (items) findViewById(R.id.it1);
        TextView tv= (TextView) it.findViewById(R.id.g4);
        it.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("2312312312312312");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem mi1=menu.add(0,100,0,"自动刷新");
        Switch sw=new Switch(this);
        mi1.setActionView(sw);
        mi1.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;

    }
    public void click(View view){
        switch (view.getId()){
            case R.id.tvjt:
                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){

                pic();
                Toast.makeText(homeActivity.this, "截图成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(homeActivity.this, "SD卡未准备好", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tvls:
                startActivity(new Intent(this,lishi.class));
                break;
        }

    }
    public void pic(){
        System.out.println("fasdasd");
        View view=getWindow().getDecorView();
        view.destroyDrawingCache();
        Rect rect=new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int top = rect.top;
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        int wid=defaultDisplay.getWidth();
        int hei=defaultDisplay.getHeight();
        view.setDrawingCacheEnabled(true);
        Bitmap bm=Bitmap.createBitmap(view.getDrawingCache(),0,top,wid,hei-top);
        long time=System.currentTimeMillis();

        File fi=new File("sdcard/download/"+time+".png");
        if (fi.exists()){
            fi=new File("sdcard/download/"+time+"b"+".png");
        }
        try {
            OutputStream os=new FileOutputStream(fi);
            bm.compress(Bitmap.CompressFormat.PNG,100,os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
