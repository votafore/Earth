package com.votafore.earthporn.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.votafore.earthporn.ActivityMain;
import com.votafore.earthporn.R;
import com.votafore.earthporn.utils.DataSet;
import com.votafore.earthporn.utils.ImageLoader;
import com.votafore.earthporn.utils.RVAdapter;

import java.util.List;
import java.util.Map;


public class FragmentList extends Fragment {

    private RecyclerView imageList;
    private ImageLoader imageLoader;
    private RVAdapter adapter;

    public static FragmentList newInstance() {
        return new FragmentList();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageLoader = new ImageLoader();

        if (savedInstanceState == null && DataSet.getInstance().getList().size() == 0){
            imageLoader.getTopImages();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        postponeEnterTransition();

        View v = View.inflate(container.getContext(), R.layout.fragment_list, null);

        v.findViewById(R.id.get_new_images).setOnClickListener(v2 -> imageLoader.getNewImages());
        v.findViewById(R.id.get_top_images).setOnClickListener(v1 -> imageLoader.getTopImages());

        adapter = new RVAdapter();

        getLifecycle().addObserver(adapter);

        imageList = v.findViewById(R.id.image_list);
        imageList.setItemAnimator(new DefaultItemAnimator());
        imageList.setAdapter(adapter);

        adapter.setListener(position -> {

            ActivityMain.selectedIndex = position;

            FragmentFullImage pageFullImage = FragmentFullImage.newInstance();

            View item = ((RVAdapter.ViewHolder)imageList.findViewHolderForAdapterPosition(position)).img;

            getFragmentManager().beginTransaction()
                    .replace(R.id.pages, pageFullImage)
                    .setReorderingAllowed(true)
                    .addSharedElement(item, ViewCompat.getTransitionName(item))
                    .addToBackStack(pageFullImage.toString())
                    .commit();
        });

        setExitSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {

                RVAdapter.ViewHolder selectedViewHolder = (RVAdapter.ViewHolder) imageList.findViewHolderForAdapterPosition(ActivityMain.selectedIndex);

                if (selectedViewHolder == null || selectedViewHolder.itemView == null) {
                    return;
                }

                sharedElements.put(names.get(0), selectedViewHolder.img);
            }
        });

        imageList.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                imageList.removeOnLayoutChangeListener(this);
                startPostponedEnterTransition();
            }
        });

        return v;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // scroll to position
        imageList.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

                imageList.removeOnLayoutChangeListener(this);

                final RecyclerView.LayoutManager layoutManager = imageList.getLayoutManager();
                View viewAtPosition = layoutManager.findViewByPosition(ActivityMain.selectedIndex);

                if (viewAtPosition == null || layoutManager.isViewPartiallyVisible(viewAtPosition, false, true)) {
                    imageList.post(() -> layoutManager.scrollToPosition(ActivityMain.selectedIndex));
                }
            }
        });
    }
}
