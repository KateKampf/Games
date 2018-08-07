package com.example.kampf.games.search;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.kampf.games.R;
import com.example.kampf.games.companies.CompaniesAdapter;
import com.example.kampf.games.companydetails.CompanyDetailsActivity;
import com.example.kampf.games.network.GbObjectResponse;
import com.example.kampf.games.network.GbObjectsListResponse;
import com.example.kampf.games.network.GiantBombService;
import com.example.kampf.games.network.RestApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompaniesSearchActivity extends AppCompatActivity implements CompaniesAdapter.Callback, View.OnClickListener {

    private final GiantBombService service = RestApi.createService(GiantBombService.class);
    private Handler handler = new Handler();
    private CompaniesAdapter adapter = new CompaniesAdapter(this);
    private RecyclerView rvCompanies;
    private ProgressBar progressBar;
    @Nullable private Call<GbObjectsListResponse> call;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_companies);
        findViewById(R.id.btnBack).setOnClickListener(this);
        progressBar = findViewById(R.id.progressBar);
        rvCompanies = findViewById(R.id.rvCompanies);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvCompanies.setAdapter(adapter);
        rvCompanies.setLayoutManager(layoutManager);

        EditText etSearch = findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(final Editable s) {
                handler.removeCallbacksAndMessages(this);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        searchCompanies(s.toString(), 10);
                    }
                }, 400);

            }
        });

    }

    public void searchCompanies(String query, int limit) {
        if (query.isEmpty()) {
            return;
        }

        showLoading();

        if (call != null) {
            call.cancel();
        }

        call = service.searchCompanies(query, limit);
        call.enqueue(new Callback<GbObjectsListResponse>() {
            @Override
            public void onResponse(Call<GbObjectsListResponse> call, Response<GbObjectsListResponse> response) {
                showContent();
                if (response.body() != null) {
                    //noinspection ConstantConditions
                    adapter.replaceAll(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<GbObjectsListResponse> call, Throwable t) {
                showContent();
                Toast.makeText(CompaniesSearchActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showContent() {
        progressBar.setVisibility(View.GONE);
        rvCompanies.setVisibility(View.VISIBLE);
    }

    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        rvCompanies.setVisibility(View.GONE);
    }

    @Override
    public void onCompanyClick(GbObjectResponse company) {
        startActivity(CompanyDetailsActivity.makeIntent(this, company));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnBack) {
            onBackPressed();
        }

    }
}
