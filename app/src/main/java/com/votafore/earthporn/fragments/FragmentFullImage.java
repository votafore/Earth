package com.votafore.earthporn.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.votafore.earthporn.ActivityMain;
import com.votafore.earthporn.R;
import com.votafore.earthporn.models.ImageItem;
import com.votafore.earthporn.utils.DataSet;

/**
 * created 03.03.2018
 */
public class FragmentFullImage extends Fragment {

    private ActivityMain activityMain;

    public static FragmentFullImage newInstance() {
        return new FragmentFullImage();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        activityMain = (ActivityMain)context;

        activityMain.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activityMain.getSupportActionBar().hide();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activityMain.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        activityMain.getSupportActionBar().show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(R.transition.open_image));

        ImageItem item = DataSet.getInstance().getItem(ActivityMain.selectedIndex);

        View v = inflater.inflate(R.layout.fragment_full_image, container, false);

        ImageView img = v.findViewById(R.id.img_full);
        img.setTransitionName(item.item.getId());
        img.setImageBitmap(item.image);

        return v;
    }

}
