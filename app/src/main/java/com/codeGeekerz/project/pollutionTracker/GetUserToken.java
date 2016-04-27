package com.codeGeekerz.project.pollutionTracker;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by User on 15-03-2016.
 */

public class GetUserToken extends AsyncTask<Call, Void, TokenResponse> {
    private static final String TAG = "GetUserToken";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected TokenResponse doInBackground(Call... calls) {
        TokenResponse tokenResponse = null;
        Call<TokenResponse> call = calls[0];
        try {
            Response<TokenResponse> response = call.execute();
            if (response != null && response.isSuccess()) {
                tokenResponse = response.body();

            } else {
                Log.i(TAG, "Call failed to get pollution Data");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tokenResponse;
    }
}
