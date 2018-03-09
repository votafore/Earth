package com.votafore.earthporn.utils;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.votafore.earthporn.R;
import com.votafore.earthporn.models.ImageItem;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author votarore
 * Created on 21.02.2018.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder>{

    private List<ImageItem> images = new ArrayList<>();
    private Map<Integer, WeakReference<ImageView>> map = new ArrayMap<>();
    private Context context;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onClick(int position);
        void onLongClick(int position);
    }

    public RVAdapter(Context context){
        this.context = context;
        Log.d("NEW_DATA", "RVAdapter");
    }

    public void setImages(List<ImageItem> list){
        images = list;
        notifyDataSetChanged();
    }





    public View getViewAtIndex(int index){
        if(map.get(index) == null){
            return null;
        }
        return map.get(index).get();
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ImageItem getImageItem(int index){
        return images.get(index);
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View itemView = View.inflate(parent.getContext(), R.layout.list_image_item, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.img.setTransitionName(images.get(position).item.getId());
        holder.ref = new WeakReference<>(holder.img);
        images.get(position).setImageToImageView(context, holder.ref);

        map.put(position, holder.ref);

        Log.d("NEW_DATA", "onBindViewHolder");
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        holder.ref.clear();

        holder.img.setImageBitmap(null);
        holder.img.setTransitionName(null);

        for (int key: map.keySet()) {
            if (map.get(key) == holder.ref) {
                map.remove(key);
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        public ImageView img;
        public WeakReference<ImageView> ref;

        public ViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (listener == null){
                return;
            }

            listener.onClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {

            if (listener == null){
                return false;
            }

            listener.onLongClick(getAdapterPosition());

            return true;
        }
    }
}
