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

public class ImageItem {

    public Bitmap image;
    public Data_ item;

    private int MAX_SIZE = 800;

    public void setImageToImageView(Context context, final WeakReference<ImageView> reference){

        if (image != null){
            if (!image.isRecycled()){
                reference.get().setImageBitmap(image);
                return;
            }
        }

        if (item.getPreview() == null){
            return;
        }

        // get width and height of image
        Image source = item.getPreview().getImages().get(0);

        int width  = source.getSource().getWidth();
        int height = source.getSource().getHeight();

        // adjusting width and height in order to reduce size
        float ratio = (float) width / height;

        if(width > height){

            width = Math.min(MAX_SIZE, width);
            height = (int) (width / ratio);

        } else {

            height = Math.min(MAX_SIZE, height);
            width = (int) (height * ratio);
        }

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
