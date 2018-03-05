package com.votafore.earthporn.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.votafore.earthporn.ActivityMain;
import com.votafore.earthporn.App;
import com.votafore.earthporn.R;

/**
 * @author Votafore
 * created 05.02.2018
 */

public class FragmentGallery extends Fragment {

    private App app;

    public FragmentGallery() {
        // Required empty public constructor
    }

    public static FragmentGallery newInstance() {
        FragmentGallery fragment = new FragmentGallery();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        app = (App) ((ActivityMain)context).getApplication();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_gallery, container, false);

        ViewPager pager = v.findViewById(R.id.gallery_pager);
        pager.setAdapter(app.getPagerAdapter());

        pager.setCurrentItem(App.selectedIndex);
        return v;
    }

}
