package com.example.monitor;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadPresenter {
    private static final String TAG = "UploadPresenter";
    private static UploadPresenter instance = null;
    private static boolean already = false;
    private static String baseUrl;
    private static int projectId;
    private static Gson gson;
    private static Retrofit retrofit;
    private static UploadApi uploadApi;

    private UploadPresenter() {
    }

    public static void initUploadPresenter(String baseUrl, int projectId) {
        UploadPresenter.baseUrl = baseUrl;
        UploadPresenter.projectId = projectId;
        gson = new GsonBuilder().setPrettyPrinting().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        uploadApi = retrofit.create(UploadApi.class);
        already = true;
    }

    public static UploadPresenter getInstance() {
        if (instance == null) instance = new UploadPresenter();
        return instance;
    }

    public static void uploadMemoryAndFPS(String view, int memory, int FPS){
        if (!already) return;
        MemoryAndFPSData memoryAndFPSData = new MemoryAndFPSData();
        memoryAndFPSData.setProjectId(projectId);
        memoryAndFPSData.setView(view);
        memoryAndFPSData.setMemory(memory);
        memoryAndFPSData.setFps(FPS);
        Call<CallStatusData> dataCall = uploadApi.postMemoryAndFPS(memoryAndFPSData);
        dataCall.enqueue(new Callback<CallStatusData>() {
            @Override
            public void onResponse(Call<CallStatusData> call, Response<CallStatusData> response) {

            }

            @Override
            public void onFailure(Call<CallStatusData> call, Throwable t) {

            }
        });
    }

    public static void uploadExceptionString(String eStr){
        if (!already) return;
        ExceptionData exceptionData = new ExceptionData();
        exceptionData.setProjectId(projectId);
        exceptionData.setData(eStr);
        Call<CallStatusData> dataCall = uploadApi.postException(exceptionData);
        dataCall.enqueue(new Callback<CallStatusData>() {
            @Override
            public void onResponse(Call<CallStatusData> call, Response<CallStatusData> response) {

            }

            @Override
            public void onFailure(Call<CallStatusData> call, Throwable t) {

            }
        });
    }
}
