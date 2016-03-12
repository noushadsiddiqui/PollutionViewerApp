package com.example.azuga.pollutionviewer.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;

import com.example.azuga.pollutionviewer.R;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
                                       Boolean twoButtons) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        if (twoButtons) {
            builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
        }
        builder.create().show();
    }

    public static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }

    public static String roundUptoTwoDecimalUnits(String value) {
        return String.valueOf(new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue());
    }

    public static int roundedAQI(String aqi) {
        return (int) Math.round(Double.parseDouble(aqi));
    }

    public static int getCardBackgroundColor(Context context, String aqi) {
        int roundedAQI = roundedAQI(aqi);
        if (isBetween(roundedAQI, 0, 50)) {
            return ContextCompat.getColor(context, R.color.colorDarkGreen);
        } else if (isBetween(roundedAQI, 51, 100)) {
            return ContextCompat.getColor(context, R.color.colorLightGreen);
        } else if (isBetween(roundedAQI, 101, 200)) {
            return ContextCompat.getColor(context, R.color.colorYellow);
        } else if (isBetween(roundedAQI, 201, 300)) {
            return ContextCompat.getColor(context, R.color.colorOrange);
        } else if (isBetween(roundedAQI, 301, 400)) {
            return ContextCompat.getColor(context, R.color.colorRed);
        } else {
            return ContextCompat.getColor(context, R.color.colorBrown);
        }
    }

    public static String getPollutionStatus(Context context, String aqi) {
        int roundedAQI = roundedAQI(aqi);
        if (isBetween(roundedAQI, 0, 50)) {
            return context.getString(R.string.GOOD);
        } else if (isBetween(roundedAQI, 51, 100)) {
            return context.getString(R.string.SATISFACTORY);
        } else if (isBetween(roundedAQI, 101, 200)) {
            return context.getString(R.string.MODERATELY_POLLUTED);
        } else if (isBetween(roundedAQI, 201, 300)) {
            return context.getString(R.string.POOR);
        } else if (isBetween(roundedAQI, 301, 400)) {
            return context.getString(R.string.VERY_POOR);
        } else {
            return context.getString(R.string.SEVERE);
        }
    }
}
