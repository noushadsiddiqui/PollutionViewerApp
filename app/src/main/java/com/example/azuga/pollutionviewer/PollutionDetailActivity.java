package com.example.azuga.pollutionviewer;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.azuga.pollutionviewer.adapter.MyRecyclerViewAdapter;
import com.example.azuga.pollutionviewer.utils.ApplicationUIUtils;
import com.example.azuga.pollutionviewer.utils.DataObject;

import java.util.ArrayList;

public class PollutionDetailActivity extends AppCompatActivity {
    private static final ArrayList<DataObject> results = new ArrayList<DataObject>();
    LocationManager locManager = null;
    private static boolean first = true;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private StationPollutionDetail spd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_pollution);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getIntent();
        StationPollutionDetail spd = intent.getParcelableExtra("pollutionDetail");
        if (spd != null) {
            locManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            if (ApplicationUIUtils.displayNetworkStatus(locManager) || ApplicationUIUtils.displayGPSStatus(locManager)) {
                setRecyclerView();
                if (spd.getCarbonMonoxide() != null) {
                    createCardView("CarbonMonoxide", spd.getCarbonMonoxide(), spd.getTimestamp());
                }
                if (spd.getNitricOxide() != null) {
                    createCardView("Nitric Oxide", spd.getNitricOxide(), spd.getTimestamp());
                }
                if (spd.getSulphurDioxide() != null) {
                    createCardView("Sulphur Dioxide", spd.getSulphurDioxide(), spd.getTimestamp());
                }
                if (spd.getBenzene() != null) {
                    createCardView("Benzene", spd.getBenzene(), spd.getTimestamp());
                }
                if (spd.getToulene() != null) {
                    createCardView("Toulene", spd.getToulene(), spd.getTimestamp());
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_map:
                Intent intent = new Intent(PollutionDetailActivity.this, ShowMapActivity.class);
                intent.putExtra("pollutionDetail", spd);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createCardView(String componentName, String componentValue, String lastTime) {
        String text1 = componentName + ":";
        String text2 = componentValue + "\ntime :" + lastTime;
        DataObject d = new DataObject(text1, text2);
        results.add(d);
        if (first) {
            mRecyclerView.swapAdapter(new MyRecyclerViewAdapter(results), false);
            first = false;
        }
        mAdapter.notifyDataSetChanged();
    }

    private void setRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_detail);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(false);
        mAdapter = new MyRecyclerViewAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
    }

}
