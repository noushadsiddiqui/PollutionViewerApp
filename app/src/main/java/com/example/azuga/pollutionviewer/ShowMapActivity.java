package com.example.azuga.pollutionviewer;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.example.azuga.pollutionviewer.utils.ApplicationUIUtils;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

public class ShowMapActivity extends AppCompatActivity {
    private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
    private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
    private GraphicalView mChartView;

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
        if (spd != null) {
            createBarChart(spd);
        } else {
            finish();
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

    private void createBarChart(StationPollutionDetail spd) {
        int hour = 0;
        String[] mLabel = new String[8];
        if (spd.getPm10AQI() != null) {
            XYSeries series = new XYSeries("PM10 AQI");
            mLabel[hour] = "PM10";
            series.add(hour++, ApplicationUIUtils.roundedAQI(spd.getPm10AQI()));
            mDataset.addSeries(series);
            XYSeriesRenderer renderer = new XYSeriesRenderer();
            renderer.setLineWidth(2);
            renderer.setColor(ApplicationUIUtils.getCardBackgroundColor(this, spd.getPm10AQI()));
            renderer.setFillPoints(true);
            renderer.setDisplayChartValues(true);
            renderer.setChartValuesTextSize(30);
            mRenderer.addSeriesRenderer(renderer);
        }
        if (spd.getPm25AQI() != null) {
            XYSeries series = new XYSeries("PM25 AQI");
            mLabel[hour] = "PM25";
            series.add(hour++, ApplicationUIUtils.roundedAQI(spd.getPm25AQI()));
            mDataset.addSeries(series);
            XYSeriesRenderer renderer = new XYSeriesRenderer();
            renderer.setLineWidth(2);
            renderer.setColor(ApplicationUIUtils.getCardBackgroundColor(this, spd.getPm25AQI()));
            renderer.setDisplayChartValues(true);
            renderer.setChartValuesTextSize(30);
            renderer.setFillPoints(true);
            mRenderer.addSeriesRenderer(renderer);
        }
        if (spd.getNitrogenDioxideAQI() != null) {
            XYSeries series = new XYSeries("NitrogenDioxide AQI");
            mLabel[hour] = "NO2";
            series.add(hour++, ApplicationUIUtils.roundedAQI(spd.getNitrogenDioxideAQI()));
            mDataset.addSeries(series);
            XYSeriesRenderer renderer = new XYSeriesRenderer();
            renderer.setLineWidth(2);
            renderer.setColor(ApplicationUIUtils.getCardBackgroundColor(this, spd.getNitrogenDioxideAQI()));
            renderer.setDisplayChartValues(true);
            renderer.setChartValuesTextSize(30);
            renderer.setFillPoints(true);
            mRenderer.addSeriesRenderer(renderer);
        }
        if (spd.getOzoneAQI() != null) {
            XYSeries series = new XYSeries("Ozone AQI");
            mLabel[hour] = "03";
            series.add(hour++, ApplicationUIUtils.roundedAQI(spd.getOzoneAQI()));
            mDataset.addSeries(series);
            XYSeriesRenderer renderer = new XYSeriesRenderer();
            renderer.setLineWidth(2);
            renderer.setColor(ApplicationUIUtils.getCardBackgroundColor(this, spd.getOzoneAQI()));
            renderer.setDisplayChartValues(true);
            renderer.setChartValuesTextSize(30);
            renderer.setFillPoints(true);
            mRenderer.addSeriesRenderer(renderer);
        }
        if (spd.getCarbonMonoxideAQI() != null) {
            XYSeries series = new XYSeries("CarbonMonoxide AQI");
            mLabel[hour] = "CO";
            series.add(hour++, ApplicationUIUtils.roundedAQI(spd.getCarbonMonoxideAQI()));
            mDataset.addSeries(series);
            XYSeriesRenderer renderer = new XYSeriesRenderer();
            renderer.setLineWidth(2);
            renderer.setColor(ApplicationUIUtils.getCardBackgroundColor(this, spd.getCarbonMonoxideAQI()));
            // Include low and max value
            renderer.setDisplayChartValues(true);
            renderer.setChartValuesTextSize(30);
            renderer.setFillPoints(true);
            mRenderer.addSeriesRenderer(renderer);
        }
        if (spd.getSulfurDioxideAQI() != null) {
            XYSeries series = new XYSeries("SulfurDioxide AQI");
            mLabel[hour] = "SO2";
            series.add(hour++, ApplicationUIUtils.roundedAQI(spd.getSulfurDioxideAQI()));
            mDataset.addSeries(series);
            XYSeriesRenderer renderer = new XYSeriesRenderer();
            renderer.setLineWidth(2);
            renderer.setColor(ApplicationUIUtils.getCardBackgroundColor(this, spd.getSulfurDioxideAQI()));
            renderer.setDisplayChartValues(true);
            renderer.setChartValuesTextSize(30);
            renderer.setFillPoints(true);
            mRenderer.addSeriesRenderer(renderer);
        }
        if (spd.getAmmoniaAQI() != null) {
            XYSeries series = new XYSeries("Ammonia AQI");
            mLabel[hour] = "NH3";
            series.add(hour++, ApplicationUIUtils.roundedAQI(spd.getAmmoniaAQI()));
            mDataset.addSeries(series);
            XYSeriesRenderer renderer = new XYSeriesRenderer();
            renderer.setLineWidth(2);
            renderer.setColor(ApplicationUIUtils.getCardBackgroundColor(this, spd.getAmmoniaAQI()));
            renderer.setDisplayChartValues(true);
            renderer.setChartValuesTextSize(30);
            renderer.setFillPoints(true);
            mRenderer.addSeriesRenderer(renderer);
        }
        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent margins
        // Disable Pan on two axis
        mRenderer.setPanEnabled(true, false);
        mRenderer.setZoomEnabled(false, false);
        mRenderer.setExternalZoomEnabled(false);
        mRenderer.setYAxisMin(0);
        mRenderer.setYAxisMax(500);
        mRenderer.setYLabels(6);
        mRenderer.setYLabelsAlign(Paint.Align.RIGHT);
        mRenderer.setXLabels(0);
        mRenderer.setXAxisMin(-3.3);
        mRenderer.setXAxisMax(8);
        mRenderer.setYLabelsAlign(Paint.Align.RIGHT);
        //mRenderer.setXTitle("Pollutants");
        mRenderer.setYTitle("AQI Values");
        mRenderer.setShowGridY(false);
        mRenderer.setShowGridX(false);
        mRenderer.setFitLegend(true);
        mRenderer.setBarWidth(50);
        mRenderer.setAxisTitleTextSize(20);
        mRenderer.setChartTitleTextSize(20);
        mRenderer.setLabelsTextSize(20);
        mRenderer.setLegendTextSize(20);
        //commenting because not required : May be in future
        /*for (int i = 0; i < hour; i++) {
            mRenderer.addXTextLabel(i, mLabel[i]);
        }*/
        GraphicalView chartView = ChartFactory.getBarChartView(ShowMapActivity.this, mDataset, mRenderer, BarChart.Type.DEFAULT);

        RelativeLayout chartLyt = (RelativeLayout) findViewById(R.id.chartLyt);
        chartLyt.addView(chartView, 0);
    }
}


