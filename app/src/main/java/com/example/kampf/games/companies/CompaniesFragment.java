package com.example.kampf.games.companies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.kampf.games.PrefsConst;
import com.example.kampf.games.R;
import com.example.kampf.games.companydetails.CompanyDetailsActivity;
import com.example.kampf.games.network.GbObjectResponse;
import com.example.kampf.games.network.GbObjectsListResponse;
import com.example.kampf.games.network.GiantBombService;
import com.example.kampf.games.network.RestApi;
import com.example.kampf.games.search.CompaniesSearchActivity;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompaniesFragment extends android.support.v4.app.Fragment implements CompaniesAdapter.Callback, Toolbar.OnMenuItemClickListener {

    private static final int TOTAL_COMPANIES_COUNT = 16858;

    private GiantBombService service = RestApi.createService(GiantBombService.class);
    private CompaniesAdapter adapter = new CompaniesAdapter(this);
    private Random random = new Random();
    private RecyclerView rvCompanies;
    private ProgressBar progressBar;
    @Nullable private Call<GbObjectsListResponse> call;
    private int companiesAmount;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_companies, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        companiesAmount = sharedPreferences.getInt(PrefsConst.SETTINGS_COMPANIES_AMOUNT, PrefsConst.SETTINGS_DEFAULT_AMOUNT);
        setupToolbar(view);
        setupRecyclerView(view);
        progressBar = view.findViewById(R.id.progressBar);

        view.findViewById(R.id.fabSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CompaniesSearchActivity.class));
            }
        });

        loadRandomCompanies();

        Call<GbObjectsListResponse> call = service.getCompanies(10, 228);
        Callback<GbObjectsListResponse> callback = new Callback<GbObjectsListResponse>() {
            @Override
            public void onResponse(Call<GbObjectsListResponse> call, Response<GbObjectsListResponse> response) {
                GbObjectsListResponse gbObjectsListResponse = response.body();
                if (gbObjectsListResponse != null) {
                    adapter.addAll(gbObjectsListResponse.getResults());
                }
            }

            @Override
            public void onFailure(Call<GbObjectsListResponse> call, Throwable t) {

            }
        };

        call.enqueue(callback);

    }

    private void loadRandomCompanies() {

        if (call != null && call.isExecuted()) {
            return;
        }

        showLoading();
        int offset = random.nextInt(TOTAL_COMPANIES_COUNT - companiesAmount + 1);
        call = service.getCompanies(companiesAmount, offset);
        //noinspection ConstantConditions
        call.enqueue(new Callback<GbObjectsListResponse>() {
            @Override
            public void onResponse(Call<GbObjectsListResponse> call, Response<GbObjectsListResponse> response) {
                showContent();
                CompaniesFragment.this.call = call.clone();
                GbObjectsListResponse gbObjectsListResponse = response.body();
                if (gbObjectsListResponse != null) {
                    adapter.replaceAll(gbObjectsListResponse.getResults());
                }
            }

            @Override
            public void onFailure(Call<GbObjectsListResponse> call, Throwable t) {
                showContent();
                CompaniesFragment.this.call = call.clone();
                if (!call.isCanceled()) {
                    Toast.makeText(getContext(), R.string.error, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        rvCompanies.setVisibility(View.GONE);
    }

    private void showContent() {
        progressBar.setVisibility(View.GONE);
        rvCompanies.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCompanyClick(GbObjectResponse company) {

        Intent intent = CompanyDetailsActivity.makeIntent(getContext(), company);
        startActivity(intent);

    }

    public void setupRecyclerView(View view) {
        rvCompanies = view.findViewById(R.id.rvCompanies);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvCompanies.setLayoutManager(layoutManager);
        rvCompanies.setAdapter(adapter);
    }

    public void setupToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.companies);
        toolbar.inflateMenu(R.menu.menu_companies);
        toolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        if (item.getItemId() == R.id.refresh) {

            loadRandomCompanies();
            return true;

        }

        return false;
    }
}
