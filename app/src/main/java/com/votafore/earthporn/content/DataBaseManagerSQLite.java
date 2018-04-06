package com.votafore.earthporn.content;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseManagerSQLite extends DataBaseManager {

    private SQLiteOpenHelper helper;

    public DataBaseManagerSQLite(Context context){
        helper = new DataBase(context);
    }

    @Override
    public Cursor getData(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = helper.getReadableDatabase();
        return db.query(TABLE_NAME_MAIN, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public void insert(ContentValues values) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.insert(TABLE_NAME_MAIN, null, values);
    }

    @Override
    public int update(ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.update(TABLE_NAME_MAIN, values, selection, selectionArgs);
    }

    @Override
    public int delete(String selection, String[] selectionArgs) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int count = db.delete(TABLE_NAME_MAIN, selection, selectionArgs);
        return count;
    }



    /************* DATABASE **************/

    public class DataBase extends SQLiteOpenHelper {

        public DataBase(Context context){
            super(context, NAME, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE "+TABLE_NAME_MAIN+" ("+COLUMN_ID+" TEXT PRIMARY KEY, "+COLUMN_URL+" TEXT)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE "+TABLE_NAME_MAIN);
            onCreate(db);
        }
    }
}
