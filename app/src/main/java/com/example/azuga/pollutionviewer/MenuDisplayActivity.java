package com.example.azuga.pollutionviewer;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.azuga.pollutionviewer.adapter.CityListAdapter;
import com.example.azuga.pollutionviewer.utils.ApplicationUIUtils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuDisplayActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (ApplicationUIUtils.isNetworkAvailable(this)) {
            setUpListView();
        } else {
            Toast.makeText(this, "OOPS! Check your internet connection", Toast.LENGTH_LONG).show();
        }
    }

    private void setUpListView() {
        ListView cities_List = (ListView) findViewById(R.id.list_cities);
        final String[] stationNames = getResources().getStringArray(R.array.StationNames);
        cities_List.setAdapter(new CityListAdapter(this, stationNames));
        cities_List.setTextFilterEnabled(true);
        cities_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                getPollutionData(stationNames[position]);
            }
        });
    }

    private void getPollutionData(final String cityName) {
        if (!ApplicationUIUtils.isNetworkAvailable(this)) {
            Toast.makeText(this, "OOPS! Check your internet connection", Toast.LENGTH_LONG).show();
            return;
        }
        showProgressBar(this, "Downloading Content...");
        APIService apiService = APIHelper.getApiService();
        Call<StationPollutionDetail> call = apiService.loadPollutionDetail("PM25", cityName);
//        try {
//            Response<StationPollutionDetail> response = call.execute();
//            if (response != null && response.isSuccess()) {
//                StationPollutionDetail cityPollutionDetail = response.body();
//                Toast.makeText(MenuDisplayActivity.this, "PM25 Level : " + cityPollutionDetail.getPollutionLevel(), Toast.LENGTH_SHORT).show();
//            }
//        } catch (IOException e) {
//            Toast.makeText(MenuDisplayActivity.this, "OOPS", Toast.LENGTH_SHORT).show();
//        }
        call.enqueue(new Callback<StationPollutionDetail>() {
            @Override
            public void onResponse(Response<StationPollutionDetail> response) {
                hideProgressDialog();
                if (response.isSuccess()) {
                    StationPollutionDetail cityPollutionDetail = response.body();
                    Toast.makeText(MenuDisplayActivity.this, "PM25 Level : " + cityPollutionDetail.getPollutionLevel(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                hideProgressDialog();
                Toast.makeText(MenuDisplayActivity.this, "OOPS", Toast.LENGTH_SHORT).show();
            }
        });
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
}
