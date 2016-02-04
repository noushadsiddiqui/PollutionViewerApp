package com.example.azuga.pollutionviewer.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by User on 10-01-2016.
 */
public class ApplicationUIUtils {

    //Method to check if network is available or not
    public static boolean isNetworkAvailable(Context ctx) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    //Method to check if GPS enable or disable
    public static boolean displayGPSStatus(LocationManager locManager) {
        boolean gps_enabled = false;
        try {
            gps_enabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }
        return gps_enabled;
    }

    //Method to check Network Provider enable or disable
    public static boolean displayNetworkStatus(LocationManager locManager) {
        boolean ntw_enabled = false;
        try {
            ntw_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }
        return ntw_enabled;
    }

    public static void showAlertDialog(Context context, String title, String message,
                                       Boolean status) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", (DialogInterface.OnClickListener) context);
        builder.setNeutralButton("Cancel", (DialogInterface.OnClickListener) context);
        builder.create().show();
    }

}
