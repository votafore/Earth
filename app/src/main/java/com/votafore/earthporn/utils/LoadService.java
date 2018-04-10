package com.votafore.earthporn.utils;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;

import android.os.Process;
import android.util.Log;

public class LoadService extends Service {

    private String TAG = "ServiceTest";

    private HandlerThread workerThread;
    private LoadingHandler handler;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate");

        workerThread = new HandlerThread("worker thread", Process.THREAD_PRIORITY_BACKGROUND);
        workerThread.start();

        handler = new LoadingHandler(workerThread.getLooper());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "onStartCommand");

        handler.sendMessage(handler.obtainMessage(0, startId, 0));

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        workerThread.quitSafely();
    }

    public class LoadingHandler extends Handler{

        public LoadingHandler(Looper looper){
            super(looper);
            Log.d(TAG, "MessageHandler: constructor");
        }

        @Override
        public void handleMessage(Message msg) {

            Log.d(TAG, "MessageHandler: handleMessage: start");

            long time = System.currentTimeMillis() + 5*1000;

            while (time > System.currentTimeMillis()){
                synchronized (this){
                    try{
                        Thread.sleep(time - System.currentTimeMillis());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            Log.d(TAG, "MessageHandler: handleMessage: end");

            stopSelf(msg.arg1);
        }
    }
}
