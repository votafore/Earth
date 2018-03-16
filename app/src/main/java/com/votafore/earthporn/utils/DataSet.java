package com.votafore.earthporn.utils;

import com.votafore.earthporn.models.ImageItem;

import java.util.ArrayList;
import java.util.List;

import java.util.Observable;
import java.util.Observer;

public class DataSet extends Observable {

    private static DataSet instance;

    static {
        instance = new DataSet();
    }

    public static DataSet getInstance(){
        return instance;
    }

    @Override
    public void notifyObservers() {
        super.setChanged();
        super.notifyObservers();
    }

    private DataSet(){
        list = new ArrayList<>();
    }

    public static void registerObserver(Observer observer){
        instance.addObserver(observer);
    }

    public static void removeObserver(Observer observer){
        instance.deleteObserver(observer);
    }

    /************** data ******************/

    List<ImageItem> list;

    public List<ImageItem> getList(){
        return list;
    }

    public ImageItem getItem(int index){
        return list.get(index);
    }
}
