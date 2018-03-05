package com.votafore.earthporn.utils;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.votafore.earthporn.R;
import com.votafore.earthporn.models.ImageItem;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author votarore
 * Created on 21.02.2018.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder>{

    private static final int TYPE_FULL = 0;
    private static final int TYPE_HALF = 1;

    private List<ImageItem> images = new ArrayList<>();
    private Map<Integer, WeakReference<ImageView>> map = new HashMap();
    private Context context;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onClick(View item, int position);
        void onLongClick(View item, int position);
    }

    public RVAdapter(Context context){
        this.context = context;
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

        final View itemView = View.inflate(parent.getContext(), R.layout.list_image_item, null);

        itemView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {

                final int type = viewType;
                final ViewGroup.LayoutParams lp = itemView.getLayoutParams();
                if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                    StaggeredGridLayoutManager.LayoutParams sglp = (StaggeredGridLayoutManager.LayoutParams) lp;
                    switch (type) {
                        case TYPE_FULL:
                            sglp.setFullSpan(true);
                            break;
                        case TYPE_HALF:
                            sglp.setFullSpan(false);
                            break;
                    }
                    itemView.setLayoutParams(sglp);
                    final StaggeredGridLayoutManager lm = (StaggeredGridLayoutManager) ((RecyclerView) parent).getLayoutManager();
                    lm.invalidateSpanAssignments();
                }
                itemView.getViewTreeObserver().removeOnPreDrawListener(this);

                return true;
            }
        });

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.ref = new WeakReference<>(holder.img);
        images.get(position).setImageToImageView(context, holder.ref);
        map.put(position, holder.ref);
    }

    @Override
    public int getItemViewType(int position) {

        if (position % 3 == 0){
            return TYPE_FULL;
        } else {
            return TYPE_HALF;
        }
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
        holder.ref.clear();
        holder.img.setImageBitmap(null);

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

            listener.onClick(itemView, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {

            if (listener == null){
                return false;
            }

            listener.onLongClick(itemView, getAdapterPosition());

            return true;
        }
    }
}
