package com.votafore.earthporn.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import com.votafore.earthporn.content.DataBaseManager;
import com.votafore.earthporn.content.DataProvider;
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

    public ImageLoader(Context context){

        final DataSet dataSet = DataSet.getInstance();

        this.mListOfImages = dataSet.getList();
        this.mEarthService = new ServiceEarthPorn();

        final ContentResolver resolver = context.getContentResolver();

        this.listener = new Callback<ListOfImages>() {
            @Override
            public void onResponse(Call<ListOfImages> call, Response<ListOfImages> response) {

                mListOfImages.clear();

                for (Child child: response.body().getData().getChildren()){
                    ImageItem img = new ImageItem();
                    img.item = child.getData();
                    mListOfImages.add(img);

                    ContentValues values = new ContentValues();
                    values.put(DataBaseManager.COLUMN_ID, img.item.getId());
                    values.put(DataBaseManager.COLUMN_URL, img.item.getUrl());

                    resolver.insert(DataProvider.BASE_URI, values);
                }

                dataSet.notifyObservers();
            }

            @Override
            public void onFailure(Call<ListOfImages> call, Throwable t) {

            }
        };
    }

    public void getNewImages(){
        mEarthService.getApi().getNewImages(30).enqueue(listener);
    }

    public void getTopImages(){
        mEarthService.getApi().getTopImages(30).enqueue(listener);
    }
}
