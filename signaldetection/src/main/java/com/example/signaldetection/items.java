package com.example.signaldetection;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.signaldetection.R;

/**
 * Created by 欧宇志 on 2016/10/16.
 */

public class items extends RelativeLayout {

    private TextView tv_yunyings;
    private TextView g2;
    private TextView g3;
    private TextView g4;
    String name="http://schemas.android.com/apk/res/com.example.signaldetection";
    private String yunyings;
    private int pic;

    public items(Context context) {
        this(context,null);
    }

    public items(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public items(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.items,this);
        initString(attrs);
        initView();
        TextView tv= (TextView) findViewById(R.id.g2);
        tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("fdgdgdfgfd");
            }
        });
    }

    private void initString(AttributeSet attrs) {
        yunyings = attrs.getAttributeValue(name,"yunyings");
       pic= attrs.getAttributeResourceValue(name,"picture",0);

    }


    private void initView() {
        tv_yunyings = (TextView) findViewById(R.id.yungyings);
        g2 = (TextView) findViewById(R.id.g2);
        g3 = (TextView) findViewById(R.id.g3);
        g4 = (TextView) findViewById(R.id.g4);
        Drawable drawable=getResources().getDrawable(pic);
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        tv_yunyings.setText(yunyings);
        tv_yunyings.setCompoundDrawables(null,drawable,null,null);


    }




}
