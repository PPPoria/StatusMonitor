package com.example.monitor;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UploadApi {

    @POST("sdk/mobile/performanceLog")
    Call<CallStatusData> postMemoryAndFPS(@Body MemoryAndFPSData data);

    @POST("sdk/mobile/exceptionLog")
    Call<CallStatusData> postException(@Body ExceptionData data);
}
