package com.votafore.earthporn.content;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.votafore.earthporn.models.DataBaseRow;

import java.util.ArrayList;
import java.util.List;

public class DataBaseManagerSQLite extends DataBaseManager {

    private SQLiteOpenHelper helper;
    private Context context;

    public DataBaseManagerSQLite(Context context){
        this.context = context;
        helper = new DataBase(context);
    }

    @Override
    public List<DataBaseRow> getData(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        List<DataBaseRow> result = new ArrayList<>();

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_MAIN, projection, selection, selectionArgs, null, null, sortOrder);

        cursor.moveToFirst();

        do {

            DataBaseRow row = new DataBaseRow();
            row.id = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
            row.url = cursor.getString(cursor.getColumnIndex(COLUMN_URL));

            result.add(row);

            cursor.moveToNext();

        } while (!cursor.isAfterLast());

        return result;
    }

    @Override
    public void insert(ContentValues values) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.insert(TABLE_NAME_MAIN, null, values);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        SQLiteDatabase db = helper.getWritableDatabase();

        //int count = db.update(TABLE_NAME_MAIN, values, selection, selectionArgs);

        return db.update(TABLE_NAME_MAIN, values, selection, selectionArgs);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db = helper.getWritableDatabase();
        int count = db.delete(TABLE_NAME_MAIN, selection, selectionArgs);
        context.getContentResolver().notifyChange(uri, null);

        return count;
    }


    /************* DATABASE **************/

//    public static String TABLE_NAME_MAIN = "main";
//
//    private static String NAME = "DataBase.db";
//    private static int VERSION = 2;
//
//    public static String COLUMN_ID  = "_id";
//    public static String COLUMN_URL = "_url";

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
