package com.codeGeekerz.project.pollutionTracker;

import android.support.annotation.NonNull;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

public class APIHelper {
    private static volatile APIService sApiService;

    private APIHelper(){
    }

    @NonNull
    public static APIService getApiService() {
        if (sApiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-52-26-104-86.us-west-2.compute.amazonaws.com:8090/")
                    .addConverterFactory(GsonConverterFactory.create()).build();
            sApiService = retrofit.create(APIService.class);
        }
        return sApiService;
    }
}
