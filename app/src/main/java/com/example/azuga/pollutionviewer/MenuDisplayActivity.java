package com.example.azuga.pollutionviewer;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
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

import com.example.azuga.pollutionviewer.adapter.AddressResultReceiver;
import com.example.azuga.pollutionviewer.adapter.MyRecyclerViewAdapter;
import com.example.azuga.pollutionviewer.utils.ApplicationUIUtils;
import com.example.azuga.pollutionviewer.utils.Constants;
import com.example.azuga.pollutionviewer.utils.DataObject;
import com.example.azuga.pollutionviewer.utils.SessionManager;
import com.example.azuga.pollutionviewer.utils.SwipeableRecyclerViewTouchListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;

public class MenuDisplayActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, AddressResultReceiver.Receiver {

    private static final String TAG = "userCurrentLocation";
    private static final int PICK_STATION_REQUEST = 0;
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
    private AddressResultReceiver mResultReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        session = new SessionManager(this);
//        session.checkLogin();
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
        if (savedInstanceState != null) {
            return;
        }

        locManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ApplicationUIUtils.isNetworkAvailable(this) || ApplicationUIUtils.displayGPSStatus(locManager)) {
            setRecyclerView();
            buildGoogleApiClient(this);

        } else {
            ApplicationUIUtils.showAlertDialog(this, "Internet Connection Error", "Sorry Not connected to Internet or gps", false);
        }
    }

    private void setRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(false);
        mAdapter = new MyRecyclerViewAdapter(results);
        mRecyclerView.setAdapter(mAdapter);
        SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener(mRecyclerView,
                        new SwipeableRecyclerViewTouchListener.SwipeListener() {
                            @Override
                            public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    results.remove(position);
                                    mAdapter.notifyItemRemoved(position);
                                }
                                mAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    results.remove(position);
                                    mAdapter.notifyItemRemoved(position);
                                }
                                mAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public boolean canSwipe(int position) {
                                if (position == 0) {
                                    return false;
                                }
                                return true;
                            }
                        });

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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null) {
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
        }

        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLocation != null) {
            mLatitude = mLocation.getLatitude();
            mLongitude = mLocation.getLongitude();
            if (mGoogleApiClient.isConnected() && mLocation != null) {
                mResultReceiver = new AddressResultReceiver(new Handler());
                mResultReceiver.setReceiver(this);
                Intent intent = new Intent(MenuDisplayActivity.this, FetchAddressIntentService.class);
                intent.putExtra(Constants.RECEIVER, mResultReceiver);
                intent.putExtra(Constants.LOCATION_DATA_EXTRA, mLocation);
                startService(intent);
            }
        }
    }

    @TargetApi(23)
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
        createFirstCard();
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
        ApplicationUIUtils.showAlertDialog(this, "Internet Connection Error", "Sorry Not connected to Internet or gps", false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_STATION_REQUEST) {
            String stationName = resultData.getStringExtra("stationName");
            APIService apiService = APIHelper.getApiService();
            Call<StationPollutionDetail> call = apiService.loadAllDetail(stationName);
            try {
                pollutionData = new DownloadPollutionData().execute(call).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            if (!stationPollutionDetailHashMap.containsKey(stationName)) {
                stationPollutionDetailHashMap.put(stationName, pollutionData);
            }
            String aqi = ApplicationUIUtils.roundUptoTwoDecimalUnits(pollutionData.getAqi());
            String text2 = "AQI Value : " + aqi;
            //get backGround Color of card according to AQI value
            int color = ApplicationUIUtils.getCardBackgroundColor(MenuDisplayActivity.this, pollutionData.getAqi());
            Log.i(TAG, "COLOR IS " + color);
            DataObject d = new DataObject(stationName, text2, color);
            results.add(d);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mResultReceiver != null) {
            mResultReceiver.setReceiver(this);
        }
        if (mAdapter != null) {
            mAdapter.setOnItemClickListener(new MyRecyclerViewAdapter
                    .MyClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    if (position != 0) {
                        Intent intent = new Intent(MenuDisplayActivity.this, PollutionDetailActivity.class);
                        String stationName = results.get(position).getmText1();
                        intent.putExtra("pollutionDetail", stationPollutionDetailHashMap.get(stationName));
                        startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGoogleApiClient.disconnect();
        if (mResultReceiver != null) {
            mResultReceiver.setReceiver(null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mResultReceiver != null) {
            mResultReceiver.setReceiver(null);
        }
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        DataObject d = new DataObject(" Your current location is :", resultData.getString(Constants.RESULT_DATA_KEY), ApplicationUIUtils.getCardBackgroundColor(MenuDisplayActivity.this, "40"));
        results.add(d);
        mAdapter.notifyDataSetChanged();
    }
}
