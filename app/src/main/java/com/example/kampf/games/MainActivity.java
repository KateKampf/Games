package com.example.kampf.games;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == bottomNavigationView.getSelectedItemId()) {

            return true;

        }

        String message = "";


        switch (item.getItemId()) {

            case R.id.nav_games:

                message = "Games";
                break;

            case R.id.nav_companies:

                message = "Companies";
                break;

            case R.id.nav_favorite:

                message = "Favorite";
                break;

            case R.id.nav_settings:

                message = "Settings";
                break;

        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        return true;
    }
}
