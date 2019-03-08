package com.example.burcu.bpfirstlevel_v1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Veritabani extends SQLiteOpenHelper {


    public Veritabani(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    public void queryData(String sql){

        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void insertData(String name, byte[] image){

        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO KONUMBILGILERI VALUES(NULL, ?, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, name);
        statement.bindBlob(2, image);

        statement.executeInsert();

    }

    public void updateData(String name, byte[] image, int id){

        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE KONUMBILGILERI SET name=?, image=? WHERE id=?";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1, name);
        statement.bindBlob(2, image);
        statement.bindDouble(3,(double)id);

        statement.execute();
        database.close();
    }

    public void deleteData(int id){

        SQLiteDatabase database = getWritableDatabase();
        String sql = "DELETE FROM KONUMBILGILERI WHERE id=?";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1,(double)id);

        statement.execute();
        database.close();
    }

    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    ///////////////////////////////////////////////////

    public void insertWLevel(String level, String BUTTONID, String KONUMAD){

        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO TABLE" + BUTTONID + KONUMAD + " VALUES(NULL, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, level);
        Log.i("BUTTONIDDDDDDDDDDDDDD" , BUTTONID + KONUMAD);
        statement.executeInsert();

    }



}