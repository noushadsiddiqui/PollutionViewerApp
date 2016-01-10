package com.example.azuga.pollutionviewer;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by User on 09-01-2016.
 */
public interface APIService {
    @GET("{gas}/{cityName}")
    Call<StationPollutionDetail> loadPollutionDetail(@Path("gas") String gas, @Path("cityName") String cityName);
}
