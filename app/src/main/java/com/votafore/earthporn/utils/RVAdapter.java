package com.votafore.earthporn.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.votafore.earthporn.R;
import com.votafore.earthporn.models.ImageItem;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author votarore
 * Created on 21.02.2018.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder>{

    private List<ImageItem> images = new ArrayList<>();
    private Context context;

    public RVAdapter(Context context){
        this.context = context;
    }

    public void setImages(List<ImageItem> list){
        images = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(parent.getContext(), R.layout.list_image_item, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.ref = new WeakReference<>(holder.img);
        images.get(position).setImageToImageView(context, holder.ref);
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
        holder.ref.clear();
        holder.img.setImageBitmap(null);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        WeakReference<ImageView> ref;

        public ViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);
        }
    }
}
