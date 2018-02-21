package com.votafore.earthporn;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.votafore.earthporn.helpers.ServiceEarthPorn;
import com.votafore.earthporn.models.Child;
import com.votafore.earthporn.utils.RVAdapter;
import com.votafore.earthporn.models.ListOfImages;
import com.votafore.earthporn.models.ImageItem;
import com.votafore.earthporn.views.IImageListActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author votarore
 * Created on 21.02.2018.
 */

public class App extends Application {

    private ServiceEarthPorn earthService;
    private Handler handler;
    private RVAdapter adapter;
    private Callback<ListOfImages> listener;

    @Override
    public void onCreate() {
        super.onCreate();

        earthService = new ServiceEarthPorn();
        handler      = new Handler();
        adapter      = new RVAdapter(getApplicationContext());

//        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
//            @Override
//            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
//                ((IImageListActivity) activity).bindImageListToAdapter(adapter);
//            }
//
//            @Override
//            public void onActivityStarted(Activity activity) {
//
//            }
//
//            @Override
//            public void onActivityResumed(Activity activity) {
//
//            }
//
//            @Override
//            public void onActivityPaused(Activity activity) {
//
//            }
//
//            @Override
//            public void onActivityStopped(Activity activity) {
//
//            }
//
//            @Override
//            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
//
//            }
//
//            @Override
//            public void onActivityDestroyed(Activity activity) {
//
//            }
//        });

        listener = new Callback<ListOfImages>() {
            @Override
            public void onResponse(Call<ListOfImages> call, Response<ListOfImages> response) {
                Log.d("NEW_DATA", "received: onResponse");
                final List<ImageItem> list = populateList(response.body());

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setImages(list);
                    }
                });
            }

            @Override
            public void onFailure(Call<ListOfImages> call, Throwable t) {
                Log.d("NEW_DATA", "received: onFailure");
                Toast.makeText(getApplicationContext(), "failure", Toast.LENGTH_SHORT).show();
            }
        };
    }

    public void sendRequestForNewImages(){
        Log.d("NEW_DATA", "send query");

        earthService.getApi().getNewImages(100).enqueue(listener);
    }

    public void sendRequestForTopImages(){
        Log.d("NEW_DATA", "send query");

        earthService.getApi().getTopImages(100).enqueue(listener);
    }

    private List<ImageItem> populateList(ListOfImages list){

        List<ImageItem> images = new ArrayList<>();
        for (Child child: list.getData().getChildren()){

            ImageItem img = new ImageItem();
            img.item = child.getData();
            images.add(img);
        }
        return images;
    }

    public RVAdapter getAdapter(){
        return adapter;
    }
}
