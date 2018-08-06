package com.example.kampf.games.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.kampf.games.FavoriteFragment;
import com.example.kampf.games.R;
import com.example.kampf.games.companies.CompaniesFragment;
import com.example.kampf.games.games.GamesFragment;
import com.example.kampf.games.settings.SettingsFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {

            GamesFragment gamesFragment = new GamesFragment();

            replaceFragment(gamesFragment);

        }

        gsonSandbox();

    }

    private void gsonSandbox() {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == bottomNavigationView.getSelectedItemId()) {
            return true;
        }

        final Fragment fragment;

        switch (item.getItemId()) {

            case R.id.nav_games:

                fragment = new GamesFragment();

                break;

            case R.id.nav_companies:

                fragment = new CompaniesFragment();

                break;

            case R.id.nav_favorite:

                fragment = new FavoriteFragment();

                break;

            case R.id.nav_settings:

                fragment = new SettingsFragment();

                break;

            default:
                fragment = new Fragment();

                break;

        }

        replaceFragment(fragment);
        return true;
    }

    private void replaceFragment(android.support.v4.app.Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();

    }

}

