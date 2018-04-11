package com.votafore.earthporn.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;

import com.votafore.earthporn.ActivityMain;

public class LoadService extends Service {

    public static final String KEY_RECEIVER        = "receiver";
    public static final String KEY_STARTLOADING    = "startLoading";
    public static final String KEY_OPENSERVICEPAGE = "openServicePage";

    private int notificationID = 120;
    private boolean isLoadingStarted = false;

    private ResultReceiver clientReceiver;

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {

        if (intent == null)
            return START_NOT_STICKY;

        clientReceiver = intent.getParcelableExtra(KEY_RECEIVER);

        if(intent.getBooleanExtra(KEY_STARTLOADING, false)){
            startLoading();
        }

        if (isLoadingStarted){

            Bundle args = new Bundle();
            args.putInt(DataLoader.KEY_MAXVALUE, loader.getSize());
            args.putInt(DataLoader.KEY_PROGRESS, loader.getProgress());

            clientReceiver.send(0, args);
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /************** utils *****************/

    DataLoader loader;

    public void startLoading(){

        if (isLoadingStarted)
            return;

        loader = new DataLoader();

        Intent startActivity = new Intent(getApplicationContext(), ActivityMain.class);
        startActivity.putExtra(KEY_OPENSERVICEPAGE, true);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, startActivity, PendingIntent.FLAG_UPDATE_CURRENT);

        final NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        final Notification.Builder builder = new Notification.Builder(getApplicationContext())
                .setAutoCancel(false)
                .setSmallIcon(android.R.drawable.ic_input_add)
                .setContentTitle("loading")
                .setContentText("file loading started")
                .setOngoing(true)
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis());

        loader.setTarget();

        Bundle args = new Bundle();
        args.putInt(DataLoader.KEY_MAXVALUE, loader.getSize());
        args.putInt(DataLoader.KEY_PROGRESS, loader.getProgress());

        clientReceiver.send(0, args);

        loader.addListener(new DataLoader.LoadingListener() {

            @Override
            public void onLoadingStarted(int size) {
                isLoadingStarted = true;
                builder.setProgress(0,0, false);
                startForeground(notificationID, builder.build());
            }

            @Override
            public void onLoadingFinished() {
                isLoadingStarted = false;
                manager.cancel(notificationID);
                stopSelf();
                loader = null;
            }

            @Override
            public void onProgress(int progress, int size) {
                builder.setProgress(size, progress, false);
                manager.notify(notificationID, builder.build());

                Bundle args = new Bundle();
                args.putInt(DataLoader.KEY_PROGRESS, progress);

                clientReceiver.send(0, args);
            }
        });

        loader.load();
    }
}
