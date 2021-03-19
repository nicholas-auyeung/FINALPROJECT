package com.hcl.finalproject;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import java.util.List;


public class FINALPROJECT extends Application implements LifecycleObserver {

    public static final String APP_BACKGROUND_ID = "appbackground";

    private NotificationManagerCompat notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
        createNotificationChannels();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onEnterBackground() {

        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

        List<ActivityManager.AppTask> tasks = activityManager.getAppTasks();

        tasks.stream().forEach(t ->{
            if(t.getTaskInfo().topActivity.getShortClassName().compareTo(".MainActivity") == 0 || t.getTaskInfo().topActivity.getShortClassName().compareTo(".ProfileActivity") == 0){

                Intent notificationIntent = getPackageManager()
                        .getLaunchIntentForPackage("com.hcl.finalproject");
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                notificationManager = NotificationManagerCompat.from(this);
                Notification notification = new NotificationCompat.Builder(this, APP_BACKGROUND_ID)
                        .setSmallIcon(R.drawable.ic_android_face)
                        .setContentTitle("Reminder")
                        .setContentText("don't forget about me")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .setContentIntent(pendingIntent)
                        .build();
                notificationManager.notify(1, notification);
            }
        });

    }

    private void createNotificationChannels(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            NotificationChannel appBackground = new NotificationChannel(
                    APP_BACKGROUND_ID,
                    "App in background",
                    NotificationManager.IMPORTANCE_HIGH
            );
            appBackground.setDescription("In order to reaccess the application quickly after leaving");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(appBackground);
        }
    }


}