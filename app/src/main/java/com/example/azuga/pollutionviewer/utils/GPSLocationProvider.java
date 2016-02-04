package com.example.azuga.pollutionviewer.utils;


import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

/**
 * Created by User on 21-01-2016.
 */
public class GPSLocationProvider implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {
    protected static final String TAG = "location-updates";

    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;
    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    protected LocationRequest mLocationRequest;
    /**
     * Represents a geographical location.
     */
    protected Location mCurrentLocation;
    protected LocationResult locationResult;

    public synchronized void buildGoogleApiClient(Context ctx) {
        Log.i(TAG, "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(ctx)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        LocationRequest locationRequest = createLocationRequest();
        startLocationUpdates();
    }

    protected LocationRequest createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setFastestInterval(100);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return mLocationRequest;
    }

    protected void startLocationUpdates() {
        /*LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);*/
    }

    protected void stopLocationUpdates() {
       /* LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);*/
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
