package com.codeGeekerz.project.pollutionTracker;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by User on 03-02-2016.
 */
public class DownloadPollutionData extends AsyncTask<Call, Void, StationPollutionDetail> {

    private static final String TAG = "DownloadPollutionData";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected StationPollutionDetail doInBackground(Call... strings) {
        StationPollutionDetail pollutionData = null;
        Call<StationPollutionDetail> call = strings[0];
        try {
            Response<StationPollutionDetail> response = call.execute();
            if (response != null && response.isSuccess()) {
                pollutionData = response.body();
            } else {
                Log.i(TAG, "Call failed to get pollution Data");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pollutionData;
    }
}
