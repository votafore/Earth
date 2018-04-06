package com.votafore.earthporn.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.IntDef;
import android.text.TextUtils;
import android.widget.Toast;

import com.votafore.earthporn.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class DataProvider extends ContentProvider {

    private static String authority = "com.earthporn.provider.content";
    private static String path = "images";

    private static String imageSetType  = "vnd.android.cursor.dir/vnd."+ authority +"."+path;
    private static String imageItemType = "vnd.android.cursor.item/vnd."+ authority +"."+path;

    private static UriMatcher matcher;

    private static final int URI_IMAGES    = 1;
    private static final int URI_IMAGES_ID = 2;

    public static Uri BASE_URI = Uri.parse("content://"+ authority +"/"+path);

    @IntDef({DATABASE_TYPE_SQL, DATABASE_TYPE_REALM, DATABASE_TYPE_ROOM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DataBaseType{}

    public static final int DATABASE_TYPE_SQL   = 1;
    public static final int DATABASE_TYPE_REALM = 2;
    public static final int DATABASE_TYPE_ROOM  = 3;

    static {
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(authority, path, URI_IMAGES);
        matcher.addURI(authority, path+"/*", URI_IMAGES_ID);
    }

    private DataBaseManager manager;

    @Override
    public boolean onCreate() {

        SharedPreferences preferences = getContext().getSharedPreferences(getContext().getResources().getString(R.string.shared_preferences_file_name), Context.MODE_PRIVATE);

        try {

            int type = preferences.getInt(getContext().getResources().getString(R.string.shared_preferences_database_type), DATABASE_TYPE_SQL);

            switch (type) {
                case DATABASE_TYPE_SQL:
                    Toast.makeText(getContext(), "current database manager supported ;)", Toast.LENGTH_SHORT).show();
                    break;
                case DATABASE_TYPE_REALM:
                    Toast.makeText(getContext(), "current database manager NOT supported yet", Toast.LENGTH_SHORT).show();
                    break;
                case DATABASE_TYPE_ROOM:
                    Toast.makeText(getContext(), "current database manager NOT supported yet", Toast.LENGTH_SHORT).show();
                    break;
            }

            manager = new DataBaseManagerSQLite(getContext());

        } catch (NullPointerException e) {
            e.printStackTrace();
            manager = new DataBaseManagerSQLite(getContext());
        }

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
        return manager.getData(projection, selection, selectionArgs, sortOrder);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        checkCondition(uri, selection);
        return manager.delete(selection, selectionArgs);
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        if (matcher.match(uri) != URI_IMAGES){
            throw new IllegalArgumentException("Wrong URI: " + uri);
        }

        String id = values.getAsString(DataBaseManager.COLUMN_ID);
        Cursor cursor = manager.getData(null, DataBaseManager.COLUMN_ID+"=?", new String[]{id}, null);

        if (cursor.getCount() == 0){
            manager.insert(values);
        }

        return Uri.withAppendedPath(BASE_URI, id);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        checkCondition(uri, selection);
        return manager.update(values, selection, selectionArgs);
    }


    private void checkCondition(Uri uri, String selection){

        switch (matcher.match(uri)){
            case URI_IMAGES:
                break;
            case URI_IMAGES_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)){
                    selection = DataBaseManager.COLUMN_ID+"="+id;
                } else {
                    selection = selection + " AND " + DataBaseManager.COLUMN_ID + "=" + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
    }

}
