package com.votafore.earthporn.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.lang.ref.WeakReference;

/**
 * @author votarore
 * Created on 21.02.2018.
 */

public class ImageItem {

    public Bitmap image;
    public Data_ item;

    private boolean isLoading = false;

    public void setImageToImageView(Context context, final WeakReference<ImageView> reference){
        Log.d("NEW_DATA", "setImageToImageView");

        reference.get().setImageBitmap(image);
    }

    public void loadImage(Context context){

        int width = 350;
        int height = 150;

        Log.d("NEW_DATA", "loadImage");

        Glide.with(context)
                .asBitmap()
                .load(item.getThumbnail())
                .into(new SimpleTarget<Bitmap>(width, height) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Log.d("NEW_DATA", "onResourceReady");
                        image = resource;
                    }
                });
    }
}
