package com.example.freedomsalt;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import services.MyService;

public class sept1  {

    private final View v;

    public sept1(final Context context) {
        v = View.inflate(context, R.layout.activity_sept1,null);


    }
    public View getView(){
        return v;
    }

}
