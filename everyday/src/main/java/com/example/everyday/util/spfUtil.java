package com.example.everyday.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 欧宇志 on 2016/10/29.
 */
public class spfUtil {
    public static String getString(Context context, String key, String defalue){
        SharedPreferences sp=context.getSharedPreferences("config",Context.MODE_PRIVATE);
        return sp.getString(key,defalue);
    }
    public static void putString(Context context ,String key,String value){
        SharedPreferences sp=context.getSharedPreferences("config",Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }
    public static int getInt(Context context, String key, int defalue){
        SharedPreferences sp=context.getSharedPreferences("config",Context.MODE_PRIVATE);
        return sp.getInt(key,defalue);
    }
    public static void putInt(Context context ,String key,int value){
        SharedPreferences sp=context.getSharedPreferences("config",Context.MODE_PRIVATE);
        sp.edit().putInt(key,value).commit();
    }
    public static boolean getBoolean(Context context,String key,boolean defalue){
        SharedPreferences sp=context.getSharedPreferences("config",Context.MODE_PRIVATE);
        return sp.getBoolean(key,defalue);
    }
    public static void putBoolean(Context context ,String key,boolean value){
        SharedPreferences sp=context.getSharedPreferences("config",Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }
}
