package com.example.azuga.pollutionviewer;

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
                    .baseUrl("your url here")
                    .addConverterFactory(GsonConverterFactory.create()).build();
            sApiService = retrofit.create(APIService.class);
        }
        return sApiService;
    }
}
