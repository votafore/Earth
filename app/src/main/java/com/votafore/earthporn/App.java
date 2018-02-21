package com.votafore.earthporn;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.votafore.earthporn.helpers.ServiceEarthPorn;
import com.votafore.earthporn.models.ListOfImages;

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

        earthService.getApi().getNewImages(100).enqueue(new Callback<ListOfImages>() {
            @Override
            public void onResponse(Call<ListOfImages> call, Response<ListOfImages> response) {

                Log.d("NEW_DATA", "received: onResponse");
            }

            @Override
            public void onFailure(Call<ListOfImages> call, Throwable t) {
                Log.d("NEW_DATA", "received: onFailure");
                Toast.makeText(getApplicationContext(), "failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void sendRequestForTopImages(){

        Log.d("NEW_DATA", "send query");

        earthService.getApi().getTopImages(100).enqueue(new Callback<ListOfImages>() {
            @Override
            public void onResponse(Call<ListOfImages> call, Response<ListOfImages> response) {

                Log.d("NEW_DATA", "received: onResponse");
            }

            @Override
            public void onFailure(Call<ListOfImages> call, Throwable t) {
                Log.d("NEW_DATA", "received: onResponse");
                Toast.makeText(getApplicationContext(), "failure", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
