package com.codeGeekerz.project.pollutionTracker;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by User on 15-03-2016.
 */
public class GetNearestStation extends AsyncTask<Call, Void, AllStation> {
    private static final String TAG = "GetNearestStation";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected AllStation doInBackground(Call... calls) {
        AllStation nearestStationData = null;
        Call<AllStation> call = calls[0];
        try {
            Response<AllStation> response = call.execute();
            if (response != null && response.isSuccess()) {
                nearestStationData = response.body();

            } else {
                Log.i(TAG, "Call failed to get pollution Data");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nearestStationData;
    }
}
