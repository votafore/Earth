package com.votafore.earthporn.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.votafore.earthporn.R;
import com.votafore.earthporn.utils.DataBaseContentAdapter;

public class FragmentDataBase extends Fragment {

    public static FragmentDataBase newInstance() {
        FragmentDataBase fragment = new FragmentDataBase();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View currentPage = inflater.inflate(R.layout.fragment_data_base, container, false);

        ListView dataList = currentPage.findViewById(R.id.database_list);
        dataList.setAdapter(new DataBaseContentAdapter(getContext()));

        return currentPage;
    }

}
