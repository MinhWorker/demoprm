package com.example.demoserviceproject;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class MyService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("DemoPRM", "Service onCreate()");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String dataIntent = intent.getStringExtra("key_data_intent");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            sendNotification(dataIntent);
        }

        return START_NOT_STICKY;
    }

    private void sendNotification(String dataIntent) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new NotificationCompat.Builder(this, MyApplication.CHANNEL_ID)
                .setContentTitle("Demo Service")
                .setContentText(dataIntent)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);
        Log.e("DemoPRM", "sendNotification: " + dataIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("DemoPRM", "Service onDestroy()");
    }
}
