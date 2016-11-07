package utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 欧宇志 on 2016/10/22.
 */
public class DateBase {

    private  Mydatebase mdb;
    List<people> lip;


    private DateBase(Context context){
        mdb = new Mydatebase(context);
        lip=new ArrayList<>();

    }
    public static DateBase getDateBase(Context context){
        DateBase dateBase=new DateBase(context);
        return dateBase;
    }
    public void insert(String phone ,String type){
        SQLiteDatabase db= mdb.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("phone",phone);
        values.put("type",type);
        db.insert("blackNumber",null,values);
        db.close();
    }
    public void delete(String phone){
        SQLiteDatabase db= mdb.getWritableDatabase();
        db.delete("blackNumber","phone=?",new String[]{phone});
        db.close();
    }
    public void updata(String phone ,String type){
        SQLiteDatabase db= mdb.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("type",type);
        db.update("blackNumber",values,"phone=?",new String[]{phone});
        db.close();
    }
    public List<people> findAll(){
        SQLiteDatabase db= mdb.getWritableDatabase();
        Cursor cursor = db.query("blackNumber", new String[]{"phone", "type"}, null, null, null, null, "_id desc");
        while (cursor.moveToNext()){
            people p=new people();
            p.phone=cursor.getString(cursor.getColumnIndex("phone"));
            p.type=cursor.getString(1);
            lip.add(p);
        }
        cursor.close();
        db.close();
        return lip;
    }
    public List<people> find(int index){
        SQLiteDatabase db= mdb.getWritableDatabase();
        List<people> lip=new ArrayList<>();

        Cursor cursor = db.rawQuery("select phone,type from blackNumber order by _id desc limit ?,20;", new String[]{index + ""});
        while (cursor.moveToNext()){
            people p=new people();
           p.phone= cursor.getString(cursor.getColumnIndex("phone"));
            p.type=cursor.getString(1);
            lip.add(p);
        }
        cursor.close();
        db.close();
        return lip;
    }
    public int getCount(){
        SQLiteDatabase db= mdb.getWritableDatabase();
        int i=0;
        Cursor cursor = db.rawQuery("select count(*) from blackNumber;", null);
        if (cursor.moveToNext()){
            i=cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return i;
    }
}
