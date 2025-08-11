package com.example.academicassistant;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.academicassistant.work.ReminderWorker;

import java.util.concurrent.TimeUnit;

public class MyApp extends Application {
    public static final String CHANNEL_ID = "reminders";
    @Override public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel ch = new NotificationChannel(CHANNEL_ID, "Task Reminders", NotificationManager.IMPORTANCE_DEFAULT);
            ((NotificationManager)getSystemService(NOTIFICATION_SERVICE)).createNotificationChannel(ch);
        }
        PeriodicWorkRequest w = new PeriodicWorkRequest.Builder(ReminderWorker.class, 15, TimeUnit.MINUTES).build();
        WorkManager.getInstance(this).enqueueUniquePeriodicWork("reminders", ExistingPeriodicWorkPolicy.KEEP, w);
    }
}
