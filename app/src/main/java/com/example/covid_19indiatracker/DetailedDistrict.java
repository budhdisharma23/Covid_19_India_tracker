package com.example.covid_19indiatracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailedDistrict extends AppCompatActivity {


    private int positionDistrict;
    TextView tvDistrict, tvCases, tvRecovered, tvCritical, tvActive, tvTodayCases, tvTotalDeaths, tvTodayDeaths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_district);

        Intent intent = getIntent();
        positionDistrict = intent.getIntExtra("position", 0);


        getSupportActionBar().setTitle("Details of " + DistrictActivity.districtModelsList.get(positionDistrict).getDistrict());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        tvDistrict = findViewById(R.id.tvDist);
        tvCases = findViewById(R.id.tvCases);
        tvRecovered = findViewById(R.id.tvRecovered);
        tvCritical = findViewById(R.id.tvTRecover);
        tvActive = findViewById(R.id.tvActive);
        tvTodayCases = findViewById(R.id.tvTodayCases);
        tvTotalDeaths = findViewById(R.id.tvDeaths);
        tvTodayDeaths = findViewById(R.id.tvTodayDeaths);

        tvDistrict.setText(DistrictActivity.districtModelsList.get(positionDistrict).getDistrict());
        tvCases.setText(DistrictActivity.districtModelsList.get(positionDistrict).getCases());
        tvRecovered.setText(DistrictActivity.districtModelsList.get(positionDistrict).getRecovered());
        tvCritical.setText(DistrictActivity.districtModelsList.get(positionDistrict).gettRecover());
        tvActive.setText(DistrictActivity.districtModelsList.get(positionDistrict).getActive());
        tvTodayCases.setText(DistrictActivity.districtModelsList.get(positionDistrict).gettCase());
        tvTotalDeaths.setText(DistrictActivity.districtModelsList.get(positionDistrict).getDeaths());
        tvTodayDeaths.setText(DistrictActivity.districtModelsList.get(positionDistrict).gettDeath());


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
