package com.votafore.earthporn.utils;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
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
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class PAdapter extends PagerAdapter implements Observer, LifecycleObserver{

    private List<ImageItem> images;

    private Map<Integer, WeakReference<ImageView>> map = new ArrayMap();

    public PAdapter(){
        images = DataSet.getInstance().getList();
    }


    @Override
    public void update(Observable o, Object arg) {
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

        Log.d("TESTS", String.format("PAdapter: instantiateItem... position:%d", position));

        View v = LayoutInflater.from(container.getContext()).inflate(R.layout.pager_item, container, false);

        ImageView img = v.findViewById(R.id.img_full);
        img.setTransitionName(images.get(position).item.getId());

        WeakReference<ImageView> reference = new WeakReference<>(img);

        images.get(position).setImageToImageView(container.getContext(), reference);

        container.addView(v);

        map.put(position, reference);

        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
        map.get(position).clear();
        map.remove(position);
    }

    public View getViewByIndex(int index){
        if (map.get(index) == null){
            return null;
        }
        return map.get(index).get();
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
