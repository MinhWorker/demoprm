package com.example.demoserviceproject;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MusicBoundService extends Service {
    private MyBinder myBinder = new MyBinder();
    private MediaPlayer mediaPlayer;

    public class MyBinder extends Binder {
        MusicBoundService getService() {
            return MusicBoundService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("DemoPRM", "onCreate: Bound service");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("DemoPRM", "onBind: Binding service");
        return myBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("DemoPRM", "onUnbind: Unbinding service");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.e("DemoPRM", "onDestroy: Bound service");
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void playMusic() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.music);
        }
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
    }
}
