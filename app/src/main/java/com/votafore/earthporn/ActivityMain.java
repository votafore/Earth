package com.votafore.earthporn;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;


import com.votafore.earthporn.utils.RVAdapter;

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

                imgView.setImageBitmap(app.getAdapter().getImageItem(position).image);
                openFullImage(item);

                btn_getNew.setVisibility(View.INVISIBLE);
                btn_getTop.setVisibility(View.INVISIBLE);

                imageIndex = position;
            }
        });

        // https://stackoverflow.com/questions/1016896/get-screen-dimensions-in-pixels
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getRealSize(size);

        finalBounds = new Rect(0,0, size.x, size.y);

        if (savedInstanceState == null) {
            return;
        }

        if(savedInstanceState.getBoolean("isFullScreen")){
            imageIndex = savedInstanceState.getInt("imageIndex");
            imgView.setImageBitmap(app.getAdapter().getImageItem(imageIndex).image);
            imgView.setVisibility(View.VISIBLE);

            btn_getNew.setVisibility(View.INVISIBLE);
            btn_getTop.setVisibility(View.INVISIBLE);

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

    }

    @Override
    public void onBackPressed() {

        if (imgView.getVisibility() == View.VISIBLE){

            if (startBounds != null) {
                closeFullImage();
            } else {
                imgView.setVisibility(View.GONE);
            }

            btn_getNew.setVisibility(View.VISIBLE);
            btn_getTop.setVisibility(View.VISIBLE);

            return;
        }

        super.onBackPressed();
    }

    private int imageIndex = -1;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("imageIndex", imageIndex);
        outState.putBoolean("isFullScreen", imgView.getVisibility() == View.VISIBLE);
        super.onSaveInstanceState(outState);
    }



    /**************** animation ******************/

    private Rect finalBounds;
    private Rect startBounds;

    private void openFullImage(View thumbnail){

        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    |View.SYSTEM_UI_FLAG_FULLSCREEN
                    |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        ViewGroup root = findViewById(R.id.container);

        startBounds = new Rect();
        thumbnail.getGlobalVisibleRect(startBounds);
        startBounds.offset(0, -50);

        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) imgView.getLayoutParams();

        layoutParams.width = startBounds.right - startBounds.left;
        layoutParams.height= startBounds.bottom - startBounds.top;

        imgView.setX(startBounds.left);
        imgView.setY(startBounds.top);

        imgView.setVisibility(View.VISIBLE);

        imgView.setLayoutParams(layoutParams);

        TransitionManager.beginDelayedTransition(root, TransitionInflater.from(getApplicationContext()).inflateTransition(R.transition.open_image));

        // TODO: how to set position without extra animation

        layoutParams.width = finalBounds.right;
        layoutParams.height = finalBounds.bottom;// - 50;

        imgView.setLayoutParams(layoutParams);

        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(imgView, View.X, startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(imgView, View.Y, startBounds.top, finalBounds.top))
        ;
        set.setDuration(300);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.start();
    }

    private void closeFullImage(){

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        ViewGroup root = findViewById(R.id.container);

        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) imgView.getLayoutParams();

        layoutParams.width = startBounds.right - startBounds.left;
        layoutParams.height= startBounds.bottom - startBounds.top;

        TransitionManager.beginDelayedTransition(root, TransitionInflater.from(getApplicationContext()).inflateTransition(R.transition.open_image));

        imgView.setLayoutParams(layoutParams);

        // TODO: find out how change position without extra animation object

        AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator
                .ofFloat(imgView, View.X, finalBounds.left, startBounds.left))
                .with(ObjectAnimator.ofFloat(imgView, View.Y,finalBounds.top, startBounds.top))
        ;
        set.setDuration(300);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                imgView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                imgView.setVisibility(View.GONE);
            }
        });
        set.start();

        imageIndex = -1;
    }
}
