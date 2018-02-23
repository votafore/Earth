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
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
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
                //imgView.showImage(Uri.parse(app.getAdapter().getImageItem(position).item.getUrl()));
                //imgView.getSSIV().setImage(ImageSource.bitmap(app.getAdapter().getImageItem(position).image));
                imgView.setImageBitmap(app.getAdapter().getImageItem(position).image);
                //imgView.setVisibility(View.VISIBLE);

                //startTransition(item, imgView);
                openFullImage(item);

                btn_getNew.setVisibility(View.INVISIBLE);
                btn_getTop.setVisibility(View.INVISIBLE);
            }
        });

        Display dsp = getWindowManager().getDefaultDisplay();
        finalBounds = new Rect(0,0, dsp.getWidth(), dsp.getHeight());

        imgView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {

        if (imgView.getVisibility() == View.VISIBLE){
            //imgView.setVisibility(View.GONE);

            closeFullImage();

            btn_getNew.setVisibility(View.VISIBLE);
            btn_getTop.setVisibility(View.VISIBLE);

            return;
        }

        super.onBackPressed();
    }





    private AnimatorSet mCurrentAnimator;
    private long mShortAnimationDuration = 200;


    Rect finalBounds;
    Rect startBounds;

    float scaleX;
    float scaleY;

    private void openFullImage(View thumbnail){

        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        startBounds = new Rect();
        thumbnail.getGlobalVisibleRect(startBounds);
        startBounds.offset(0, -50);

        scaleX = (float) startBounds.width() / finalBounds.width();
        scaleY = (float) startBounds.height() / finalBounds.height();

        imgView.setVisibility(View.VISIBLE);

        imgView.setPivotX(0f);
        imgView.setPivotY(0f);

        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(imgView, View.X, startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(imgView, View.Y, startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(imgView, View.SCALE_X, scaleX, 1f))
                .with(ObjectAnimator.ofFloat(imgView, View.SCALE_Y, scaleY, 1f))
        //.with(ObjectAnimator.ofFloat(expandedImageView, View.ALPHA, 0f, 1f))
        ;
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        imageList.setClickable(false);
    }

    private void closeFullImage(){

        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator
                .ofFloat(imgView, View.X, finalBounds.left, startBounds.left))
                .with(ObjectAnimator.ofFloat(imgView, View.Y,finalBounds.top, startBounds.top))
                .with(ObjectAnimator.ofFloat(imgView, View.SCALE_X, 1, scaleX))
                .with(ObjectAnimator.ofFloat(imgView, View.SCALE_Y, 1, scaleY))
        //.with(ObjectAnimator.ofFloat(imgView, View.ALPHA, 1, 0.6f, 0f))
        ;
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                imgView.setVisibility(View.GONE);
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                imgView.setVisibility(View.GONE);
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        imageList.setClickable(true);
    }
}
