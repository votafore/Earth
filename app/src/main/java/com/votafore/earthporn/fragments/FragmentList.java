package com.votafore.earthporn.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.votafore.earthporn.ActivityMain;
import com.votafore.earthporn.App;
import com.votafore.earthporn.R;
import com.votafore.earthporn.utils.RVAdapter;

/**
 * @author Vorafore
 * created 03.03.2018
 */
public class FragmentList extends Fragment {

    private App app;

    public FragmentList() {
        // Required empty public constructor
    }

    public static FragmentList newInstance() {
        return new FragmentList();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app.sendRequestForTopImages();
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

        RecyclerView imageList = v.findViewById(R.id.image_list);
        imageList.setItemAnimator(new DefaultItemAnimator());
        imageList.setAdapter(app.getAdapter());

        app.getAdapter().setListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onClick(View item, int position) {

                App.selectedIndex = position;
                goToFullImage();
            }

            @Override
            public void onLongClick(View item, int position) {
                App.selectedIndex = position;
                goToGallery();
            }
        });

        startPostponedEnterTransition();

        return v;
    }

    public void goToFullImage(){

        FragmentFullImage f = FragmentFullImage.newInstance();

        getFragmentManager().beginTransaction()
                .replace(R.id.pages, f, "Full")
                .addToBackStack(f.toString())
                .commit();
    }

    public void goToGallery(){

        FragmentGallery f = FragmentGallery.newInstance();

        getFragmentManager().beginTransaction()
                .replace(R.id.pages, f, "Gallery")
                .addToBackStack(f.toString())
                .commit();
    }
}
