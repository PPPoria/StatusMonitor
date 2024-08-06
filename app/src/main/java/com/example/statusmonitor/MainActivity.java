package com.example.statusmonitor;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.monitor.FPSCounter;
import com.example.monitor.MemoryInfoProvider;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private TextView testText;
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

        FPSCounter.initFPSCounter();
        MemoryInfoProvider.initMemoryInfoProvider(this);

        initView();
        initListener();
    }

    @SuppressLint("SetTextI18n")
    private void initListener() {
        testText.setOnClickListener(v -> {
            if(which == 0) {
                double FPS = FPSCounter.getFPS();
                testText.setText(String.valueOf(FPS) + "fps");
            }else if (which == 1){
                long memory = MemoryInfoProvider.getUsedMemory();
                testText.setText(String.valueOf(memory) + "Mb");
            }

            if (which++ == 1) which = 0;
        });
    }

    private void initView() {
        testText = findViewById(R.id.test_text);
    }
}