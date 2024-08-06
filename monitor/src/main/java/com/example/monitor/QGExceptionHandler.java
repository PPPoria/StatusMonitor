package com.example.monitor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class QGExceptionHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "QGExemptionHandler";
    private Thread.UncaughtExceptionHandler defaultHandler;
    private static final QGExceptionHandler instance = new QGExceptionHandler();
    @SuppressLint("SimpleDateFormat")
    private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd-HH-mm-ss");
    private Map<String, String> infoMap = new HashMap<>();
    private Context context;
    private String tip = "error";

    //private私有化修饰，只允许创建一个实例
    private QGExceptionHandler() {
    }

    public static QGExceptionHandler getInstance() {
        return instance;
    }

    //初始化，设置QGExceptionHandler为默认的异常处理器
    public void install(Context context) {
        this.context = context;
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        new Handler(Looper.getMainLooper()).post(()->{
            for(;;){
                try {
                    Looper.loop();
                }catch (Throwable e){
                    Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setExceptionTip(String tip){
        this.tip = tip;
    }

    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
        if (!handleException(e) && defaultHandler != null)
            defaultHandler.uncaughtException(t, e);
    }

    private boolean handleException(Throwable e) {
        if (e == null) return false;

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }).start();

        collectDeviceInfo(context);
        saveExceptionInfo(e);

        return true;
    }

    //收集设备信息到map中
    private void collectDeviceInfo(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);

            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                infoMap.put("versionName", versionName);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error when collect device info", e);
        }
    }

    //将异常转为字符串并上报
    private void saveExceptionInfo(Throwable e) {
        //保存错误的描述
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> info : infoMap.entrySet()) {
            String key = info.getKey();
            String value = info.getValue();
            sb.append(key).append("=").append(value).append("\n");
        }

        //追溯错误的发生原因并保存
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        e.printStackTrace(printWriter);
        Throwable cause = e.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
    }
}
