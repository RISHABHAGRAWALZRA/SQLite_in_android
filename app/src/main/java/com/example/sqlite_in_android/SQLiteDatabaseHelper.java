package com.example.sqlite_in_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteDatabaseHelper extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 1;
    private final static String DATABASE_NAME = "univercity";
    
    
    public SQLiteDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sqlCommand = "CREATE TABLE students (_id Integer Primary key Autoincrement , name Text, email text)";
        sqLiteDatabase.execSQL(sqlCommand);

        String sqlInsert = "INSERT INTO students (name, email) VALUES (\"Ris\",\"ris@gmail.com\")";
        sqLiteDatabase.execSQL(sqlInsert);

        ContentValues contentValues= new ContentValues();
        contentValues.put("name","Bis");
        contentValues.put("email","bis@gmail.com");
        sqLiteDatabase.insert("students",null,contentValues);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sqlStatment = "ALTER TABLE students ADD COLUMN marks Double";
        sqLiteDatabase.execSQL(sqlStatment);
    }

    public void insert(SQLiteDatabase db, String tableName, String name, String email){

        ContentValues contentValues= new ContentValues();
        contentValues.put("name",name);
        contentValues.put("email",email);
        db.insert(tableName,null,contentValues);

    }

    public Cursor read(SQLiteDatabase db, String tableName){

        Cursor cursor = db.query(tableName, null, null, null, null, null, null);
        return cursor;
    }

    public void updateName(SQLiteDatabase db, String tableName, String oldName, String newName){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",newName);
        db.update(tableName,contentValues,"name=?",new String[]{oldName});
    }

    public void delete(SQLiteDatabase db, String tableName, String name){
        db.delete(tableName,"name=?",new String[]{name});
    }

}
