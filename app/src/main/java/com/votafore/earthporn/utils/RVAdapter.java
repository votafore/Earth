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
import java.util.List;

/**
 * @author votarore
 * Created on 21.02.2018.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder>{

    private static final int TYPE_FULL = 0;
    private static final int TYPE_HALF = 1;

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
    }

    @Override
    public int getItemViewType(int position) {

//        switch (position % 6){
//            case 0:
//                return TYPE_FULL;
//            case 1:
//            case 2:
//                return TYPE_HALF;
//            default:
//                return TYPE_NORMAL;
//        }


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
