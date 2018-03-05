package com.votafore.earthporn.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
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

    private RecyclerView imageList;

    private App app;
    private RVAdapter.OnItemClickListener listener;

    //private View currentView;

    public FragmentList() {
        // Required empty public constructor
    }

    public void setListener(RVAdapter.OnItemClickListener newListener){
        listener = newListener;
    }

    public static FragmentList newInstance() {
        return new FragmentList();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: dodelat'
        // request for images when fragment is created at first time
        app.sendRequestForTopImages();

//        setExitTransition(new Fade());




//        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(R.transition.open_image));
//        setSharedElementReturnTransition(TransitionInflater.from(getContext()).inflateTransition(R.transition.open_image));

//        setExitSharedElementCallback(new SharedElementCallback() {
//            @Override
//            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
//
//                if (App.selectedIndex < 0) {
//                    // When transitioning out, use the view already specified in makeSceneTransition
//                    return;
//                }
//
//                // When transitioning back in, use the thumbnail at index the user had swiped to in the pager activity
//                //sharedElements.put(names.get(0), app.getAdapter().getViewAtIndex(app.selectedIndex));
//                sharedElements.put(names.get(0), app.getAdapter().getViewAtIndex(App.selectedIndex));
//                //App.selectedIndex = -1;
//            }
//        });


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        app = (App) ((ActivityMain)context).getApplication();
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
            public void onClick(View item, int position) {

                App.selectedIndex = position;
                //currentView = item;
                //currentView.setTransitionName(String.format("Open_img_%d", position));
                //currentView.setTransitionName("Open_img");

                goToFullImage();
            }

            @Override
            public void onLongClick(View item, int position) {
                App.selectedIndex = position;
                //currentView = item;
                goToGallery();
            }
        });

        startPostponedEnterTransition();

        return v;
    }



    public void goToFullImage(){

//        setExitSharedElementCallback(new SharedElementCallback() {
//
//            @Override
//            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
////                sharedElements.put(names.get(0), view);
//
////                RecyclerView.ViewHolder selectedViewHolder = imageList.findViewHolderForAdapterPosition(App.selectedIndex);
////
////                if (selectedViewHolder == null || selectedViewHolder.itemView == null) {
////                    return;
////                }
////
////                //View v = imageList.getChildAt(App.selectedIndex);
////                View v = selectedViewHolder.itemView.findViewById(R.id.img_full);
////
////                // Map the first shared element name to the child ImageView.
////                sharedElements.put(names.get(0), v);
//
////                ((Transition)getExitTransition()).excludeTarget(v, true);
//            }
//        });

        FragmentFullImage f = FragmentFullImage.newInstance();

        getFragmentManager().beginTransaction()
                .replace(R.id.pages, f, "Full")
//                .setReorderingAllowed(true)
//                .addSharedElement(currentView, ViewCompat.getTransitionName(currentView))
                .addToBackStack(f.toString())
                .commit();
    }

    public void goToGallery(){

        FragmentGallery f = FragmentGallery.newInstance();

        getFragmentManager().beginTransaction()
                .replace(R.id.pages, f, "Gallery")
//                .setReorderingAllowed(true)
//                .addSharedElement(currentView, ViewCompat.getTransitionName(currentView))
                .addToBackStack(f.toString())
                .commit();
    }
}
