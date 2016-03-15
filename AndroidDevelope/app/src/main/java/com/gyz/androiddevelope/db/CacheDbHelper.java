package com.gyz.androiddevelope.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author: guoyazhou
 * @date: 2016-03-15 15:31
 */
public class CacheDbHelper extends SQLiteOpenHelper {
    private static final String TAG = "CacheDbHelper";

    public CacheDbHelper(Context context, int version) {
        super(context, "cache.db", null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists CacheList " +
                "(id INTEGER primary key autoincrement," +
                "date INTEGER unique," +
                "json text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
