package com.example.azuga.pollutionviewer;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by User on 09-01-2016.
 */
public interface APIService {
    @GET("pollution/{gas}/{stationName}")
    Call<StationPollutionDetail> loadPollutantDetail(@Path("gas") String gas, @Path("cityName") String stationName);

    @GET("pollution/{stationName}")
    Call<StationPollutionDetail> loadAllDetail(@Path("stationName") String stationName);

    //These methods are to get list of states, cities, station for pollution Detail
    @GET("stations/")
    Call<ArrayList<AllStation>> loadAllStates();

    @GET("stations/{stateId}")
    Call<ArrayList<AllStation>> loadAllCities(@Path("stateId") String stateId);

    @GET("stations/{stateId}/{cityId}")
    Call<ArrayList<AllStation>> loadAllStations(@Path("stateId") String stateId, @Path("cityId") String cityId);

}
