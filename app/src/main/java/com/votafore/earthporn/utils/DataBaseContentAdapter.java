package com.votafore.earthporn.utils;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.votafore.earthporn.R;
import com.votafore.earthporn.content.DataBaseManager;
import com.votafore.earthporn.content.DataProvider;


public class DataBaseContentAdapter extends BaseAdapter {

    Cursor data;

    public DataBaseContentAdapter(Context context){
        data = context.getContentResolver().query(DataProvider.BASE_URI, null, null, null, null);
    }

    @Override
    public int getCount() {
        return data.getCount();
    }

    @Override
    public Object getItem(int position) {
        data.moveToPosition(position);
        return data;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View item = convertView;

        if(item == null)
          item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_database, parent, false);

        if (data.moveToPosition(position)){

            TextView textViewID = item.findViewById(R.id.item_id);
            TextView textViewURL = item.findViewById(R.id.item_url);

            textViewID.setText(data.getString(data.getColumnIndex(DataBaseManager.COLUMN_ID)));
            textViewURL.setText(data.getString(data.getColumnIndex(DataBaseManager.COLUMN_URL)));
        }

        return item;
    }
}
