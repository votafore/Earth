package com.votafore.earthporn.content;

import android.content.ContentValues;
import android.net.Uri;

import com.votafore.earthporn.models.DataBaseRow;

import java.util.List;

public abstract class DataBaseManager {

    public static String TABLE_NAME_MAIN = "main";

    protected String NAME = "DataBase.db";
    protected int VERSION = 4;

    public static String COLUMN_ID  = "_id";
    public static String COLUMN_URL = "_url";


    public abstract List<DataBaseRow> getData(String[] projection, String selection, String[] selectionArgs, String sortOrder);

    public abstract void insert(ContentValues values);

    public abstract int update(ContentValues values, String selection, String[] selectionArgs);

    public abstract int delete(String selection, String[] selectionArgs);
}
