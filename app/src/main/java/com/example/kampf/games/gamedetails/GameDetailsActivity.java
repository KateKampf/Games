package com.example.kampf.games.gamedetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kampf.games.BuildConfig;
import com.example.kampf.games.R;
import com.example.kampf.games.network.GbObjectResponse;
import com.example.kampf.games.network.GbSingleObjectResponse;
import com.example.kampf.games.network.GiantBombService;
import com.example.kampf.games.network.RestApi;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_GAME_NAME = BuildConfig.APPLICATION_ID + "EXTRA_GAME_NAME";
    private static final String EXTRA_GAME_DECK = BuildConfig.APPLICATION_ID + "EXTRA_GAME_DECK";
    private static final String EXTRA_GAME_GUID = BuildConfig.APPLICATION_ID + "EXTRA_GAME_GUID";
    private static final String EXTRA_GAME_PICTURE_URL = BuildConfig.APPLICATION_ID + "EXTRA_GAME_PICTURE_URL";

    private GiantBombService service = RestApi.createService(GiantBombService.class);
    private ProgressBar progressBar;
    private TextView tvDescription;
    private ViewGroup vgContent;
    @Nullable private Call<GbSingleObjectResponse> call;

    public static Intent makeIntent(Context context, GbObjectResponse game) {

        return new Intent(context, GameDetailsActivity.class)
                .putExtra(GameDetailsActivity.EXTRA_GAME_NAME, game.getName())
                .putExtra(GameDetailsActivity.EXTRA_GAME_DECK, game.getDeck())
                .putExtra(GameDetailsActivity.EXTRA_GAME_GUID, game.getGuid())
                .putExtra(GameDetailsActivity.EXTRA_GAME_PICTURE_URL, game.getImage().getSmallUrl());

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);

        tvDescription = findViewById(R.id.tvDescription);
        vgContent = findViewById(R.id.vgContent);
        progressBar = findViewById(R.id.progressBar);

        Intent intent = getIntent();

        String gameName = intent.getStringExtra(EXTRA_GAME_NAME);
        String gameDeck = intent.getStringExtra(EXTRA_GAME_DECK);
        String guid = intent.getStringExtra(EXTRA_GAME_GUID);
        String gamePicUrl = intent.getStringExtra(EXTRA_GAME_PICTURE_URL);

        ImageView ivPicture = findViewById(R.id.ivPicture);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        TextView tvDeck = findViewById(R.id.tvDeck);

        tvDeck.setText(gameDeck == null ? "No deck" : gameDeck);
        toolbar.setTitle(gameName);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Picasso.get().load(gamePicUrl).into(ivPicture);

        loadGameDetails(guid);

    }

    private void loadGameDetails(String guid) {

        showLoading();
        call = service.getGameDetails(guid);
        call.enqueue(new Callback<GbSingleObjectResponse>() {
            @Override
            public void onResponse(Call<GbSingleObjectResponse> call, Response<GbSingleObjectResponse> response) {
                showContent();
                GbSingleObjectResponse gbSingleObjectResponse = response.body();
                if (gbSingleObjectResponse != null) {

                    String description = gbSingleObjectResponse.getResults().getDescription();
                    CharSequence text = description == null ? getString(R.string.no_description) : Html.fromHtml(description);
                    tvDescription.setText(text);


                }
            }

            @Override
            public void onFailure(Call<GbSingleObjectResponse> call, Throwable t) {

                if (!call.isCanceled()) {

                    showContent();
                    Toast.makeText(GameDetailsActivity.this, R.string.error, Toast.LENGTH_SHORT).show();

                }

            }
        });


    }

    private void showContent() {

        vgContent.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);


    }

    private void showLoading() {

        vgContent.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            onBackPressed();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        assert call != null;
        call.cancel();
        super.onDestroy();
    }
}
