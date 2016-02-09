package com.example.azuga.pollutionviewer;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.example.azuga.pollutionviewer.adapter.MyRecyclerViewAdapter;
import com.example.azuga.pollutionviewer.utils.ApplicationUIUtils;
import com.example.azuga.pollutionviewer.utils.DataObject;

import java.util.ArrayList;

public class PollutionDetailActivity extends AppCompatActivity {
    private static boolean first = true;
    private final ArrayList<DataObject> results = new ArrayList<>();
    LocationManager locManager = null;
    private RecyclerView mRecyclerView;
    private TextView mRecyclerView_expanded;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private StationPollutionDetail spd;
    private int descriptionViewFullHeight;

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
        spd = intent.getParcelableExtra("pollutionDetail");
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
                if (spd.getEthylBenzene() != null) {
                    createCardView("Ethyl Benzene", spd.getEthylBenzene(), spd.getTimestamp());
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
                intent.putExtra("pollutionDetailList", spd);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display, menu);
        menu.findItem(R.id.action_settings).setVisible(false);
        return true;
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
        mRecyclerView_expanded = (TextView) findViewById(R.id.pollution_detail_expanded);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MyRecyclerViewAdapter(null);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver
                .OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mRecyclerView_expanded.getViewTreeObserver().removeOnPreDrawListener(this);
                // save the full height
                descriptionViewFullHeight = mRecyclerView.getHeight() + mRecyclerView_expanded.getHeight();

                // initially changing the height to min height
                ViewGroup.LayoutParams layoutParams = mRecyclerView.getLayoutParams();
                layoutParams.height = (int) getResources().getDimension(R.dimen.card_min_height);
                mRecyclerView.setLayoutParams(layoutParams);
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                /*RecyclerView.ViewHolder childview = mRecyclerView.findViewHolderForAdapterPosition(position);
                View child = childview.itemView.getRootView();*/
                toggleCardHeight();
            }
        });
    }

    private void toggleCardHeight() {

        int descriptionViewMinHeight = (int) getResources().getDimension(R.dimen.card_min_height);
        if (mRecyclerView.getHeight() == descriptionViewMinHeight) {
            // expand
            ValueAnimator anim = ValueAnimator.ofInt(mRecyclerView.getMeasuredHeightAndState(),
                    descriptionViewFullHeight);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (Integer) valueAnimator.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = mRecyclerView.getLayoutParams();
                    layoutParams.height = val;
                    mRecyclerView.setLayoutParams(layoutParams);
                }
            });
            anim.start();
        } else {
            // collapse
            ValueAnimator anim = ValueAnimator.ofInt(mRecyclerView.getMeasuredHeightAndState(),
                    descriptionViewMinHeight);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (Integer) valueAnimator.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = mRecyclerView.getLayoutParams();
                    layoutParams.height = val;
                    mRecyclerView.setLayoutParams(layoutParams);
                }
            });
            anim.start();
        }
    }

}
