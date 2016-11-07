package com.example.freedomsalt;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by 欧宇志 on 2016/10/14.
 */
public class SettingItems extends RelativeLayout {
    SharedPreferences spf;
    String NameSp="http://schemas.android.com/apk/res/com.example.freedomsalt";
    private String title;
    private String desoff;
    private String deson;
    private TextView tv_title;
    private TextView tv_open;
    private CheckBox cb;


    public SettingItems(Context context) {
           this(context,null);
    }
    public SettingItems(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SettingItems(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        spf=context.getSharedPreferences("config",Context.MODE_PRIVATE);
        View.inflate(context,R.layout.gvitem,this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_open = (TextView) findViewById(R.id.tv_open);
        cb = (CheckBox) findViewById(R.id.cb1);
        initText(attrs);
        tv_title.setText(title);
    }

    private void initText(AttributeSet attrs) {
        title = attrs.getAttributeValue(NameSp,"destitle");
        desoff = attrs.getAttributeValue(NameSp,"desoff");
        deson = attrs.getAttributeValue(NameSp,"deson");
    }
    public boolean isCheck(){
        return cb.isChecked();
    }
    public void setCheck(boolean isChenck){
        cb.setChecked(isChenck);
        if(isChenck){
            tv_open.setText(deson);

        }else {
            tv_open.setText(desoff);

        }
    }


}
