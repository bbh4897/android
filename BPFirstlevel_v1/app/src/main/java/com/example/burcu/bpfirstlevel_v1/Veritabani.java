package com.example.burcu.bpfirstlevel_v1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Veritabani extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db_bp";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE = "konum_bilgileri";
    private static final String ROW_ID = "id";
    private static final String ROW_KONUM_AD = "ad";
//    imageView eklemeye bak


    public Veritabani(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + "(" + ROW_ID + " INTEGER PRIMARY KEY, " + ROW_KONUM_AD + " TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXİSTS " + TABLE);
    }

    public void Ekle(String konum_ad){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ROW_KONUM_AD, konum_ad.trim());
        db.insert(TABLE, null, cv);
        db.close();
    }

    public List<String> listele(){

        List<String> veriler = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] sutunlar = {ROW_ID, ROW_KONUM_AD};
        Cursor cursor = db.query(TABLE, sutunlar, null, null, null, null, null);
        while(cursor.moveToNext()){
            veriler.add(cursor.getInt(0) + " - " + cursor.getString(1));
        }
        return veriler;
    }


}
