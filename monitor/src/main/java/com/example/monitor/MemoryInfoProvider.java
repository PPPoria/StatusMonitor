package com.example.monitor;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

public class MemoryInfoProvider {
    public static boolean already = false;

    public static void initMemoryInfoProvider() {
        if (already) return;
        already = true;
    }

    public static int getAvailMemory() {
        if (!already) return 0;
        return (int)(Runtime.getRuntime().freeMemory() / 1024L / 1024L);
    }

    public static int getTotalMemory() {
        if (!already) return 0;
        return (int)(Runtime.getRuntime().totalMemory() / 1024L / 1024L);
    }

    public static int getUsedMemory() {
        if (!already) return 0;
        return getTotalMemory() - getAvailMemory();
    }
}
