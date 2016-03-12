package com.example.azuga.pollutionviewer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

public class ShowMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getIntent();
        StationPollutionDetail spd = intent.getParcelableExtra("pollutionDetailList");
        createBarChart(spd);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createBarChart(StationPollutionDetail spd) {
        XYSeries series = new XYSeries("Pollutant AQI last 24 hour");
        int hour = 0;
        if (spd.getCarbonMonoxide() != null) {
            series.add(hour++, Double.parseDouble(spd.getCarbonMonoxide()));
        }
        if (spd.getNitricOxide() != null) {
            series.add(hour++, Double.parseDouble(spd.getNitricOxide()));
        }
        if (spd.getSulphurDioxide() != null) {
            series.add(hour++, Double.parseDouble(spd.getSulphurDioxide()));
        }
        if (spd.getBenzene() != null) {
            series.add(hour++, Double.parseDouble(spd.getBenzene()));
        }
        if (spd.getToulene() != null) {
            series.add(hour++, Double.parseDouble(spd.getToulene()));
        }
        if (spd.getEthylBenzene() != null) {
            series.add(hour++, Double.parseDouble(spd.getEthylBenzene()));
        }


        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(series);

        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setLineWidth(2);
        renderer.setColor(Color.RED);
        // Include low and max value
        renderer.setDisplayBoundingPoints(true);
        // we add point markers
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setPointStrokeWidth(3);

        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.addSeriesRenderer(renderer);
        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent margins
        // Disable Pan on two axis
        mRenderer.setPanEnabled(false, false);
        mRenderer.setZoomButtonsVisible(true);
        mRenderer.setYAxisMax(300);
        mRenderer.setYAxisMin(0);
        mRenderer.setBarSpacing(2);

        GraphicalView chartView = ChartFactory.getBarChartView(ShowMapActivity.this, dataset, mRenderer, BarChart.Type.DEFAULT);

        RelativeLayout chartLyt = (RelativeLayout) findViewById(R.id.chartLyt);
        chartLyt.addView(chartView, 0);
    }
}


