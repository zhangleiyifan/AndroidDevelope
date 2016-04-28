package com.gyz.androiddevelope.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gyz.androiddevelope.util.L;

/**
 * @author: guoyazhou
 * @date: 2016-04-27 16:37
 */
public class TngouListDbHelper extends SQLiteOpenHelper {
    private static final String TAG = "TngouDbHelper";

    public TngouListDbHelper(Context context, int version) {
        super(context, "tngouList.db", null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        L.i(TAG,"create table tngouListDbHelper_____________________________");
        db.execSQL("create table if not exists tngouList " +
                "(id INTEGER primary key autoincrement," +
                "date INTEGER unique," +
                "typeid INTEGER ,"+
                "json text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        L.i("onUpgrade table TngouListDbHelper++++++++++++++++++");
    }
}
