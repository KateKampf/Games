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
import com.example.kampf.games.gamedetails.GameDetailsActivity;
import com.example.kampf.games.games.GamesAdapter;
import com.example.kampf.games.network.GbObjectResponse;
import com.example.kampf.games.network.GbObjectsListResponse;
import com.example.kampf.games.network.GiantBombService;
import com.example.kampf.games.network.RestApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GamesSearchActivity extends AppCompatActivity implements GamesAdapter.Callback, View.OnClickListener {

    private final GiantBombService service = RestApi.createService(GiantBombService.class);
    private Handler handler = new Handler();
    private GamesAdapter adapter = new GamesAdapter(this);
    private RecyclerView rvGames;
    private ProgressBar progressBar;
    @Nullable private Call<GbObjectsListResponse> call;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_games);
        findViewById(R.id.btnBack).setOnClickListener(this);
        progressBar = findViewById(R.id.progressBar);
        rvGames = findViewById(R.id.rvGames);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvGames.setAdapter(adapter);
        rvGames.setLayoutManager(layoutManager);

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

                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        searchGames(s.toString(), 10);
                    }
                }, 400);

            }
        });
    }

    private void searchGames(String query, int limit) {

        if (query.isEmpty()) {

            return;

        }

        showLoading();

        if (call != null) {

            call.cancel();

        }
        call = service.searchGames(query, limit);
        //noinspection ConstantConditions
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

                Toast.makeText(GamesSearchActivity.this, R.string.error, Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void showContent() {

        rvGames.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

    }

    private void showLoading() {

        rvGames.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void onGameClick(GbObjectResponse game) {

        startActivity(GameDetailsActivity.makeIntent(this, game));


    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnBack) {

            onBackPressed();

        }

    }
}
