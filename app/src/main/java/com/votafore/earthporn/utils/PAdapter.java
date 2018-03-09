package com.votafore.earthporn.utils;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;

import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
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
 * @author Vorafore
 * Created on 01.03.2018.
 */

public class PAdapter extends PagerAdapter {

    private List<ImageItem> images = new ArrayList<>();

    private Map<Integer, WeakReference<ImageView>> map = new ArrayMap();

    public void setImages(List<ImageItem> newList){
        images = newList;
        notifyDataSetChanged();
    }



    /************** common **************/

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        Log.d("NEW_DATA", String.format("PAdapter: instantiateItem... position:%d", position));

        View v = LayoutInflater.from(container.getContext()).inflate(R.layout.pager_item, container, false);

        ImageView img = v.findViewById(R.id.img_full);
        img.setTransitionName(images.get(position).item.getId());
        images.get(position).setImageToImageView(container.getContext(), new WeakReference<>(img));

        container.addView(v);

        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
        map.remove(position);
    }

    public View getViewByIndex(int index){
        if (map.get(index) == null){
            return null;
        }
        return map.get(index).get();
    }
}
