package com.example.statusmonitor;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.monitor.FPSCounter;
import com.example.monitor.MemoryInfoProvider;
import com.example.monitor.UploadPresenter;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private TextView testStatus;
    private TextView testException;
    private int which = 0;

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

        initView();
        initListener();
    }

    @SuppressLint("SetTextI18n")
    private void initListener() {
        testStatus.setOnClickListener(v -> {
            if (which == 0) {
                int FPS = FPSCounter.getFPS();
                testStatus.setText(String.valueOf(FPS) + "fps");
            } else if (which == 1) {
                int memory = MemoryInfoProvider.getUsedMemory();
                testStatus.setText(String.valueOf(memory) + "Mb");
            }

            if (which++ == 1) which = 0;
        });
        testException.setOnClickListener(v -> {
            throw new RuntimeException("test");
        });
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        testStatus = findViewById(R.id.test_status);
        testException = findViewById(R.id.test_exception);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                int FPS = FPSCounter.getFPS();
                runOnUiThread(() -> {
                    testStatus.setText(String.valueOf(FPS) + "fps");
                });
            }
        }, 1000, 500);
    }
}