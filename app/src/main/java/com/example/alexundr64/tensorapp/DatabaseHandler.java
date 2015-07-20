package com.example.alexundr64.tensorapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alexundr64 on 17.07.2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "PeoplesDatabase";
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE peoples " +
                "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "firstname TEXT, " +
                "lastname TEXT ) ";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS peoples";
        db.execSQL(sql);
        onCreate(db);
    }
}
