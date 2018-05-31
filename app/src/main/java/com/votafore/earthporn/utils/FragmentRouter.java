package com.votafore.earthporn.utils;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.votafore.earthporn.ActivityMain;
import com.votafore.earthporn.R;
import com.votafore.earthporn.fragments.FragmentBroadcast;
import com.votafore.earthporn.fragments.FragmentCustomText;
import com.votafore.earthporn.fragments.FragmentDataBase;
import com.votafore.earthporn.fragments.FragmentDialog;
import com.votafore.earthporn.fragments.FragmentFullImage;
import com.votafore.earthporn.fragments.FragmentGallery;
import com.votafore.earthporn.fragments.FragmentList;
import com.votafore.earthporn.fragments.FragmentRx;
import com.votafore.earthporn.fragments.FragmentService;

public class FragmentRouter {

    private FragmentManager fragmentManager;
    private int fragmentContainer;

    public FragmentRouter(FragmentManager manager){
        fragmentManager   = manager;
        fragmentContainer = R.id.pages;
    }

    public void openImageListFragment(){

        Fragment currentFragment = fragmentManager.findFragmentById(fragmentContainer);

        // it is at first
        if (currentFragment == null){
            fragmentManager.beginTransaction()
                    .add(fragmentContainer, FragmentList.newInstance())
                    .commit();

            return;
        }
    }

    public void goToFullImageFragment(RecyclerView list, int position){

        FragmentFullImage pageFullImage = FragmentFullImage.newInstance();

        View item = ((ImageListAdapter.ViewHolder)list.findViewHolderForAdapterPosition(position)).img;

        fragmentManager.beginTransaction()
                .replace(fragmentContainer, pageFullImage)
                .setReorderingAllowed(true)
                .addSharedElement(item, ViewCompat.getTransitionName(item))
                .addToBackStack(pageFullImage.toString())
                .commit();
    }

    public void goToImageListFragment(){

        fragmentManager.beginTransaction()
                .replace(fragmentContainer, FragmentList.newInstance())
                .commit();
    }

    public void goToGalleryFragment(){

        Fragment currentFragment = fragmentManager.findFragmentById(fragmentContainer);

        if (currentFragment instanceof FragmentList) {

            FragmentList fragmentList = (FragmentList)currentFragment;

            RecyclerView rv_view = fragmentList.getView().findViewById(R.id.image_list);
            GridLayoutManager layoutManager = (GridLayoutManager) rv_view.getLayoutManager();

            ActivityMain.selectedIndex = layoutManager.findFirstCompletelyVisibleItemPosition();

            View itemView = layoutManager.findViewByPosition(ActivityMain.selectedIndex).findViewById(R.id.img);

            fragmentManager.beginTransaction()
                    .replace(fragmentContainer, FragmentGallery.newInstance())
                    .setReorderingAllowed(true)
                    .addSharedElement(itemView, ViewCompat.getTransitionName(itemView))
                    .commit();

        } else {

            fragmentManager.beginTransaction()
                    .replace(fragmentContainer, FragmentGallery.newInstance())
                    .commit();
        }
    }

    public void goToDataBaseFragment(){

        fragmentManager.beginTransaction()
                .replace(fragmentContainer, FragmentDataBase.newInstance())
                .commit();
    }

    public void goToServiceFragment(){

        fragmentManager.beginTransaction()
                .replace(fragmentContainer, FragmentService.newInstance())
                .commit();
    }

    public void goToBroadcastFragment(){

        fragmentManager.beginTransaction()
                .replace(fragmentContainer, FragmentBroadcast.newInstance())
                .commit();
    }

    public void goToDialogFragment() {

        fragmentManager.beginTransaction()
                .replace(fragmentContainer, FragmentDialog.newInstance())
                .commit();
    }

    public void goToCustomTextFragment(){

        fragmentManager.beginTransaction()
                .replace(fragmentContainer, FragmentCustomText.newInstance())
                .commit();
    }

    public void goToRxFragment(){

        fragmentManager.beginTransaction()
                .replace(fragmentContainer, FragmentRx.newInstance())
                .commit();
    }


    public int getCurrentFragmentMenuID(){
        Fragment currentFragment = fragmentManager.findFragmentById(fragmentContainer);

        int result = 0;

        if (currentFragment instanceof FragmentList)
            result = R.id.item_main;

        if (currentFragment instanceof FragmentGallery)
            result = R.id.item_gallery;

        if (currentFragment instanceof FragmentDataBase)
            result = R.id.item_database;

        if (currentFragment instanceof FragmentService)
            result = R.id.item_service;

        if (currentFragment instanceof FragmentBroadcast)
            result = R.id.item_broadcast;

        if (currentFragment instanceof FragmentDialog)
            result = R.id.item_dialogs;

        if (currentFragment instanceof FragmentCustomText)
            result = R.id.item_customText;

        if (currentFragment instanceof FragmentRx)
            result = R.id.item_rx;

        return result;
    }
}
