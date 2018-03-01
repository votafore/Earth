package com.votafore.earthporn;

import android.app.Application;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.votafore.earthporn.helpers.ServiceEarthPorn;
import com.votafore.earthporn.models.Child;
import com.votafore.earthporn.utils.PAdapter;
import com.votafore.earthporn.utils.RVAdapter;
import com.votafore.earthporn.models.ListOfImages;
import com.votafore.earthporn.models.ImageItem;

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
    private PAdapter pagerAdapter;

    @Override
    public void onCreate() {
        super.onCreate();

        earthService = new ServiceEarthPorn();
        handler      = new Handler();
        adapter      = new RVAdapter(getApplicationContext());
        pagerAdapter = new PAdapter();
        listener     = new Callback<ListOfImages>() {
            @Override
            public void onResponse(Call<ListOfImages> call, Response<ListOfImages> response) {
                Log.d("NEW_DATA", "received: onResponse");

                final List<ImageItem> list = new ArrayList<>();

                for (Child child: response.body().getData().getChildren()){
                    ImageItem img = new ImageItem();
                    img.item = child.getData();
                    list.add(img);
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setImages(list);
                        pagerAdapter.setImages(list);
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

        earthService.getApi().getNewImages(30).enqueue(listener);
    }

    public void sendRequestForTopImages(){
        Log.d("NEW_DATA", "send query");

        earthService.getApi().getTopImages(30).enqueue(listener);
    }

    public RVAdapter getAdapter(){
        return adapter;
    }

    public PAdapter getPagerAdapter(){
        return pagerAdapter;
    }
}
