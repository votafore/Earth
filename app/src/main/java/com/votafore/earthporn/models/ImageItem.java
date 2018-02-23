package com.votafore.earthporn.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
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

    public Bitmap image;
    public Data_ item;

    public void setImageToImageView(Context context, final WeakReference<ImageView> reference){
        Log.d("NEW_DATA", "setImageToImageView");

        reference.get().setImageBitmap(image);

        if (image != null){
            return;
        }

        // adjust width and height
        Image source = item.getPreview().getImages().get(0);

        int max_width  = 1300;
        int max_Height = 1200;

        int width = source.getSource().getWidth();
        int height= source.getSource().getHeight();

        float ratio = (float) width / height;

        if(width > height){

            width = Math.min(max_width, width);
            height = (int) (width / ratio);

        } else {

            height = Math.min(max_Height, height);
            width = (int) (height * ratio);
        }

        Log.d("NEW_DATA", "loadImage");

        Glide.with(context)
                .asBitmap()
                .load(item.getUrl())
                .thumbnail(Glide.with(context).asBitmap().load(item.getThumbnail()))
                .into(new SimpleTarget<Bitmap>(width, height) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                        image = resource;

                        if (reference.get() != null) {
                            reference.get().setImageBitmap(resource);
                        }
                    }
                });
    }
}
