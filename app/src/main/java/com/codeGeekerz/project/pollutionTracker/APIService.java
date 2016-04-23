package com.codeGeekerz.project.pollutionTracker;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by User on 09-01-2016.
 */
public interface APIService {
    @GET("pollution/{gas}/{stationName}")
    Call<StationPollutionDetail> loadPollutantDetail(@Path("gas") String gas, @Path("cityName") String stationName);

    @GET("pollution/{stationName}")
    Call<StationPollutionDetail> loadAllDetail(@Path("stationName") String stationName, @Query("token") String token);

    //These methods are to get list of states, cities, station for pollution Detail
    @GET("stations")
    Call<ArrayList<AllStation>> loadAllStates(@Query("token") String token);

    @GET("neareststation/{latitude}/{longitude}")
    Call<AllStation> findNearestStation(@Path("latitude") double latitude, @Path("longitude") double longitude);

    @GET("stations/{stateId}")
    Call<ArrayList<AllStation>> loadAllCities(@Path("stateId") String stateId);

    @GET("stations/{stateId}/{cityId}")
    Call<ArrayList<AllStation>> loadAllStations(@Path("stateId") String stateId, @Path("cityId") String cityId);

    @FormUrlEncoded
    @POST("authenticate")
    Call<TokenResponse> authenticateUser(@Field("id") String id);


}
