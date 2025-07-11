package com.example.budgetbuddytravel;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;

public class HomeFragment extends Fragment {

    private LinearLayout layoutVoyages;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // On gonfle le layout de ton ancien activity_home.xml
        View view = inflater.inflate(R.layout.activity_home, container, false);

        layoutVoyages = view.findViewById(R.id.layoutVoyages);

        Button nouveauVoyageBtn = view.findViewById(R.id.buttonNouveauVoyage);
        Button clearDataBtn = view.findViewById(R.id.buttonClearData);

        nouveauVoyageBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), TripActivity.class);
            startActivity(intent);
        });


        clearDataBtn.setOnClickListener(v -> {
            if (getActivity() == null) return;

            File dir = getActivity().getFilesDir();
            File[] fichiers = dir.listFiles((dir1, name) -> name.startsWith("voyage_") && name.endsWith(".txt"));

            if (fichiers != null) {
                for (File fichier : fichiers) {
                    boolean deleted = fichier.delete();
                    if (!deleted) {
                        Toast.makeText(getActivity(), "Erreur suppression : " + fichier.getName(), Toast.LENGTH_SHORT).show();
                    }
                }
                Toast.makeText(getActivity(), "Tous les voyages ont été supprimés", Toast.LENGTH_SHORT).show();
                afficherTousLesVoyages();
            }
        });

        afficherTousLesVoyages();

        return view;
    }

    private void afficherTousLesVoyages() {
        if (getActivity() == null) return;

        layoutVoyages.removeAllViews();

        File dir = getActivity().getFilesDir();
        File[] fichiers = dir.listFiles((dir1, name) -> name.startsWith("voyage_") && name.endsWith(".txt"));

        if (fichiers == null || fichiers.length == 0) {
            TextView tv = new TextView(getActivity());
            tv.setText("Aucun voyage trouvé.");
            layoutVoyages.addView(tv);
            return;
        }

        for (File fichier : fichiers) {
            FileInputStream fis = null;
            BufferedReader reader = null;

            try {
                fis = getActivity().openFileInput(fichier.getName());
                reader = new BufferedReader(new InputStreamReader(fis));

                StringBuilder contenu = new StringBuilder();
                String ligne;
                while ((ligne = reader.readLine()) != null) {
                    contenu.append(ligne).append("\n");
                }

                final String nomFichier = fichier.getName();

                TextView tv = new TextView(getActivity());
                tv.setText(contenu.toString());
                tv.setPadding(0, 16, 0, 16);
                tv.setOnClickListener(v -> {
                    Intent intent = new Intent(getActivity(), DetailVoyageActivity.class);
                    intent.putExtra("fichier", nomFichier);
                    startActivity(intent);
                });

                layoutVoyages.addView(tv);

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Erreur de lecture du fichier : " + fichier.getName(), Toast.LENGTH_SHORT).show();
            } finally {
                try {
                    if (reader != null) reader.close();
                    if (fis != null) fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Erreur lors de la fermeture du fichier : " + fichier.getName(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        afficherTousLesVoyages();
    }
}
