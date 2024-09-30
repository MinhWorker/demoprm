package com.example.demoserviceproject;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyBackgroundService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(
            new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        Log.e("DemoPRM", "Backgroundservice: running");
                        try {
                            Thread.sleep(2000);
                        } catch (Exception e){
                            Log.e("DemoPRM", "Backgroundservice: error");
                        }
                }
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
