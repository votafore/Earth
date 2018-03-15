package com.votafore.earthporn.utils;

import android.util.Log;

import com.votafore.earthporn.helpers.ServiceEarthPorn;
import com.votafore.earthporn.models.Child;
import com.votafore.earthporn.models.ImageItem;
import com.votafore.earthporn.models.ListOfImages;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ImageLoader{

    private Callback<ListOfImages> listener;
    private ServiceEarthPorn mEarthService;
    private List<ImageItem> mListOfImages;


    private int QUERY_TOP = 1;
    private int QUERY_NEW = 2;

    private int lastQuery;

    public ImageLoader(){

        lastQuery = -1;

        DataSet dataSet = DataSet.getInstance();

        this.mListOfImages = dataSet.getList();
        this.mEarthService = new ServiceEarthPorn();

        this.listener = new Callback<ListOfImages>() {
            @Override
            public void onResponse(Call<ListOfImages> call, Response<ListOfImages> response) {

                mListOfImages.clear();

                for (Child child: response.body().getData().getChildren()){
                    ImageItem img = new ImageItem();
                    img.item = child.getData();
                    mListOfImages.add(img);
                }

                dataSet.notifyObservers();
            }

            @Override
            public void onFailure(Call<ListOfImages> call, Throwable t) {

            }
        };
    }

    public void getNewImages(){

        if(lastQuery == QUERY_NEW){
            return;
        }

        mEarthService.getApi().getNewImages(30).enqueue(listener);

        lastQuery = QUERY_NEW;
    }

    public void getTopImages(){

        if(lastQuery == QUERY_TOP){
            return;
        }

        Log.d("NEW_DATA", "send query");
        mEarthService.getApi().getTopImages(30).enqueue(listener);

        lastQuery = QUERY_TOP;
    }
}
