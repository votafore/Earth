package com.votafore.earthporn.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

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

        if (image != null){
            reference.get().setImageBitmap(image);
        }

//        if (isLoading){
//            return;
//        }
//
//        Log.d("NEW_DATA", "try download the image");
//
//        int width = 350;
//        int height = 150;

//        if (reference.get() != null){
//            width = reference.get().getWidth();
//            height= reference.get().getHeight();
//        }

//        Log.d("NEW_DATA", String.format("width: %d.... height: %d", width, height));
//
//        Picasso.with(context)
//                .load(item.getUrl())
//                .resize(width, height)
//                .centerCrop()
//                .into(new Target() {
//                    @Override
//                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                        Log.d("NEW_DATA", "onBitmapLoaded");
//                        image = bitmap;
//                        if (reference.get() != null){
//                            reference.get().setImageBitmap(image);
////                            reference.get().requestLayout();
//                        }
//                        isLoading = false;
//                    }
//
//                    @Override
//                    public void onBitmapFailed(Drawable errorDrawable) {
//
//                    }
//
//                    @Override
//                    public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//                    }
//                });
//
//        isLoading = image == null;
    }

    public void loadImage(Context context){

        int width = 350;
        int height = 150;

        Log.d("NEW_DATA", "loadImage");

        Picasso.with(context)
                .load(item.getUrl())
                .resize(width, height)
                .centerCrop()
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        Log.d("NEW_DATA", "onBitmapLoaded");
                        image = bitmap;
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
    }
}
