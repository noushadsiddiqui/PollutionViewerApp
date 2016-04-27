package com.codeGeekerz.project.pollutionTracker;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.codeGeekerz.project.pollutionTracker.adapter.MyRecyclerViewAdapter;
import com.codeGeekerz.project.pollutionTracker.utils.ApplicationUIUtils;
import com.codeGeekerz.project.pollutionTracker.utils.DataObject;
import com.codeGeekerz.project.pollutionTracker.utils.SessionManager;
import com.codeGeekerz.project.pollutionTracker.utils.SwipeableRecyclerViewTouchListener;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuDisplayActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final String TAG = "userCurrentLocation";
    private static final int PICK_STATION_REQUEST = 0;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private final int REQUEST_CODE_ASK_PERMISSIONS = 1;
    private final ArrayList<DataObject> results = new ArrayList<>();
    StationPollutionDetail pollutionData = null;
    Location mLocation;
    LocationManager locManager = null;
    Double mLatitude, mLongitude;
    private HashMap<String, StationPollutionDetail> stationPollutionDetailHashMap = new HashMap<>();
    private GoogleApiClient mGoogleApiClient;
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SessionManager session;
    private AllStation nearestStation;
    private TokenResponse token_response;
    private HashSet<String> selectedStations = new HashSet<>();
    private String token;
    private LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        session = new SessionManager(this);
        setContentView(R.layout.activity_menu_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuDisplayActivity.this, DisplayStatesOptionsActivity.class);
                startActivityForResult(intent, PICK_STATION_REQUEST);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        locManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ApplicationUIUtils.isNetworkAvailable(this)) {
            showProgressBar(this, "Downloading Content.....");
            getSessionToken();
            mLocationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                    .setFastestInterval(1 * 1000); // 1 second, in milliseconds
            setRecyclerView();
            if (ApplicationUIUtils.displayNetworkStatus(locManager) || ApplicationUIUtils.displayGPSStatus(locManager)) {
                buildGoogleApiClient(this);
            }
        } else {
            ApplicationUIUtils.showAlertDialog(this, "Internet Connection Error", "Please connect to Internet and Retry", false);
        }
    }

    private void getSessionToken() {
        if (session.getToken().isEmpty()) {
            APIService apiService = APIHelper.getApiService();
            String android_id = Settings.Secure.getString(this.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            Call<TokenResponse> tokenResponse = apiService.authenticateUser(android_id);
            try {
                token_response = new GetUserToken().execute(tokenResponse).get();
                if (token_response != null && token_response.isSuccess()) {
                    token = token_response.getToken();
                    if (token != null && !token.isEmpty()) {
                        session.setSessionToken(token);
                    }
                }
                //TODO: check for refresh token
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }


    private void setRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(false);
        Iterator<String> itr = session.getStationsList().iterator();
        DataObject d1 = new DataObject("Your Nearest Pollution Station is : ", "", "", ContextCompat.getColor(this, R.color.blue));
        results.add(d1);
        while (itr.hasNext()) {
            DataObject d = new DataObject(itr.next(), "", "", ContextCompat.getColor(this, R.color.blue));
            results.add(d);
        }
        mAdapter = new MyRecyclerViewAdapter(results);
        mRecyclerView.setAdapter(mAdapter);
        SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener(mRecyclerView,
                        new SwipeableRecyclerViewTouchListener.SwipeListener() {
                            @Override
                            public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    DataObject d = results.get(position);
                                    results.remove(position);
                                    mAdapter.notifyItemRemoved(position);
                                    selectedStations = session.getStationsList();
                                    selectedStations.remove(d.getmText1());
                                    session.setSessionStateList(selectedStations, session.getToken());
                                }
                                mAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    DataObject d = results.get(position);
                                    results.remove(position);
                                    mAdapter.notifyItemRemoved(position);
                                    selectedStations = session.getStationsList();
                                    selectedStations.remove(d.getmText1());
                                    session.setSessionStateList(selectedStations, session.getToken());
                                }
                                mAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public boolean canSwipe(int position) {
                                return position != 0;
                            }
                        });
        results.clear();
        mRecyclerView.addOnItemTouchListener(swipeTouchListener);
    }

    private synchronized void buildGoogleApiClient(Context ctx) {
        Log.i(TAG, "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(ctx)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_display, menu);
        menu.findItem(R.id.action_map).setVisible(false);
        menu.findItem(R.id.action_settings).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            StringBuilder sb = new StringBuilder();
            Iterator<DataObject> itr = results.iterator();
            int count = 1;
            while (itr.hasNext()) {
                DataObject d = itr.next();
                sb.append(d.getContent(count++));
            }
            String shareBody = sb.toString();
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        createFirstCard();
    }

    private void createFirstCard() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            checkForPermissionGranted();
            return;
        } else {
            mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLocation != null) {
                handleNewLocation(mLocation, false);
            } else {
                if (!mGoogleApiClient.isConnected()) {
                    mGoogleApiClient.connect();
                }
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                callForPollutionData("Sorry No Nearest Area Found", "", true, false);
            }
            if (session.getStationsList() != null && !session.getStationsList().isEmpty() && !session.getToken().isEmpty()) {
                Log.i(TAG, "Token is" + session.getToken());
                Set<String> user_station = session.getStationsList();
                Iterator<String> itr = user_station.iterator();
                while (itr.hasNext()) {
                    String stationName = itr.next();
                    callForPollutionData(stationName, "", false, false);
                }
            }
            hideProgressDialog();
        }
    }

    private void handleNewLocation(Location mLocation, final boolean isFromLocationUpdate) {
        mLatitude = mLocation.getLatitude();
        mLongitude = mLocation.getLongitude();
            /*if (mGoogleApiClient.isConnected() && mLocation != null) {
                mResultReceiver = new AddressResultReceiver(new Handler());
                mResultReceiver.setReceiver(this);
                Intent intent = new Intent(MenuDisplayActivity.this, FetchAddressIntentService.class);
                intent.putExtra(Constants.RECEIVER, mResultReceiver);
                intent.putExtra(Constants.LOCATION_DATA_EXTRA, mLocation);
                startService(intent);
            }*/
        //calling API to get nearest station area
        APIService apiService = APIHelper.getApiService();
        Call<AllStation> nearestStationCall = apiService.findNearestStation(mLatitude, mLongitude);
        nearestStationCall.enqueue(new Callback<AllStation>() {
            @Override
            public void onResponse(Response<AllStation> response) {
                if (response.isSuccess()) {
                    nearestStation = response.body();
                    if (nearestStation != null && !session.getToken().isEmpty()) {
                        callForPollutionData(nearestStation.getStationName(), nearestStation.getFullStationName(), true, isFromLocationUpdate);
                    } else {
                        callForPollutionData("Sorry No Nearest Area Found", "", true, isFromLocationUpdate);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                ApplicationUIUtils.showAlertDialog(MenuDisplayActivity.this, "Error", "A Problem has occured! Please retry", false);
            }
        });
    }

    private void checkForPermissionGranted() {
        int hasAccessLocationPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (hasAccessLocationPermission != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                showMessageOKCancel("You need to allow access to Location Data",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MenuDisplayActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        REQUEST_CODE_ASK_PERMISSIONS);
                            }
                        });
                return;
            }
            ActivityCompat.requestPermissions(MenuDisplayActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MenuDisplayActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    createFirstCard();
                } else {
                    // Permission Denied
                    Toast.makeText(MenuDisplayActivity.this, "ACCESS_FINE_LOCATION Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        ApplicationUIUtils.showAlertDialog(this, "Internet Connection Error", "Sorry Not connected to Internet or gps", false);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
        ApplicationUIUtils.showAlertDialog(this, "Internet Connection Error", "Sorry Not connected to Internet or gps", false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_STATION_REQUEST) {
            String stationName = resultData.getStringExtra("stationName");
            callForPollutionData(stationName, "", false, false);
            mAdapter.notifyDataSetChanged();
            selectedStations = session.getStationsList();
            selectedStations.add(stationName);
            session.setSessionStateList(selectedStations, session.getToken());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient == null) {
            if (ApplicationUIUtils.isNetworkAvailable(this)) {
                buildGoogleApiClient(MenuDisplayActivity.this);
            }
        }
        if (mAdapter != null) {
            mAdapter.setOnItemClickListener(new MyRecyclerViewAdapter
                    .MyClickListener() {
                String stationName;

                @Override
                public void onItemClick(int position, View v) {
                    if (position != 0) {
                        stationName = results.get(position).getmText1();
                    } else {
                        if (nearestStation != null) {
                            stationName = nearestStation.getStationName();
                        }
                    }
                    Intent intent = new Intent(MenuDisplayActivity.this, PollutionDetailActivity.class);
                    intent.putExtra("pollutionDetail", stationPollutionDetailHashMap.get(stationName));
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    /*@Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        DataObject d = new DataObject(" Your current location is :", resultData.getString(Constants.RESULT_DATA_KEY), ApplicationUIUtils.getPollutionStatus(MenuDisplayActivity.this, "50"), ApplicationUIUtils.getCardBackgroundColor(MenuDisplayActivity.this, "40"));
        results.add(d);
        if (session.getStationsList() != null && !session.getStationsList().isEmpty() && !session.getToken().isEmpty()) {
            Set<String> user_station = session.getStationsList();
            Iterator<String> itr = user_station.iterator();
            while (itr.hasNext()) {
                String stationName = itr.next();
                callForPollutionData(stationName);
            }
        }
        mAdapter.notifyDataSetChanged();
    }*/

    private void callForPollutionData(final String stationName, final String stationFullName, final boolean firstCard, final boolean isFromLocationUpdate) {
        if (!stationName.equals("Sorry No Nearest Area Found")) {
            APIService apiService = APIHelper.getApiService();
            Call<StationPollutionDetail> call = apiService.loadAllDetail(stationName, session.getToken());
            call.enqueue(new Callback<StationPollutionDetail>() {
                @Override
                public void onResponse(Response<StationPollutionDetail> response) {
                    if (response.isSuccess() && response != null) {
                        pollutionData = response.body();
                        if (!stationPollutionDetailHashMap.containsKey(stationName)) {
                            stationPollutionDetailHashMap.put(stationName, pollutionData);
                        }
                        String aqi = String.valueOf(ApplicationUIUtils.roundedAQI(pollutionData.getAqi()));
                        String text2 = "AQI : " + aqi;
                        //get backGround Color of card according to AQI value
                        int color = ApplicationUIUtils.getCardBackgroundColor(MenuDisplayActivity.this, pollutionData.getAqi());
                        String text3 = ApplicationUIUtils.getPollutionStatus(MenuDisplayActivity.this, pollutionData.getAqi());
                        DataObject d = new DataObject(firstCard ? "Your Nearest Pollution Station is : " + stationFullName : stationName, text2, text3, color);
                        if (firstCard) {
                            mAdapter.addItem(d, 0);
                            if (isFromLocationUpdate) {
                                mAdapter.notifyItemChanged(0);
                            }
                            mAdapter.notifyDataSetChanged();
                        } else {
                            int count = mAdapter.getItemCount();
                            mAdapter.addItem(d, count);
                            mAdapter.notifyItemChanged(count);
                        }
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    ApplicationUIUtils.showAlertDialog(MenuDisplayActivity.this, "Error", "A Problem has occured! Please retry", false);
                }
            });
        } else {
            DataObject d = new DataObject(stationName, "", "", ContextCompat.getColor(MenuDisplayActivity.this, R.color.blue));
            mAdapter.addItem(d, 0);
            mAdapter.notifyItemChanged(0);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location, true);
    }
}
