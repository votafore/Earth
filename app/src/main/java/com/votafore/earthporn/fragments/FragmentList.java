package com.votafore.earthporn.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.votafore.earthporn.ActivityMain;
import com.votafore.earthporn.App;
import com.votafore.earthporn.R;
import com.votafore.earthporn.utils.RVAdapter;

import java.util.List;
import java.util.Map;

/**
 * @author Vorafore
 * created 03.03.2018
 */
public class FragmentList extends Fragment {

    private App app;
    private RecyclerView imageList;

    public static FragmentList newInstance() {
        return new FragmentList();
    }

    public FragmentList() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        app = (App) ((ActivityMain)context).getApplication();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app.sendRequestForTopImages();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        postponeEnterTransition();

        View v = View.inflate(container.getContext(), R.layout.fragment_list, null);

        Button btn_getNew = v.findViewById(R.id.get_new_images);
        btn_getNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.sendRequestForNewImages();
            }
        });

        Button btn_getTop = v.findViewById(R.id.get_top_images);
        btn_getTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.sendRequestForTopImages();
            }
        });

        imageList = v.findViewById(R.id.image_list);
        imageList.setItemAnimator(new DefaultItemAnimator());
        imageList.setAdapter(app.getAdapter());

        app.getAdapter().setListener(new RVAdapter.OnItemClickListener() {

            @Override
            public void onClick(int position) {
                App.selectedIndex = position;

                FragmentFullImage pageFullImage = FragmentFullImage.newInstance();

                View item = ((RVAdapter.ViewHolder)imageList.findViewHolderForAdapterPosition(position)).img;

                getFragmentManager().beginTransaction()
                        .replace(R.id.pages, pageFullImage)
                        .setReorderingAllowed(true)
                        .addSharedElement(item, ViewCompat.getTransitionName(item))
                        .addToBackStack(pageFullImage.toString())
                        .commit();
            }

            @Override
            public void onLongClick(int position) {
                App.selectedIndex = position;

            }
        });

        setExitSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                Log.d("NEW_DATA", "list: onMapSharedElements");

                RVAdapter.ViewHolder selectedViewHolder = (RVAdapter.ViewHolder) imageList.findViewHolderForAdapterPosition(App.selectedIndex);

                if (selectedViewHolder == null || selectedViewHolder.itemView == null) {
                    return;
                }

                sharedElements.put(names.get(0), selectedViewHolder.img);
                //sharedElements.put(names.get(0), app.getAdapter().getViewAtIndex(App.selectedIndex));
            }
        });

//        imageList.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                imageList.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                startPostponedEnterTransition();
//            }
//        });

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
                View viewAtPosition = layoutManager.findViewByPosition(App.selectedIndex);

                if (viewAtPosition == null || layoutManager.isViewPartiallyVisible(viewAtPosition, false, true)) {
                    Log.d("NEW_DATA", "list: scrollToPosition");
                    imageList.post(() -> layoutManager.scrollToPosition(App.selectedIndex));
                }
            }
        });
    }

    //    public void goToFullImage(){
//
//        FragmentFullImage f = FragmentFullImage.newInstance();
//
//        View item = ((RVAdapter)imageList.getAdapter()).getViewAtIndex(App.selectedIndex);
//
//        getFragmentManager().beginTransaction()
//                .replace(R.id.pages, f)
//                .setReorderingAllowed(true)
//                .addSharedElement(item, ViewCompat.getTransitionName(item))
//                .addToBackStack(f.toString())
//                .commit();
//    }

//    public void goToGallery(){
//
//        FragmentGallery f = FragmentGallery.newInstance();
//
//        View item = ((RVAdapter)imageList.getAdapter()).getViewAtIndex(App.selectedIndex);
//
//        getFragmentManager().beginTransaction()
//                .replace(R.id.pages, f)
//                .setReorderingAllowed(true)
//                .addSharedElement(item, ViewCompat.getTransitionName(item))
//                .addToBackStack(f.toString())
//                .commit();
//    }
}
