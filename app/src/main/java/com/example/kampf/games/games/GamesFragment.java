package com.example.kampf.games.games;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kampf.games.R;
import com.example.kampf.games.network.GbObjectsListResponse;
import com.example.kampf.games.network.GiantBombService;
import com.example.kampf.games.network.RestApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GamesFragment extends Fragment implements Toolbar.OnMenuItemClickListener {

    private static final String TAG = "33__";

    private RecyclerView rvGames;
    private GamesAdapter adapter = new GamesAdapter();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_games, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        setupToolbar(view);
        setupRecyclerView(view);

        GiantBombService service = RestApi.createService(GiantBombService.class);

        Call<GbObjectsListResponse> call = service.getGames(10, 228);

        Callback<GbObjectsListResponse> callback = new Callback<GbObjectsListResponse>() {
            @Override
            public void onResponse(Call<GbObjectsListResponse> call, Response<GbObjectsListResponse> response) {
                Log.d(TAG, "onResponse");
                GbObjectsListResponse gbObjectsListResponse = response.body();
                if (gbObjectsListResponse != null) {

                    adapter.addAll(gbObjectsListResponse.getResults());

                }
            }

            @Override
            public void onFailure(Call<GbObjectsListResponse> call, Throwable t) {
                Log.d(TAG, "onFailure");
            }
        };

        call.enqueue(callback);



    }

    private void setupRecyclerView(View view) {

        rvGames = view.findViewById(R.id.rvGames);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvGames.setLayoutManager(layoutManager);
        rvGames.setAdapter(adapter);
    }

    private void setupToolbar(View view) {

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.games);
        toolbar.inflateMenu(R.menu.menu_games);
        toolbar.setOnMenuItemClickListener(this);

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        if (item.getItemId() == R.id.refresh) {

            Toast.makeText(getContext(), R.string.refresh, Toast.LENGTH_SHORT).show();
            return true;

        }

        return false;
    }
}
