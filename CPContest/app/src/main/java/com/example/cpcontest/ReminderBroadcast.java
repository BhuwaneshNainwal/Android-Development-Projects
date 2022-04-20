package com.example.cpcontest;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderBroadcast extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {

        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "Tag");
        wakeLock.acquire();
        wakeLock.release();

        String contestName =intent.getStringExtra("ContestName");
        Notification.Builder builder = new Notification.Builder(context, "CP CONTEST");
        builder.setVisibility(Notification.VISIBILITY_PUBLIC);
        builder.setContentTitle(contestName);
        builder.setContentText("Starting in 10 minutes");
        builder.setTicker("New contest Alert!");
        builder.setSmallIcon(R.drawable.logo);
        @SuppressLint("WrongConstant") Notification.Builder builder1 = builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(200, builder.build());


    }
}
