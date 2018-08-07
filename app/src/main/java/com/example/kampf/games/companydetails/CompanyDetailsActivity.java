package com.example.kampf.games.companydetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.kampf.games.BuildConfig;
import com.example.kampf.games.R;
import com.example.kampf.games.network.GbObjectResponse;
import com.example.kampf.games.network.GbSingleObjectResponse;
import com.example.kampf.games.network.GiantBombService;
import com.example.kampf.games.network.RestApi;

public class CompanyDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_COMPANY_NAME = BuildConfig.APPLICATION_ID + "EXTRA_COMPANY_NAME";
    private static final String EXTRA_COMPANY_DECK = BuildConfig.APPLICATION_ID + "EXTRA_COMPANY_DECK";
    private static final String EXTRA_COMPANY_CITY = BuildConfig.APPLICATION_ID + "EXTRA_COMPANY_CITY";
    private static final String EXTRA_COMPANY_COUNTY = BuildConfig.APPLICATION_ID + "EXTRA_COMPANY_COUNTY";
    private static final String EXTRA_COMPANY_PICTURE_URL = BuildConfig.APPLICATION_ID + "EXTRA_COMPANY_PICTURE_URL";

    private GiantBombService service = RestApi.createService(GiantBombService.class);
    @Nullable private retrofit2.Call<GbSingleObjectResponse> call;


    public static Intent makeIntent(Context context, GbObjectResponse company) {

        return new Intent(context, CompanyDetailsActivity.class)
                .putExtra(EXTRA_COMPANY_NAME, company.getName())
                .putExtra(EXTRA_COMPANY_DECK, company.getDeck())
                .putExtra(EXTRA_COMPANY_CITY, company.getLocationCity())
                .putExtra(EXTRA_COMPANY_COUNTY, company.getLocationCountry())
                .putExtra(EXTRA_COMPANY_PICTURE_URL, company.getImage().getSmallUrl());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);

        Intent intent = getIntent();

        String companyName = intent.getStringExtra(EXTRA_COMPANY_NAME);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(companyName);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            onBackPressed();
            return true;

        }
        return super.onOptionsItemSelected(item);

    }
}


