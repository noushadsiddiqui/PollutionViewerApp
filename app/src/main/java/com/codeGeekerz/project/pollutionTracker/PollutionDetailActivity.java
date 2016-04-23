package com.codeGeekerz.project.pollutionTracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.codeGeekerz.project.pollutionTracker.utils.ApplicationUIUtils;

import pl.pawelkleczkowski.customgauge.CustomGauge;

public class PollutionDetailActivity extends AppCompatActivity {
    LocationManager locManager = null;
    TableLayout stk;
    private StationPollutionDetail spd;
    private CustomGauge gauge;

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
                gauge = (CustomGauge) findViewById(R.id.gauge);
                TextView t1 = (TextView) findViewById(R.id.gaugeValue);
                TextView t2 = (TextView) findViewById(R.id.aqi_text);
                TextView t3 = (TextView) findViewById(R.id.pollution_status);
                TextView t4 = (TextView) findViewById(R.id.aqi_health);
                int roundedAQI = ApplicationUIUtils.roundedAQI(spd.getAqi());
                gauge.setValue(roundedAQI > 500 ? 500 : roundedAQI);
                t3.setText(ApplicationUIUtils.getPollutionStatus(PollutionDetailActivity.this, spd.getAqi()));
                t1.setText(String.valueOf(roundedAQI));
                t2.setText("AQI");
                t4.setText("HEALTH IMPACT : ");
                t4.setTypeface(null, Typeface.BOLD);
                t4.append(ApplicationUIUtils.getPollutionText(PollutionDetailActivity.this, spd.getAqi()));
                //t4.setTextSize(getResources().getDimension(R.dimen.textsize_rows));
                init(spd);
            }
        }
    }

    private void init(StationPollutionDetail spd) {
        stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        tv0.setText(" Pollutant ");
        tv0.setTextSize(getResources().getDimension(R.dimen.textsize));
        tv0.setTypeface(null, Typeface.BOLD);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" Current Value ");
        tv1.setTextSize(getResources().getDimension(R.dimen.textsize));
        tv1.setTypeface(null, Typeface.BOLD);
        tbrow0.addView(tv1);
        /*TextView tv2 = new TextView(this);
        tv2.setText(" From Last Hour");
        tv2.setTextSize(getResources().getDimension(R.dimen.textsize));
        tv2.setTypeface(null, Typeface.BOLD);
        tbrow0.addView(tv2);*/
        stk.addView(tbrow0);
        if (spd.getpM25() != null) {
            createRow("PM25", ApplicationUIUtils.roundUptoTwoDecimalUnits(spd.getpM25())+ApplicationUIUtils.getPollutantUnits("PM25"));
        }
        if (spd.getpM10() != null) {
            createRow("PM10", ApplicationUIUtils.roundUptoTwoDecimalUnits(spd.getpM10())+ApplicationUIUtils.getPollutantUnits("PM10"));
        }
        if (spd.getNitrogenDioxide() != null) {
            createRow("NitrogenDioxide", ApplicationUIUtils.roundUptoTwoDecimalUnits(spd.getNitrogenDioxide())+ApplicationUIUtils.getPollutantUnits("NitrogenDioxide"));
        }
        if (spd.getOzone() != null) {
            createRow("Ozone", ApplicationUIUtils.roundUptoTwoDecimalUnits(spd.getOzone())+ApplicationUIUtils.getPollutantUnits("Ozone"));
        }
        if (spd.getCarbonMonoxide() != null) {
            createRow("CarbonMonoxide", ApplicationUIUtils.roundUptoTwoDecimalUnits(spd.getCarbonMonoxide())+ApplicationUIUtils.getPollutantUnits("CarbonMonoxide"));
        }
        if (spd.getSulphurDioxide() != null) {
            createRow("Sulphur Dioxide", ApplicationUIUtils.roundUptoTwoDecimalUnits(spd.getSulphurDioxide())+ApplicationUIUtils.getPollutantUnits("Sulphur Dioxide"));
        }
        if (spd.getAmmonia() != null) {
            createRow("Ammonia", ApplicationUIUtils.roundUptoTwoDecimalUnits(spd.getAmmonia())+ApplicationUIUtils.getPollutantUnits("Ammonia"));
        }
        if (spd.getNitricOxide() != null) {
            createRow("NitricOxide", ApplicationUIUtils.roundUptoTwoDecimalUnits(spd.getNitricOxide())+ApplicationUIUtils.getPollutantUnits("NitricOxide"));
        }
        if (spd.getOxidesOfNitrogen() != null) {
            createRow("oxidesOfNitrogen", ApplicationUIUtils.roundUptoTwoDecimalUnits(spd.getOxidesOfNitrogen())+ApplicationUIUtils.getPollutantUnits("oxidesOfNitrogen"));
        }
        if (spd.getBenzene() != null) {
            createRow("Benzene", ApplicationUIUtils.roundUptoTwoDecimalUnits(spd.getBenzene())+ApplicationUIUtils.getPollutantUnits("Benzene"));
        }
        if (spd.getToulene() != null) {
            createRow("Toulene", ApplicationUIUtils.roundUptoTwoDecimalUnits(spd.getToulene())+ApplicationUIUtils.getPollutantUnits("Toulene"));
        }
        if (spd.getEthylBenzene() != null) {
            createRow("EthylBenzene", ApplicationUIUtils.roundUptoTwoDecimalUnits(spd.getEthylBenzene())+ApplicationUIUtils.getPollutantUnits("EthylBenzene"));
        }
        if (spd.getNonMethaneHydrocarbon() != null) {
            createRow("nonMethaneHydrocarbon", ApplicationUIUtils.roundUptoTwoDecimalUnits(spd.getNonMethaneHydrocarbon())+ApplicationUIUtils.getPollutantUnits("nonMethaneHydrocarbon"));
        }
        if (spd.getPXylene() != null) {
            createRow("Pxylene", ApplicationUIUtils.roundUptoTwoDecimalUnits(spd.getPXylene())+ApplicationUIUtils.getPollutantUnits("Pxylene"));
        }
    }

    private void createRow(String v1, String v2) {
        TableRow tbrow = new TableRow(this);
        TextView t1v = new TextView(this);
        t1v.setText(v1);
        t1v.setTextSize(getResources().getDimension(R.dimen.textsize_rows));
        t1v.setGravity(Gravity.CENTER);
        tbrow.addView(t1v);
        TextView t2v = new TextView(this);
        t2v.setText(v2);
        t2v.setTextSize(getResources().getDimension(R.dimen.textsize_rows));
        t2v.setGravity(Gravity.CENTER);
        tbrow.addView(t2v);
        /*ImageView img = new ImageView(this);
        //add comparison logic here
        img.setImageResource(R.drawable.arrowUp);
        tbrow.addView(img);*/
        stk.addView(tbrow);
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

   /* private void createCardView(String componentName, String componentValue, String lastTime) {
        String text1 = componentName + ":";
        String text2 = componentValue + "\ntime :" + lastTime;
        DataObject d = new DataObject(text1, text2, R.color.colorDarkGreen);
        results.add(d);
        mAdapter.notifyDataSetChanged();
    }*/

    @Override
    protected void onResume() {
        super.onResume();
    }

}
