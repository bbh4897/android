package com.example.burcu.bpfirstlevel_v1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Veritabani extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db_bp";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE = "konum_bilgileri";



    public Veritabani(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, newimage blob)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXİSTS " + TABLE);
        onCreate(db);
    }

    public boolean addData(String name, byte[] img){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("newimage", img);
        long result = db.insert(TABLE, null, contentValues);
        if(result==-1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from " + TABLE;
        Cursor data = db.rawQuery(query,null);
        return data;
    }
}
