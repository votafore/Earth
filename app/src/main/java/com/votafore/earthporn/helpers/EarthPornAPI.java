package com.votafore.earthporn.helpers;

import com.votafore.earthporn.models.ListOfImages;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author votarore
 * Created on 21.02.2018.
 */

public interface EarthPornAPI {

    @GET("/r/EarthPorn/top/.json")
    Call<ListOfImages> getTopImages(@Query("limit") int value);

    @GET("/r/EarthPorn/new/.json")
    Call<ListOfImages> getNewImages(@Query("limit") int value);
}
