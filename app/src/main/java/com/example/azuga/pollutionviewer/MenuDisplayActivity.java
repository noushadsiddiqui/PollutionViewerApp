package com.example.azuga.pollutionviewer;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.azuga.pollutionviewer.adapter.MyRecyclerViewAdapter;
import com.example.azuga.pollutionviewer.utils.ApplicationUIUtils;
import com.example.azuga.pollutionviewer.utils.DataObject;
import com.example.azuga.pollutionviewer.utils.SwipeableRecyclerViewTouchListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;

public class MenuDisplayActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "userCurrentLocation";
    private static final int PICK_STATION_REQUEST = 0;
    private static final ArrayList<DataObject> results = new ArrayList<DataObject>();
    StationPollutionDetail pollutionData = null;
    private static boolean isFromOnActivityResult = false;
    Location mLocation;
    LocationManager locManager = null;
    String mLatitude, mLongitude;
    private GoogleApiClient mGoogleApiClient;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        //calling method for getting user's latest location
        locManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ApplicationUIUtils.displayNetworkStatus(locManager) || ApplicationUIUtils.displayGPSStatus(locManager)) {
            buildGoogleApiClient(this);
            setRecyclerView();

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
        mRecyclerView.setAdapter(new MyRecyclerViewAdapter(null));
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.i(TAG, "Permission not granted");
            return;
        } else {
            mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLocation != null) {
                mLatitude = String.valueOf(mLocation.getLatitude());
                mLongitude = String.valueOf(mLocation.getLongitude());
            }
            String text1 = " Your current location is Latitude : " + mLatitude + " Longitude : " + mLongitude;
            String text2 = "Pollution Data yet to find";
            DataObject d = new DataObject(text1, text2);
            ((MyRecyclerViewAdapter) mAdapter).addItem(d, 0);
            mRecyclerView.swapAdapter(new MyRecyclerViewAdapter(results), false);
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
            Toast.makeText(this, "Your choosen station is : " + resultData, Toast.LENGTH_SHORT).show();
            String stationName = resultData.getStringExtra("stationName");
            APIService apiService = APIHelper.getApiService();
            Call<StationPollutionDetail> call = apiService.loadAllDetail(stationName);
            try {
                pollutionData = new DownloadPollutionData().execute(call).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            String text2 = "PM Value : " + pollutionData.getPollutionLevel();
            DataObject d = new DataObject(stationName, text2);
            int n = results.size();
            Log.i(TAG, "no. of items in list are :" + n);
            ((MyRecyclerViewAdapter) mAdapter).addItem(d, n);
            mAdapter.notifyDataSetChanged();
            isFromOnActivityResult = true;
        }
    }

    @Override
    protected void onResume() {
        if (results.size() != 0) {
            results.remove(0);
        }
        super.onResume();
        /*if(isFromOnActivityResult){
            return;
        }*/
        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                //Toast.makeText(MenuDisplayActivity.this, " Clicked on Item " + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MenuDisplayActivity.this, PollutionDetailActivity.class);
                intent.putExtra("pollutionDetail", pollutionData);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
