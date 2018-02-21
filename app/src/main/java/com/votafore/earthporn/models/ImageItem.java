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

    public void setImageToImageView(Context context, final WeakReference<ImageView> reference){

        if (image != null){
            reference.get().setImageBitmap(image);
            return;
        }

        Log.d("NEW_DATA", "try download the image");

        Picasso.with(context)
                .load(item.getUrl())
                .resize(350, 150)
                .centerCrop()
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        Log.d("NEW_DATA", "onBitmapLoaded");
                        image = bitmap;
                        if (reference.get() != null){
                            reference.get().setImageBitmap(image);
//                            reference.get().requestLayout();
                        }
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
