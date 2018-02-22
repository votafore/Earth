package com.votafore.earthporn.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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

    public Drawable image;
    public Data_ item;

    public void setImageToImageView(Context context, final WeakReference<ImageView> reference){
        Log.d("NEW_DATA", "setImageToImageView");

        reference.get().setImageDrawable(image);

        if (image != null){
            return;
        }

        Log.d("NEW_DATA", "loadImage");

        Glide.with(context)
                .load(item.getUrl())
                .thumbnail(Glide.with(context).load(item.getThumbnail()))
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                        image = resource;

                        if (reference.get() != null) {
                            reference.get().setImageDrawable(resource);
                        }
                    }
                });
    }
}
