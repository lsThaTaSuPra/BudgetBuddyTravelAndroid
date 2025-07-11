package com.example.budgetbuddytravel;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatDelegate;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Locale;

public class ParametresFragment extends Fragment {

    private static final String PREFS_NAME = "prefs";
    private static final String KEY_DARK_MODE = "dark_mode";
    private static final String KEY_LANGUE = "langue";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_parametres, container, false);

        Switch switchDarkMode = view.findViewById(R.id.switchDarkMode);
        Spinner spinnerLangue = view.findViewById(R.id.spinnerLangue);

        SharedPreferences prefs = requireContext().getSharedPreferences(PREFS_NAME, 0);
        boolean darkMode = prefs.getBoolean(KEY_DARK_MODE, false);
        String langue = prefs.getString(KEY_LANGUE, Locale.getDefault().getLanguage());

        // Appliquer les valeurs enregistrées
        switchDarkMode.setChecked(darkMode);
        if (langue.equals("fr")) {
            spinnerLangue.setSelection(0);
        } else {
            spinnerLangue.setSelection(1);
        }

        // Changement mode sombre
        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean(KEY_DARK_MODE, isChecked).apply();

            AppCompatDelegate.setDefaultNightMode(
                    isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
            );

            requireActivity().recreate(); // recharge l'activité pour appliquer le thème
        });

        // Changement langue
        spinnerLangue.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                String selectedLang = (position == 0) ? "fr" : "en";
                if (!selectedLang.equals(langue)) {
                    prefs.edit().putString(KEY_LANGUE, selectedLang).apply();
                    setLocale(selectedLang);
                }
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        return view;
    }

    private void setLocale(String langCode) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        requireActivity().getResources().updateConfiguration(config, requireActivity().getResources().getDisplayMetrics());
        requireActivity().recreate(); // recharge l'activité
    }
}
