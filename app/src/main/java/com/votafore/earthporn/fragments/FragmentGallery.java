package com.votafore.earthporn.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewPager;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.votafore.earthporn.ActivityMain;
import com.votafore.earthporn.R;
import com.votafore.earthporn.utils.GalleryAdapter;

import java.util.List;
import java.util.Map;


public class FragmentGallery extends Fragment {

    public static FragmentGallery newInstance() {
        return new FragmentGallery();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        postponeEnterTransition();
        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(R.transition.open_image));

        View v = inflater.inflate(R.layout.fragment_gallery, container, false);

        final GalleryAdapter adapter = new GalleryAdapter();

        getLifecycle().addObserver(adapter);

        final ViewPager pager = v.findViewById(R.id.gallery_pager);
        pager.setAdapter(adapter);
        pager.setCurrentItem(ActivityMain.selectedIndex);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ActivityMain.selectedIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //https://stackoverflow.com/questions/13914609/viewpager-with-previous-and-next-page-boundaries
        pager.setPageMargin(10);

        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                sharedElements.put(names.get(0), adapter.getViewByIndex(ActivityMain.selectedIndex));
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
