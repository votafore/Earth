package com.votafore.earthporn;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;


import com.votafore.earthporn.activities.ActivityFullImage;
import com.votafore.earthporn.activities.ActivityGallery;
import com.votafore.earthporn.utils.RVAdapter;

import java.util.List;
import java.util.Map;

/**
 * @author votarore
 * Created on 21.02.2018.
 */

public class ActivityMain extends AppCompatActivity {

    private RecyclerView imageList;

    ImageView imgView;
    Button btn_getTop;
    Button btn_getNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Transition fade = new Fade()
//                .setDuration(400)
//                .setInterpolator(new AccelerateInterpolator());
//
//        getWindow().setExitTransition(fade);
//        getWindow().setEnterTransition(fade);

        final App app = (App) getApplication();

        imageList = findViewById(R.id.image_list);
        imageList.setItemAnimator(new DefaultItemAnimator());
        imageList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        imageList.setAdapter(app.getAdapter());

        btn_getNew = findViewById(R.id.get_new_images);
        btn_getNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.sendRequestForNewImages();
            }
        });

        btn_getTop = findViewById(R.id.get_top_images);
        btn_getTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.sendRequestForTopImages();
            }
        });

        imgView = findViewById(R.id.full_image);

        app.getAdapter().setListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onClick(View item, int position) {

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(ActivityMain.this, item, "Open_img");

                Intent openFullImg = new Intent(ActivityMain.this, ActivityFullImage.class);
                openFullImg.putExtra("imgIndex", position);

                startActivity(openFullImg, options.toBundle());
                //startActivity(openFullImg);

                //imgView.setImageBitmap(app.getAdapter().getImageItem(position).image);
                //openFullImage(item);

//                btn_getNew.setVisibility(View.INVISIBLE);
//                btn_getTop.setVisibility(View.INVISIBLE);

//                imageIndex = position;
            }

            @Override
            public void onLongClick(View item, int position) {

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(ActivityMain.this, item, "Open_img");

                Intent openFullImg = new Intent(ActivityMain.this, ActivityGallery.class);
                openFullImg.putExtra("imgIndex", position);

                startActivity(openFullImg, options.toBundle());
            }
        });


        ActivityCompat.setExitSharedElementCallback(this, new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {

                if (ActivityGallery.selectedIndex < 0) {
                    // When transitioning out, use the view already specified in makeSceneTransition
                    return;
                }

                // When transitioning back in, use the thumbnail at index the user had swiped to in the pager activity
                sharedElements.put(names.get(0), ((RVAdapter)imageList.getAdapter()).getViewAtIndex(ActivityGallery.selectedIndex));
                ActivityGallery.selectedIndex = -1;
            }
        });

        // https://stackoverflow.com/questions/1016896/get-screen-dimensions-in-pixels
//        Point size = new Point();
//        getWindowManager().getDefaultDisplay().getRealSize(size);
//
//        finalBounds = new Rect(0,0, size.x, size.y);
//
//        if (savedInstanceState == null) {
//            return;
//        }
//
//        if(savedInstanceState.getBoolean("isFullScreen")){
//            imageIndex = savedInstanceState.getInt("imageIndex");
//            imgView.setImageBitmap(app.getAdapter().getImageItem(imageIndex).image);
//            imgView.setVisibility(View.VISIBLE);
//
//            btn_getNew.setVisibility(View.INVISIBLE);
//            btn_getTop.setVisibility(View.INVISIBLE);
//
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        }

    }

//    @Override
//    public void onBackPressed() {
//
//        if (imgView.getVisibility() == View.VISIBLE){
//
//            if (startBounds != null) {
//                closeFullImage();
//            } else {
//                imgView.setVisibility(View.GONE);
//            }
//
//            btn_getNew.setVisibility(View.VISIBLE);
//            btn_getTop.setVisibility(View.VISIBLE);
//
//            return;
//        }
//
//        super.onBackPressed();
//    }

//    private int imageIndex = -1;

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        outState.putInt("imageIndex", imageIndex);
//        outState.putBoolean("isFullScreen", imgView.getVisibility() == View.VISIBLE);
//        super.onSaveInstanceState(outState);
//    }



    /**************** animation ******************/

//    private Rect finalBounds;
//    private Rect startBounds;

    private void openFullImage(View thumbnail){

        //thumbnail.setTransitionName(getResources().getString(R.string.transition_name));

//        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, thumbnail, getResources().getString(R.string.transition_name));
//
//        Intent openFullImg = new Intent(this, ActivityFullImage.class);
//
//        startActivity(openFullImg, options.toBundle());

//        ViewGroup root = findViewById(R.id.container);
//
//        startBounds = new Rect();
//        thumbnail.getGlobalVisibleRect(startBounds);
//        startBounds.offset(0, -50);
//
//        imgView.setVisibility(View.VISIBLE);
//
//        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(startBounds.right - startBounds.left-16, startBounds.bottom - startBounds.top - 16);
//
//        layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
//        layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
////        layoutParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
////        layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
//
//        layoutParams.leftMargin = startBounds.left + 8;
//        layoutParams.topMargin = startBounds.top + 8;
//
//        imgView.setLayoutParams(layoutParams);
//
//
//        TransitionManager.beginDelayedTransition(root, TransitionInflater.from(getApplicationContext()).inflateTransition(R.transition.open_image));
//
//
//        ConstraintLayout.LayoutParams finalParams = new ConstraintLayout.LayoutParams(720, 1280);
//        imgView.setLayoutParams(finalParams);
//
//
//
////        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
////                |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
////                |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
////                |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
////                |View.SYSTEM_UI_FLAG_FULLSCREEN
////                |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

    }

    private void closeFullImage(){

//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//
//        ViewGroup root = findViewById(R.id.container);
//
//        Transition tr = TransitionInflater.from(getApplicationContext()).inflateTransition(R.transition.open_image);
//        tr.addListener(new Transition.TransitionListener(){
//
//            @Override
//            public void onTransitionStart(Transition transition) {
//
//            }
//
//            @Override
//            public void onTransitionEnd(Transition transition) {
//                imgView.setVisibility(View.GONE);
//                imageIndex = -1;
//            }
//
//            @Override
//            public void onTransitionCancel(Transition transition) {
//
//            }
//
//            @Override
//            public void onTransitionPause(Transition transition) {
//
//            }
//
//            @Override
//            public void onTransitionResume(Transition transition) {
//
//            }
//        });
//
//        TransitionManager.beginDelayedTransition(root, tr);
//
//        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(startBounds.right - startBounds.left, startBounds.bottom - startBounds.top);
//
//        layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
//        layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
//
//        layoutParams.leftMargin = startBounds.left;
//        layoutParams.topMargin = startBounds.top;
//
//        imgView.setLayoutParams(layoutParams);
    }

}
