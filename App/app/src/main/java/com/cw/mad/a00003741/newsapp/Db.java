package com.cw.mad.a00003741.newsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Db extends SQLiteOpenHelper {

    SQLiteDatabase db;
    Context context;
    int version;

    public Db(Context context){
        super(context, "DB", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users (_id integer primary key autoincrement, username text, password text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists users");
        onCreate(db);
    }

    public long insert(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password",password);
        return db.insert("users",null,contentValues);
    }

    public Cursor read(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from users",null);
        return cursor;
    }

    public void update(String username, String password, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update users set username ='" + username +"', password ='" + password + "' where _id = "+id);
    }
}