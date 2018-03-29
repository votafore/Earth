package com.votafore.earthporn.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.votafore.earthporn.ActivityMain;
import com.votafore.earthporn.R;
import com.votafore.earthporn.customviews.AutofitRecyclerView;
import com.votafore.earthporn.utils.DataSet;
import com.votafore.earthporn.utils.FragmentRouter;
import com.votafore.earthporn.utils.ImageLoader;
import com.votafore.earthporn.utils.ImageListAdapter;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FragmentList extends Fragment {

    @BindView(R.id.image_list) AutofitRecyclerView imageList;

    private ImageLoader imageLoader;
    private FragmentRouter router;

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

        setExitTransition(new Explode().setDuration(getResources().getInteger(R.integer.anim_std_duration)));

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        router = ((ActivityMain)getActivity()).getRouter();

        postponeEnterTransition();

        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        ButterKnife.bind(this, rootView);

        ImageListAdapter adapter = new ImageListAdapter();
        getLifecycle().addObserver(adapter);

        imageList.setAdapter(adapter);
        imageList.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private LinearLayoutManager manager = (LinearLayoutManager) imageList.getLayoutManager();
            private boolean onBottomNow = false;

            View container = rootView.findViewById(R.id.buttons_container);

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                int lastVisible = manager.findLastCompletelyVisibleItemPosition();
                int size = manager.getItemCount() - 1;

                onBottomNow = (lastVisible == size);

                if (onBottomNow) {
                    container.setVisibility(View.INVISIBLE);
                } else {
                    container.setVisibility(View.VISIBLE);
                }
            }
        });

        adapter.setListener(position -> {
            ActivityMain.selectedIndex = position;
            router.goToFullImageFragment(imageList, position);
        });

        setExitSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {

                ImageListAdapter.ViewHolder selectedViewHolder = (ImageListAdapter.ViewHolder) imageList.findViewHolderForAdapterPosition(ActivityMain.selectedIndex);

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

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.appbar_menu, menu);

        MenuItem item = menu.findItem(R.id.change_grid);

        if (imageList.getMode() == AutofitRecyclerView.MODE_LIST){
            item.setIcon(R.drawable.mode_grid);
        } else {
            item.setIcon(R.drawable.mode_list);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.change_grid:

                int newMode = imageList.getMode();
                newMode = newMode == AutofitRecyclerView.MODE_LIST ? AutofitRecyclerView.MODE_GRID : AutofitRecyclerView.MODE_LIST;

                imageList.changeMode(newMode);

                if (newMode == AutofitRecyclerView.MODE_LIST){
                    item.setIcon(R.drawable.mode_grid);
                } else {
                    item.setIcon(R.drawable.mode_list);
                }

                return true;
        }

        return false;
    }

    @OnClick(R.id.get_new_images)
    public void getNewImages(View view){
        imageLoader.getNewImages();
    }

    @OnClick(R.id.get_top_images)
    public void getTopImages(View view){
        imageLoader.getTopImages();
    }

}
