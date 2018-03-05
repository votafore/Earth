package com.votafore.earthporn.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.votafore.earthporn.ActivityMain;
import com.votafore.earthporn.App;
import com.votafore.earthporn.R;

import java.util.List;
import java.util.Map;

/**
 * @author Vorafore
 * created 03.03.2018
 */
public class FragmentFullImage extends Fragment {

    private ActivityMain activityMain;
    private App app;
    //private ImageView img;

    public FragmentFullImage() {
        // Required empty public constructor
    }



    public static FragmentFullImage newInstance() {
        return new FragmentFullImage();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        activityMain = (ActivityMain)context;

        app = (App) activityMain.getApplication();

        activityMain.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activityMain.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setEnterTransition(new Fade());

        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(R.transition.open_image));
        //setSharedElementReturnTransition(TransitionInflater.from(getContext()).inflateTransition(R.transition.open_image));

//        setExitSharedElementCallback(new SharedElementCallback() {
//            @Override
//            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
//                super.onMapSharedElements(names, sharedElements);
//            }
//        });
//
//        setExitSharedElementCallback(new SharedElementCallback() {
//            @Override
//            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
//                sharedElements.put(names.get(0), img);
//            }
//        });





//        setSharedElementReturnTransition(TransitionInflater.from(getContext()).inflateTransition(R.transition.open_image));
//
        setExitTransition(new Fade());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //postponeEnterTransition();

        View v = View.inflate(container.getContext(), R.layout.fragment_full_image, null);

        ImageView img = v.findViewById(R.id.img_full);
        img.setImageBitmap(app.getAdapter().getImageItem(App.selectedIndex).image);

//        img.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                startPostponedEnterTransition();
//            }
//        });

//        setEnterSharedElementCallback(new SharedElementCallback() {
//            @Override
//            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
//
//                // Map the first shared element name to the child ImageView.
//                sharedElements.put(names.get(0), img);
//            }
//        });

        return v;
    }

}
