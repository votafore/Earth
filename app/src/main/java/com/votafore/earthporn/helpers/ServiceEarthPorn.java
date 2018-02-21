package com.votafore.earthporn.helpers;

import retrofit2.Retrofit;

/**
 * @author votarore
 * Created on 21.02.2018.
 */


public class ServiceEarthPorn {

    private EarthPornAPI api;

    public ServiceEarthPorn(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.reddit.com")
                .build();

        api = retrofit.create(EarthPornAPI.class);
    }

    public EarthPornAPI getApi(){
        return api;
    }
}
