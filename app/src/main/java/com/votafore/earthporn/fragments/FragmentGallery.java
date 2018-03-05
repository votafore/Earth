package com.votafore.earthporn.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
//        Bundle args = new Bundle();
//        args.putInt(paramIndex, index);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        app = (App) ((ActivityMain)context).getApplication();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_gallery, container, false);
        //View v = View.inflate(container.getContext(), R.layout.fragment_gallery, null);

        ViewPager pager = v.findViewById(R.id.gallery_pager);
        pager.setAdapter(app.getPagerAdapter());

        //int selectedIndex = getIntent().getIntExtra("imgIndex", 0);
        pager.setCurrentItem(App.selectedIndex);
//        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                App.selectedIndex = position;
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

        return v;
    }

}
