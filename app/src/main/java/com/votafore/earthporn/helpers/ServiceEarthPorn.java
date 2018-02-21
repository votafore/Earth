package com.votafore.earthporn.helpers;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author votarore
 * Created on 21.02.2018.
 */


public class ServiceEarthPorn {

    private EarthPornAPI api;

    public ServiceEarthPorn(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.reddit.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(EarthPornAPI.class);
    }

    public EarthPornAPI getApi(){
        return api;
    }
}
