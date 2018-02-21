package com.votafore.earthporn;

import android.app.Application;
import android.util.Log;

import com.votafore.earthporn.helpers.ServiceEarthPorn;
import com.votafore.earthporn.models.Child;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author votarore
 * Created on 21.02.2018.
 */

public class App extends Application {

    private ServiceEarthPorn earthService;

    @Override
    public void onCreate() {
        super.onCreate();

        earthService = new ServiceEarthPorn();
    }

    public void sendRequestForNewImages(){

        Log.d("NEW_DATA", "send query");

        earthService.getApi().getNewImages(100).enqueue(new Callback<Child>() {
            @Override
            public void onResponse(Call<Child> call, Response<Child> response) {

                Log.d("NEW_DATA", "received: onResponse");
            }

            @Override
            public void onFailure(Call<Child> call, Throwable t) {
                Log.d("NEW_DATA", "received: onFailure");
            }
        });
    }

    public void sendRequestForTopImages(){

        Log.d("NEW_DATA", "send query");

        earthService.getApi().getTopImages(100).enqueue(new Callback<Child>() {
            @Override
            public void onResponse(Call<Child> call, Response<Child> response) {

                Log.d("NEW_DATA", "received: onResponse");
            }

            @Override
            public void onFailure(Call<Child> call, Throwable t) {

                Log.d("NEW_DATA", "received: onResponse");
            }
        });
    }
}
