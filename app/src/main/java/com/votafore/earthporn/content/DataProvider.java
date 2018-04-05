package com.votafore.earthporn.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.text.TextUtils;

import com.votafore.earthporn.models.DataBaseRow;

import java.util.List;

public class DataProvider extends ContentProvider {

    private static String authority = "com.earthporn.provider.content";
    private static String path = "images";

    private static String imageSetType  = "vnd.android.cursor.dir/vnd."+ authority +"."+path;
    private static String imageItemType = "vnd.android.cursor.item/vnd."+ authority +"."+path;

    private static UriMatcher matcher;

    private static final int URI_IMAGES    = 1;
    private static final int URI_IMAGES_ID = 2;

    public static Uri BASE_URI = Uri.parse("content://"+ authority +"/"+path);

    static {
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(authority, path, URI_IMAGES);
        matcher.addURI(authority, path+"/*", URI_IMAGES_ID);
    }

    //private SQLiteOpenHelper helper;
    private DataBaseManager manager;

    @Override
    public boolean onCreate() {
        //helper = new DataBase(getContext());

        // TODO: get preferences and get proper data base manager

        manager = new DataBaseManagerSQLite(getContext());

        return false;
    }

    @Override
    public String getType(Uri uri) {
        switch (matcher.match(uri)){
            case URI_IMAGES:
                return imageSetType;
            case URI_IMAGES_ID:
                return imageItemType;
        }
        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        checkCondition(uri, selection);

//        try (SQLiteDatabase db = helper.getReadableDatabase()){
//
//            Cursor result = db.query(DataBase.TABLE_NAME_MAIN, projection, selection, selectionArgs, null, null, sortOrder);
//
//            // просим ContentResolver уведомлять этот курсор
//            // об изменениях данных в CONTACT_CONTENT_URI
//            result.setNotificationUri(getContext().getContentResolver(), BASE_URI);
//
//            return result;
//        }

//        SQLiteDatabase db = helper.getReadableDatabase();
//
//        Cursor result = db.query(DataBase.TABLE_NAME_MAIN, projection, selection, selectionArgs, null, null, sortOrder);
//
//        // просим ContentResolver уведомлять этот курсор
//        // об изменениях данных в CONTACT_CONTENT_URI
//        result.setNotificationUri(getContext().getContentResolver(), BASE_URI);

        List<DataBaseRow> list = manager.getData(uri, projection, selection, selectionArgs, sortOrder);

        MatrixCursor cursor = new MatrixCursor(new String[]{DataBaseManager.COLUMN_ID, DataBaseManager.COLUMN_URL});

        for (DataBaseRow row : list){
            cursor.newRow()
                    .add(DataBaseManager.COLUMN_ID, row.id)
                    .add(DataBaseManager.COLUMN_URL, row.url);
        }

        return cursor;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        checkCondition(uri, selection);

//        try (SQLiteDatabase db = helper.getWritableDatabase()){
//            int count = db.delete(DataBase.TABLE_NAME_MAIN, selection, selectionArgs);
//            getContext().getContentResolver().notifyChange(uri, null);
//            return count;
//        }

//        SQLiteDatabase db = helper.getWritableDatabase();
//        int count = db.delete(DataBase.TABLE_NAME_MAIN, selection, selectionArgs);
//        getContext().getContentResolver().notifyChange(uri, null);

        int count = manager.delete(uri, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        if (matcher.match(uri) != URI_IMAGES){
            throw new IllegalArgumentException("Wrong URI: " + uri);
        }

//        try (SQLiteDatabase db = helper.getWritableDatabase()){
//
//            String id = values.getAsString(DataBase.COLUMN_ID);
//
//            Cursor cursor = db.query(DataBase.TABLE_NAME_MAIN, null, DataBase.COLUMN_ID+"=?", new String[]{id}, null, null, null);
//
//            if (cursor.getCount() == 0){
//                db.insert(DataBase.TABLE_NAME_MAIN, null, values);
//            }
//
//            Uri resultUri = Uri.withAppendedPath(BASE_URI, id);
//            getContext().getContentResolver().notifyChange(resultUri, null);
//            return resultUri;
//        }




//        SQLiteDatabase db = helper.getWritableDatabase();
//
//        String id = values.getAsString(DataBase.COLUMN_ID);
//
//        Cursor cursor = db.query(DataBase.TABLE_NAME_MAIN, null, DataBase.COLUMN_ID+"=?", new String[]{id}, null, null, null);
//
//        if (cursor.getCount() == 0){
//            db.insert(DataBase.TABLE_NAME_MAIN, null, values);
//        }





        String id = values.getAsString(DataBaseManager.COLUMN_ID);
        List<DataBaseRow> list = manager.getData(uri, null, DataBaseManager.COLUMN_ID+"=?", new String[]{id}, null);

        if (list.size() == 0){
            manager.insert(values);
        }




        Uri resultUri = Uri.withAppendedPath(BASE_URI, id);
        getContext().getContentResolver().notifyChange(resultUri, null);

        return resultUri;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        checkCondition(uri, selection);

//        try (SQLiteDatabase db = helper.getWritableDatabase()){
//            int count = db.update(DataBase.TABLE_NAME_MAIN, values, selection, selectionArgs);
//            getContext().getContentResolver().notifyChange(uri, null);
//            return count;
//        }

//        SQLiteDatabase db = helper.getWritableDatabase();
//
//        int count = db.update(DataBase.TABLE_NAME_MAIN, values, selection, selectionArgs);
//        getContext().getContentResolver().notifyChange(uri, null);





        int count = manager.update(uri, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }


    private void checkCondition(Uri uri, String selection){

        switch (matcher.match(uri)){
            case URI_IMAGES:
                break;
            case URI_IMAGES_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)){
                    selection = DataBase.COLUMN_ID+"="+id;
                } else {
                    selection = selection + " AND " + DataBase.COLUMN_ID + "=" + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
    }
}
