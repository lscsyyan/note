package com.zhangqie.notepad.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zhangqie.notepad.entity.UserInfo;

import java.util.ArrayList;
import java.util.List;



public class HelperSQLite extends SQLiteOpenHelper{

    private SQLiteDatabase sqLiteDatabase;


    public HelperSQLite(Context context){
        super(context, UtilDB.DATABASE_NAME, null, UtilDB.DATABASE_VERION);
        sqLiteDatabase=this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UtilDB.showCreateSql());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public boolean insertDate(String userContent,String userYear,String userTime){
        ContentValues contentValues=new ContentValues();
        contentValues.put(UtilDB.USER_CONTENT,userContent);
        contentValues.put(UtilDB.USER_YEAR,userYear);
        contentValues.put(UtilDB.USER_TIME,userTime);
        return sqLiteDatabase.insert(UtilDB.DATABASE_TABLE,null,contentValues)>0;

    }


    public boolean deleteData(String id){
        /*String sql = "delete from "+UtilDB.DATABASE_TABLE+" where "+UtilDB.USER_ID+" = "+id;
        sqLiteDatabase.execSQL(sql);
        return true;*/

        String sql=UtilDB.USER_ID+"=?";
        String[] contentValuesArray=new String[]{String.valueOf(id)};
        return sqLiteDatabase.delete(UtilDB.DATABASE_TABLE,sql,contentValuesArray)>0;
    }

 boolean updateData(String id,String content,String userYear,String userTime){
        ContentValues contentValues=new ContentValues();
        contentValues.put(UtilDB.USER_CONTENT,content);
        contentValues.put(UtilDB.USER_YEAR,userYear);
        contentValues.put(UtilDB.USER_TIME,userTime);
        String sql=UtilDB.USER_ID+"=?";
        String[] strings=new String[]{id};
        return sqLiteDatabase.update(UtilDB.DATABASE_TABLE,contentValues,sql,strings)>0;
    }


    public List<UserInfo> query(){
        List<UserInfo> list=new ArrayList<UserInfo>();
        Cursor cursor=sqLiteDatabase.query(UtilDB.DATABASE_TABLE,null,null,null,null,null,UtilDB.USER_ID+" desc");
        if (cursor!=null){
            while (cursor.moveToNext()){
                UserInfo userInfo=new UserInfo();
                userInfo.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex(UtilDB.USER_ID))));
                userInfo.setUserContent(cursor.getString(cursor.getColumnIndex(UtilDB.USER_CONTENT)));
                userInfo.setUserYear(cursor.getString(cursor.getColumnIndex(UtilDB.USER_YEAR)));
                userInfo.setUserTime(cursor.getString(cursor.getColumnIndex(UtilDB.USER_TIME)));
                list.add(userInfo);
            }
        }
        return list;
    }




}
