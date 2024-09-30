package com.example.demoserviceproject;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText edtDataIntent;
    private Button btnStartService, btnStopService, btnStartBgService, btnStartBoundService, btnStopBoundService;

    private MusicBoundService musicBoundService;
    private boolean isServiceConnected = false;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MusicBoundService.MyBinder myBinder = (MusicBoundService.MyBinder) iBinder;
            musicBoundService = myBinder.getService();
            musicBoundService.playMusic();
            isServiceConnected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isServiceConnected = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Foreground service
        edtDataIntent = findViewById(R.id.edt_data_intent);
        btnStartService = findViewById(R.id.btn_start_service);
        btnStopService = findViewById(R.id.btn_stop_service);
        btnStartService.setOnClickListener(view -> clickStartService());
        btnStopService.setOnClickListener(view -> clickStopService());

        // Background service
        btnStartBgService = findViewById(R.id.btn_start_bg_service);
        btnStartBgService.setOnClickListener(view -> clickStartBgService());
        
        // Bound service
        btnStartBoundService = findViewById(R.id.btn_start_bound_service);
        btnStartBoundService.setOnClickListener(view -> clickStartBoundService());
        btnStopBoundService = findViewById(R.id.btn_stop_bound_service);
        btnStopBoundService.setOnClickListener(view -> clickStopBoundService());
    }

    private void clickStopBoundService() {
        if (isServiceConnected){
            unbindService(serviceConnection);
            isServiceConnected = false;
        }
    }

    private void clickStartBoundService() {
        Intent intent = new Intent(this, MusicBoundService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    private void clickStartBgService() {
        Intent intent = new Intent(this, MyBackgroundService.class);
        startService(intent);
    }

    private void clickStartService() {
        Intent intent = new Intent(this, MyService.class);
        intent.putExtra("key_data_intent", edtDataIntent.getText().toString().trim());

        ContextCompat.startForegroundService(this, intent);
    }

    private void clickStopService() {
        Intent intent = new Intent(this, MyService.class);

        stopService(intent);
    }
}