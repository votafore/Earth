package com.votafore.earthporn.activities;

import android.graphics.Bitmap;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Transition;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;
import com.votafore.earthporn.App;
import com.votafore.earthporn.R;

import java.util.List;
import java.util.Map;

public class ActivityFullImage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ActivityCompat.postponeEnterTransition(this);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        final ImageView img = findViewById(R.id.img_full);

        ActivityCompat.setEnterSharedElementCallback(this, new SharedElementCallback() {

            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                sharedElements.put(getResources().getString(R.string.transition_name), img);
            }
        });

        Bitmap img_bmp = ((App)getApplication()).getAdapter().getImageItem(getIntent().getIntExtra("imgIndex", -1)).image;

        img.setImageBitmap(img_bmp);
        img.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){

            @Override
            public void onGlobalLayout() {
                img.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                ActivityCompat.startPostponedEnterTransition(ActivityFullImage.this);
            }
        });
    }
}
