package com.votafore.earthporn.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.votafore.earthporn.ActivityMain;
import com.votafore.earthporn.App;
import com.votafore.earthporn.R;
import com.votafore.earthporn.models.ImageItem;

/**
 * @author Vorafore
 * created 03.03.2018
 */
public class FragmentFullImage extends Fragment {

    private ActivityMain activityMain;
    private App app;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

<<<<<<< HEAD
        View v = inflater.inflate(R.layout.fragment_full_image, container, false);

        ImageView img = v.findViewById(R.id.img_full);
        img.setImageBitmap(app.getAdapter().getImageItem(App.selectedIndex).image);
=======
        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(R.transition.open_image));

        View v = View.inflate(container.getContext(), R.layout.fragment_full_image, null);

        final ImageView img = v.findViewById(R.id.img_full);

        ImageItem item = app.getAdapter().getImageItem(App.selectedIndex);

        img.setTransitionName(item.item.getId());
        img.setImageBitmap(item.image);
>>>>>>> task4_fragments

        return v;
    }

}
