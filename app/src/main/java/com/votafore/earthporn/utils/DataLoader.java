package com.votafore.earthporn.utils;


import android.os.Handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataLoader {

    public static final String KEY_PROGRESS = "loadingProgress";
    public static final String KEY_MAXVALUE = "maxValue";

    public interface LoadingListener{
        void onLoadingStarted(int size);
        void onLoadingFinished();
        void onProgress(int progress, int size);
    }

    private Handler handler;
    private int size;
    private int progress;
    private List<LoadingListener> listeners;

    public DataLoader(){
        handler = new Handler();
        listeners = new ArrayList<>();
    }

    public void setTarget(){
        size = 4096;
    }

    public int getSize(){
        return size;
    }

    public int getProgress(){
        return progress;
    }

    public void addListener(LoadingListener listener){
        if(listeners.indexOf(listener) > -1)
            return;
        listeners.add(listener);
    }

    public void load(){

        Runnable task = new Runnable() {
            @Override
            public void run() {

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        for(LoadingListener currentlistener: listeners){
                            currentlistener.onLoadingStarted(size);
                        }
                    }
                });

                Random generator = new Random(System.currentTimeMillis());

                while (size > progress){

                    progress = Math.min(progress + generator.nextInt(120), size);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            for(LoadingListener currentlistener: listeners){
                                currentlistener.onProgress(progress, size);
                            }
                        }
                    });

                    try{
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        for(LoadingListener currentlistener: listeners){
                            currentlistener.onLoadingFinished();
                        }
                        listeners.clear();
                    }
                });
            }
        };

        Thread worker = new Thread(task);
        worker.start();
    }
}
