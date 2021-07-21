package com.example.pocket;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String name="user_details.db";
    public static final String TABLE_NAME_1 = "user_details";
    public static final String COL_1_1="Id";
    public static final String COL_1_2="Phone Number";
    public static final String COL_1_3="Name";
    public static final String COL_1_4="PassCode";
    public static final String COL_1_5="Business";
    public static final String COL_1_6="Location";
    public static final String COL_1_7="Created_Time";
    public static final String COL_1_8="Status";
    public static final String COL_1_9 ="Image";

    public DatabaseHelper(Context context){
        super(context,name,null,1);
        SQLiteDatabase dp=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("create table %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,%s INTEGER,%s TEXT,%s TEXT,%s TEXT,%s TEXT,%s DEFAULT CURRENT_TIMESTAMP,%s INTEGER,%s blob)", TABLE_NAME_1, COL_1_1, COL_1_2, COL_1_3, COL_1_4, COL_1_5, COL_1_6, COL_1_7, COL_1_8, COL_1_9));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_1);
    }
    Cursor userNameExist(String phone_no,int status){
        String query = "SELECT * FROM "+TABLE_NAME_1+" WHERE "+COL_1_2+" ="+phone_no+ " AND "+COL_1_8+" = "+status;
        Cursor cursor=null;
        SQLiteDatabase db=this.getReadableDatabase();
        if(db!=null){
            cursor=db.rawQuery(query,null);
            return  cursor;
        }
        return cursor;
    }
}
