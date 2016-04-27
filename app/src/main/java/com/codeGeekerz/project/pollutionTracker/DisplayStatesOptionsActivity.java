package com.codeGeekerz.project.pollutionTracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.codeGeekerz.project.pollutionTracker.adapter.ExpandableListAdapter;
import com.codeGeekerz.project.pollutionTracker.utils.ApplicationUIUtils;
import com.codeGeekerz.project.pollutionTracker.utils.SessionManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 21-01-2016.
 */
public class DisplayStatesOptionsActivity extends BaseActivity {
    private static final String TAG = "display_states_activity";
    private static final int PICK_STATION_REQUEST = 0;
    private final String TAG_ID = "stateId";
    private final String TAG_NAME = "stateName";
    ExpandableListAdapter listAdapter;
    ExpandableListView states_List;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    HashMap<String, List<String>> listStationChild;
    HashMap<String, String> station_names_map;
    private SessionManager session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_states_names);
        session = new SessionManager(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (ApplicationUIUtils.isNetworkAvailable(this)) {
            showProgressBar(this, "Downloading Content...");
            callForStateList();
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

    private void callForStateList() {
        APIService apiService = APIHelper.getApiService();
        Call<ArrayList<AllStation>> call = apiService.loadAllStates(session.getToken());
        call.enqueue(new Callback<ArrayList<AllStation>>() {
            @Override
            public void onResponse(Response<ArrayList<AllStation>> response) {
                if (response.isSuccess() && !response.body().isEmpty()) {
                    hideProgressDialog();
                    Log.i(TAG, "Response is not null");
                    listDataHeader = new ArrayList<>();
                    listDataChild = new HashMap<>();
                    listStationChild = new HashMap<>();
                    station_names_map = new HashMap<>();
                    List<String> list_cities = new ArrayList<>();
                    List<String> station_Names = new ArrayList<>();
                    ArrayList<AllStation> list = response.body();
                    Iterator<AllStation> itr = list.iterator();
                    while (itr.hasNext()) {
                        AllStation stateFullList = itr.next();
                        if (!listDataHeader.contains(stateFullList.getStateName())) {
                            listDataHeader.add(stateFullList.getStateName());
                            list_cities = new ArrayList<>();
                            station_Names = new ArrayList<>();
                            list_cities.add(stateFullList.getCityName());
                            listDataChild.put(stateFullList.getStateName(), list_cities);
                        } else {
                            listDataChild.put(stateFullList.getStateName(), list_cities);
                        }
                        if (!list_cities.contains(stateFullList.getCityName())) {
                            list_cities.add(stateFullList.getCityName());
                            station_Names = new ArrayList<>();
                            station_Names.add(stateFullList.getStationName());
                            station_names_map.put(stateFullList.getStationName(), stateFullList.getFullStationName());
                            listStationChild.put(stateFullList.getCityName(), station_Names);
                        } else {
                            listStationChild.put(stateFullList.getCityName(), station_Names);
                        }
                        if (!station_Names.contains(stateFullList.getStationName())) {
                            station_Names.add(stateFullList.getStationName());
                            station_names_map.put(stateFullList.getStationName(), stateFullList.getFullStationName());
                        }
                    }
                    setUpStateList(listDataHeader, listDataChild, listStationChild);
                }else{
                    try {
                        Log.i(TAG, "error is :"+response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                hideProgressDialog();
                Toast.makeText(DisplayStatesOptionsActivity.this, "OOPS! Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpStateList(final List<String> listDataHeader, final HashMap<String, List<String>> listDataChild, final HashMap<String, List<String>> listStationChild) {
        // get the listview
        states_List = (ExpandableListView) findViewById(R.id.list_states);
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        states_List.setAdapter(listAdapter);
        states_List.setTextFilterEnabled(true);
        states_List.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long id) {
                /*Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();*/
                Intent intent = new Intent(DisplayStatesOptionsActivity.this, StationDisplayActivity.class);
                List<String> stations = listStationChild.get(listDataChild.get(
                        listDataHeader.get(groupPosition)).get(
                        childPosition));
                intent.putStringArrayListExtra("stations", (ArrayList<String>) stations);
                intent.putExtra("station_names_map", station_names_map);
                startActivityForResult(intent, PICK_STATION_REQUEST);
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_STATION_REQUEST) {
            Intent output = new Intent();
            output.putExtra("stationName", resultData.getStringExtra("stationName"));
            output.putExtra("stationFullName", resultData.getStringExtra("stationFullName"));
            setResult(RESULT_OK, output);
            finish();
        }
    }
}
