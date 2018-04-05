package com.votafore.earthporn.fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.votafore.earthporn.R;
import com.votafore.earthporn.content.DataBase;
import com.votafore.earthporn.content.DataProvider;

public class FragmentDataBase extends Fragment {

    public static FragmentDataBase newInstance() {
        FragmentDataBase fragment = new FragmentDataBase();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View currentPage = inflater.inflate(R.layout.fragment_data_base, container, false);

        ListView dataList = currentPage.findViewById(R.id.database_list);


        Cursor cursor = getContext().getContentResolver().query(DataProvider.BASE_URI, null, null, null, null);
        getActivity().startManagingCursor(cursor);

        String from[] = {DataBase.COLUMN_ID, DataBase.COLUMN_URL };
        int to[] = { android.R.id.text1, android.R.id.text2 };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_2, cursor, from, to);

        dataList.setAdapter(adapter);

        return currentPage;
    }

}
