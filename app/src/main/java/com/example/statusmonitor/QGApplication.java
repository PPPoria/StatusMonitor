package com.example.statusmonitor;

import android.app.Application;

import com.example.monitor.QGExceptionHandler;

public class QGApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        QGExceptionHandler handler = QGExceptionHandler.getInstance();
        handler.install(this);
        handler.setExceptionTip("功能暂时不可用");
    }
}
