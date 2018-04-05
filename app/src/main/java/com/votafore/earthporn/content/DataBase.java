package com.votafore.earthporn.content;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {

    public static String TABLE_NAME_MAIN = "main";

    private Context context;

    private static String NAME = "DataBase.db";
    private static int VERSION = 2;

    public static String COLUMN_ID  = "_id";
    public static String COLUMN_URL = "_url";

    public DataBase(Context context){
        super(context, NAME, null, VERSION);
        this.context = context;
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
