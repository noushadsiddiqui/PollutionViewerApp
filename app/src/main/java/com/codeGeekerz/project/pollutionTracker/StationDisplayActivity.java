package com.codeGeekerz.project.pollutionTracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.codeGeekerz.project.pollutionTracker.utils.ApplicationUIUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class StationDisplayActivity extends BaseActivity {
    private HashMap<String, String> station_names_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_stations_names);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ArrayList<String> stations = getIntent().getStringArrayListExtra("stations");
        station_names_map = (HashMap<String, String>) getIntent().getSerializableExtra("station_names_map");
        if (!stations.isEmpty() && ApplicationUIUtils.isNetworkAvailable(this)) {
            showProgressBar(this, "Downloading Content...");
            setCityListView(stations);
        } else {
            Toast.makeText(this, "OOPS! Check your internet connection", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setCityListView(ArrayList<String> stations) {
        ListView lstCities = (ListView) findViewById(R.id.list_stations);
        ListAdapter adapter = new ArrayAdapter<>(this, R.layout.list_station_items, R.id.station_id, stations);
        hideProgressDialog();
        lstCities.setAdapter(adapter);
        lstCities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(StationDisplayActivity.this, MenuDisplayActivity.class);
                String stationName = (String) parent.getItemAtPosition(position);
                intent.putExtra("stationName", stationName);
                intent.putExtra("stationFullName", station_names_map.get(stationName));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
