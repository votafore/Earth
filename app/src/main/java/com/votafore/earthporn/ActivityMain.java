package com.votafore.earthporn;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.votafore.earthporn.fragments.FragmentFullImage;
import com.votafore.earthporn.fragments.FragmentList;
import com.votafore.earthporn.utils.RVAdapter;

import java.util.List;
import java.util.Map;

/**
 * @author votarore
 * Created on 21.02.2018.
 */

public class ActivityMain extends AppCompatActivity {

    private FragmentManager mFragmentManager;
    //private App app;

    private View currentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentManager = getSupportFragmentManager();

//        RVAdapter.OnItemClickListener adapterListener = new RVAdapter.OnItemClickListener() {
//            @Override
//            public void onClick(View item, int position) {
//
////                Toast.makeText(ActivityMain.this, "onClick", Toast.LENGTH_SHORT).show();
//
//                App.selectedIndex = position;
//                currentView = item;
//                currentView.setTransitionName("Open_img");
//
//                goToFullImage();
//
////                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(ActivityMain.this, item, "Open_img");
////
////                Intent openFullImg = new Intent(getContext(), ActivityFullImage.class);
////                openFullImg.putExtra("imgIndex", position);
////
////                startActivity(openFullImg, options.toBundle());
//            }
//
//            @Override
//            public void onLongClick(View item, int position) {
//
//                App.selectedIndex = position;
//                currentView = item;
//
////                Toast.makeText(ActivityMain.this, "onLongClick", Toast.LENGTH_SHORT).show();
//
////                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(ActivityMain.this, item, "Open_img");
////
////                Intent openFullImg = new Intent(getContext(), ActivityGallery.class);
////                openFullImg.putExtra("imgIndex", position);
////
////                startActivity(openFullImg, options.toBundle());
//            }
//        };

        Fragment currentFragment = mFragmentManager.findFragmentById(R.id.pages);

        if (currentFragment == null) {

            FragmentList pageList = FragmentList.newInstance();
            //pageList.setListener(adapterListener);

            mFragmentManager.beginTransaction()
                    .add(R.id.pages, pageList, "LIST")
                    .commit();

        } //else {

//            if (currentFragment instanceof FragmentList){
//                ((FragmentList)currentFragment).setListener(adapterListener);
//            }
//        }
    }



    public void goToFullImage(){

        FragmentFullImage f = FragmentFullImage.newInstance();
//        f.setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.open_image));
//        f.setEnterTransition(new Fade(Fade.IN));
//        mFragmentManager.findFragmentById(R.id.pages).setExitTransition(new Fade(Fade.OUT));
//        f.setSharedElementReturnTransition(TransitionInflater.from(this).inflateTransition(R.transition.open_image));

        mFragmentManager.findFragmentById(R.id.pages).setExitSharedElementCallback(new SharedElementCallback() {

            private View v;

            @Override
            public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                super.onSharedElementStart(sharedElementNames, sharedElements, sharedElementSnapshots);
            }

            @Override
            public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
            }

            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                sharedElements.put(names.get(0), currentView);
            }
        });

        mFragmentManager.beginTransaction()
                .replace(R.id.pages, f, "FULL")
                .setReorderingAllowed(true)
                .addSharedElement(currentView, ViewCompat.getTransitionName(currentView))
                .addToBackStack(f.toString())
                .commit();
    }
}
