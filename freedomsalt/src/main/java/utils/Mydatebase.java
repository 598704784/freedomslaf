package utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 欧宇志 on 2016/10/22.
 */
public class Mydatebase extends SQLiteOpenHelper {
    public Mydatebase(Context context) {
        super(context, "black", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table blackNumber(_id integer primary key autoincrement,phone char(20),type char(5))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
