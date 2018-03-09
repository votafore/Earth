package com.votafore.earthporn.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewPager;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.votafore.earthporn.ActivityMain;
import com.votafore.earthporn.App;
import com.votafore.earthporn.R;

import java.util.List;
import java.util.Map;

/**
 * @author Votafore
 * created 05.02.2018
 */

public class FragmentGallery extends Fragment {

    private App app;

    public static FragmentGallery newInstance() {
        FragmentGallery fragment = new FragmentGallery();
        return fragment;
    }

    public FragmentGallery() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        app = (App) ((ActivityMain)context).getApplication();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        postponeEnterTransition();
        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(R.transition.open_image));

        View v = inflater.inflate(R.layout.fragment_gallery, container, false);

        ViewPager pager = v.findViewById(R.id.gallery_pager);
        pager.setAdapter(app.getPagerAdapter());

        pager.setCurrentItem(App.selectedIndex);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                App.selectedIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                Log.d("NEW_DATA", "gallery: onMapSharedElements");
                sharedElements.put(names.get(0), app.getPagerAdapter().getViewByIndex(App.selectedIndex));
            }
        });

        pager.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                pager.removeOnLayoutChangeListener(this);
                startPostponedEnterTransition();
            }
        });

        return v;
    }

}
