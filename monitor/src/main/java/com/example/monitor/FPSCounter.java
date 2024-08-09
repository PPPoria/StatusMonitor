package com.example.monitor;

import android.view.Choreographer;

public class FPSCounter {
    private static final String TAG = "FPSCounter";
    public static boolean already = false;

    private static final long MONITOR_INTERVAL = 160L;
    private static final long MONITOR_INTERVAL_NANOS = MONITOR_INTERVAL * 1000L * 1000L;
    private static final long MAX_INTERVAL = 1000L;

    private static long mStartFrameTime = 0;
    private static int mFrameCount = 0;
    private static double mFPS;

    public static void initFPSCounter() {
        if (already) return;
        already = true;
        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long frameTimeNanos) {
                if (mStartFrameTime == 0) {
                    mStartFrameTime = frameTimeNanos;
                }
                long interval = frameTimeNanos - mStartFrameTime;
                if (interval > MONITOR_INTERVAL_NANOS) {
                    mFPS = (double) ((mFrameCount * 1000L * 1000L) * MAX_INTERVAL / interval);
                    mFrameCount = 0;
                    mStartFrameTime = 0;
                } else {
                    ++mFrameCount;
                }
                Choreographer.getInstance().postFrameCallback(this);
            }
        });
    }

    public static int getFPS() {
        return (int) mFPS;
    }
}
