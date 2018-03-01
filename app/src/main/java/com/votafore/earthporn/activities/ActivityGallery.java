package com.votafore.earthporn.activities;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import com.votafore.earthporn.App;
import com.votafore.earthporn.R;
import com.votafore.earthporn.utils.PAdapter;

import java.util.List;
import java.util.Map;


public class ActivityGallery extends AppCompatActivity {

    public static int selectedIndex = -1;

    private ViewPager pager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityCompat.postponeEnterTransition(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        pager = findViewById(R.id.gallery_pager);
        pager.setAdapter(((App)getApplication()).getPagerAdapter());

        selectedIndex = getIntent().getIntExtra("imgIndex", 0);
        pager.setCurrentItem(selectedIndex);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectedIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ActivityCompat.setEnterSharedElementCallback(this, new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                sharedElements.put(getResources().getString(R.string.transition_name), ((PAdapter)pager.getAdapter()).getViewByID(selectedIndex));
            }
        });

        pager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){

            @Override
            public void onGlobalLayout() {
                pager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                ActivityCompat.startPostponedEnterTransition(ActivityGallery.this);
            }
        });
    }
}
