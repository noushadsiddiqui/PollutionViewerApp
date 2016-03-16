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

    public static String getPollutionText(Context context, String aqi) {
        int roundedAQI = roundedAQI(aqi);
        if (isBetween(roundedAQI, 0, 50)) {
            return context.getString(R.string.GOOD_text);
        } else if (isBetween(roundedAQI, 51, 100)) {
            return context.getString(R.string.SATISFACTORY_text);
        } else if (isBetween(roundedAQI, 101, 200)) {
            return context.getString(R.string.MODERATELY_POLLUTED_text);
        } else if (isBetween(roundedAQI, 201, 300)) {
            return context.getString(R.string.POOR_text);
        } else if (isBetween(roundedAQI, 301, 400)) {
            return context.getString(R.string.VERY_POOR_text);
        } else {
            return context.getString(R.string.SEVERE_text);
        }
    }

    public static int getPM10Color(Context context, String value) {
        int roundedAQI = roundedAQI(value);
        if (isBetween(roundedAQI, 0, 50)) {
            return ContextCompat.getColor(context, R.color.colorDarkGreen);
        } else if (isBetween(roundedAQI, 51, 100)) {
            return ContextCompat.getColor(context, R.color.colorLightGreen);
        } else if (isBetween(roundedAQI, 101, 250)) {
            return ContextCompat.getColor(context, R.color.colorYellow);
        } else if (isBetween(roundedAQI, 251, 350)) {
            return ContextCompat.getColor(context, R.color.colorOrange);
        } else if (isBetween(roundedAQI, 351, 430)) {
            return ContextCompat.getColor(context, R.color.colorRed);
        } else {
            return ContextCompat.getColor(context, R.color.colorBrown);
        }
    }

    public static int getPM25Color(Context context, String value) {
        int roundedAQI = roundedAQI(value);
        if (isBetween(roundedAQI, 0, 30)) {
            return ContextCompat.getColor(context, R.color.colorDarkGreen);
        } else if (isBetween(roundedAQI, 31, 60)) {
            return ContextCompat.getColor(context, R.color.colorLightGreen);
        } else if (isBetween(roundedAQI, 61, 90)) {
            return ContextCompat.getColor(context, R.color.colorYellow);
        } else if (isBetween(roundedAQI, 91, 120)) {
            return ContextCompat.getColor(context, R.color.colorOrange);
        } else if (isBetween(roundedAQI, 121, 250)) {
            return ContextCompat.getColor(context, R.color.colorRed);
        } else {
            return ContextCompat.getColor(context, R.color.colorBrown);
        }
    }

    public static int getNitrogenDioxideColor(Context context, String value) {
        int roundedAQI = roundedAQI(value);
        if (isBetween(roundedAQI, 0, 40)) {
            return ContextCompat.getColor(context, R.color.colorDarkGreen);
        } else if (isBetween(roundedAQI, 41, 80)) {
            return ContextCompat.getColor(context, R.color.colorLightGreen);
        } else if (isBetween(roundedAQI, 81, 180)) {
            return ContextCompat.getColor(context, R.color.colorYellow);
        } else if (isBetween(roundedAQI, 181, 280)) {
            return ContextCompat.getColor(context, R.color.colorOrange);
        } else if (isBetween(roundedAQI, 281, 400)) {
            return ContextCompat.getColor(context, R.color.colorRed);
        } else {
            return ContextCompat.getColor(context, R.color.colorBrown);
        }
    }

    public static int getO3Color(Context context, String value) {
        int roundedAQI = roundedAQI(value);
        if (isBetween(roundedAQI, 0, 50)) {
            return ContextCompat.getColor(context, R.color.colorDarkGreen);
        } else if (isBetween(roundedAQI, 51, 100)) {
            return ContextCompat.getColor(context, R.color.colorLightGreen);
        } else if (isBetween(roundedAQI, 101, 168)) {
            return ContextCompat.getColor(context, R.color.colorYellow);
        } else if (isBetween(roundedAQI, 169, 208)) {
            return ContextCompat.getColor(context, R.color.colorOrange);
        } else if (isBetween(roundedAQI, 209, 748)) {
            return ContextCompat.getColor(context, R.color.colorRed);
        } else {
            return ContextCompat.getColor(context, R.color.colorBrown);
        }
    }

    public static int getCOColor(Context context, String value) {
        int roundedAQI = roundedAQI(value);
        if (isBetween(roundedAQI, 0, 1)) {
            return ContextCompat.getColor(context, R.color.colorDarkGreen);
        } else if (isBetween(roundedAQI, 1, 2)) {
            return ContextCompat.getColor(context, R.color.colorLightGreen);
        } else if (isBetween(roundedAQI, 2, 10)) {
            return ContextCompat.getColor(context, R.color.colorYellow);
        } else if (isBetween(roundedAQI, 10, 17)) {
            return ContextCompat.getColor(context, R.color.colorOrange);
        } else if (isBetween(roundedAQI, 17, 34)) {
            return ContextCompat.getColor(context, R.color.colorRed);
        } else {
            return ContextCompat.getColor(context, R.color.colorBrown);
        }
    }

    public static int getSO2Color(Context context, String value) {
        int roundedAQI = roundedAQI(value);
        if (isBetween(roundedAQI, 0, 40)) {
            return ContextCompat.getColor(context, R.color.colorDarkGreen);
        } else if (isBetween(roundedAQI, 41, 80)) {
            return ContextCompat.getColor(context, R.color.colorLightGreen);
        } else if (isBetween(roundedAQI, 81, 380)) {
            return ContextCompat.getColor(context, R.color.colorYellow);
        } else if (isBetween(roundedAQI, 381, 800)) {
            return ContextCompat.getColor(context, R.color.colorOrange);
        } else if (isBetween(roundedAQI, 801, 1600)) {
            return ContextCompat.getColor(context, R.color.colorRed);
        } else {
            return ContextCompat.getColor(context, R.color.colorBrown);
        }
    }

    public static int getNH3Color(Context context, String value) {
        int roundedAQI = roundedAQI(value);
        if (isBetween(roundedAQI, 0, 200)) {
            return ContextCompat.getColor(context, R.color.colorDarkGreen);
        } else if (isBetween(roundedAQI, 201, 400)) {
            return ContextCompat.getColor(context, R.color.colorLightGreen);
        } else if (isBetween(roundedAQI, 401, 800)) {
            return ContextCompat.getColor(context, R.color.colorYellow);
        } else if (isBetween(roundedAQI, 801, 1200)) {
            return ContextCompat.getColor(context, R.color.colorOrange);
        } else if (isBetween(roundedAQI, 1200, 1800)) {
            return ContextCompat.getColor(context, R.color.colorRed);
        } else {
            return ContextCompat.getColor(context, R.color.colorBrown);
        }
    }
}
