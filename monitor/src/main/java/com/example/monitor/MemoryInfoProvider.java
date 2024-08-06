package com.example.monitor;

import android.app.ActivityManager;
import android.content.Context;

public class MemoryInfoProvider {
    private static final String TAG = "MemoryInfoProvider";
    public static boolean already = false;

    private static ActivityManager manager;
    private static final ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();

    public static void initMemoryInfoProvider(Context context) {
        if (already) return;
        already = true;
        manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

    }

    public static long getAvailMemory() {
        if (manager == null) return 0;
        manager.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem / 1024L / 1024L;
    }

    public static long getTotalMemory() {
        if (manager == null) return 0;
        manager.getMemoryInfo(memoryInfo);
        return memoryInfo.totalMem / 1024L / 1024L;
    }

    public static long getUsedMemory() {
        if (manager == null) return 0;
        return getTotalMemory() - getAvailMemory();
    }
}
