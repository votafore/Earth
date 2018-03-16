package com.votafore.earthporn.utils;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.votafore.earthporn.R;
import com.votafore.earthporn.models.ImageItem;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * @author votarore
 * Created on 21.02.2018.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> implements Observer, LifecycleObserver{

    private List<ImageItem> images = new ArrayList<>();

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public RVAdapter(){
        images = DataSet.getInstance().getList();
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_image_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.img.setTransitionName(images.get(position).item.getId());
        holder.ref = new WeakReference<>(holder.img);
        images.get(position).setImageToImageView(holder.itemView.getContext(), holder.ref);
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        holder.ref.clear();
        holder.img.setImageBitmap(null);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView img;
        public WeakReference<ImageView> ref;

        public ViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (listener == null){
                return;
            }

            listener.onClick(getAdapterPosition());
        }
    }

    /****************** observer ****************/

    @Override
    public void update(Observable o, Object arg) {
        notifyDataSetChanged();
    }

    /****************** life cycle observer ****************/

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void startObserve(){
        DataSet.registerObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void stopObserve(){
        DataSet.removeObserver(this);
    }
}
