package com.example.monitor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UploadApi {

    @POST("mobile/performanceLog")
    Call<CallStatusData> postMemoryAndFPS(@Body List<MemoryAndFPSData> data);

    @POST("mobile/exceptionLog")
    Call<CallStatusData> postException(@Body List<ExceptionData> data);
}
