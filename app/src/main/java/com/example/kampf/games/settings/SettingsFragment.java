package com.example.kampf.games.settings;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.kampf.games.PrefsConst;
import com.example.kampf.games.R;

import java.util.Arrays;
import java.util.List;

public class SettingsFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Spinner spGamesAmount;
    private Spinner spCompaniesAmount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_settings, container, false);

    }

    @SuppressLint("CommitPrefEdits")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = sharedPreferences.edit();
        ((Toolbar) view.findViewById(R.id.toolbar)).setTitle(R.string.settings);
        spGamesAmount = view.findViewById(R.id.spGamesAmount);
        spCompaniesAmount = view.findViewById(R.id.spCompaniesAmount);
        setSelections();
        setOnItemClickListeners();

    }

    private void setSelections() {

        int gamesAmount = sharedPreferences.getInt(PrefsConst.SETTINGS_GAMES_AMOUNT, PrefsConst.SETTINGS_DEFAULT_AMOUNT);
        spGamesAmount.setSelection(getAmountIndex(gamesAmount));

        int companiesAmount = sharedPreferences.getInt(PrefsConst.SETTINGS_COMPANIES_AMOUNT, PrefsConst.SETTINGS_DEFAULT_AMOUNT);
        spGamesAmount.setSelection(getAmountIndex(companiesAmount));

    }

    private int getAmountIndex(int amount) {

        String[] optionsArray = getResources().getStringArray(R.array.amount_option);
        List<String> optionsList = Arrays.asList(optionsArray);
        return optionsList.indexOf(String.valueOf(amount));

    }

    private void setOnItemClickListeners() {

        spGamesAmount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String amountString = parent.getItemAtPosition(position).toString();
                int amountInt = Integer.parseInt(amountString);
                editor.putInt(PrefsConst.SETTINGS_GAMES_AMOUNT, amountInt).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spCompaniesAmount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String amountString = parent.getItemAtPosition(position).toString();
                int amountInt = Integer.parseInt(amountString);
                editor.putInt(PrefsConst.SETTINGS_COMPANIES_AMOUNT, amountInt).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

}
