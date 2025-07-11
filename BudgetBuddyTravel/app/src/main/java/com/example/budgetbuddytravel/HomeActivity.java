package com.example.budgetbuddytravel;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;

public class HomeActivity extends AppCompatActivity {

    private LinearLayout layoutVoyages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        layoutVoyages = findViewById(R.id.layoutVoyages);

        Button nouveauVoyageBtn = findViewById(R.id.buttonNouveauVoyage);

        nouveauVoyageBtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, TripActivity.class);
            startActivity(intent);
        });
        // Bouton de déconnexion pour l'utilisateur quand il est sur la HomePage
        Button deconnexionBtn = findViewById(R.id.buttonDeconnexion);

        deconnexionBtn.setOnClickListener(v -> {
            // Optionnel : effacer l’email enregistré si tu l’utilises
            getSharedPreferences("BudgetBuddyPrefs", MODE_PRIVATE)
                    .edit()
                    .clear()
                    .apply();

            Toast.makeText(this, "Déconnexion réussie", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(HomeActivity.this, AuthActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // évite retour avec le bouton back
            startActivity(intent);
            finish();
        });
        // Bouton pour nettoyer les données dans les voyages
        Button clearDataBtn = findViewById(R.id.buttonClearData);
        clearDataBtn.setOnClickListener(v -> {
            File dir = getFilesDir();
            File[] fichiers = dir.listFiles((dir1, name) -> name.startsWith("voyage_") && name.endsWith(".txt"));

            if (fichiers != null) {
                for (File fichier : fichiers) {
                    boolean deleted = fichier.delete();
                    if (!deleted) {
                        Toast.makeText(this, "Erreur suppression : " + fichier.getName(), Toast.LENGTH_SHORT).show();
                    }
                }
                Toast.makeText(this, "Tous les voyages ont été supprimés", Toast.LENGTH_SHORT).show();
                afficherTousLesVoyages(); // refresh UI
            }
        });

        afficherTousLesVoyages();
    }

    private void afficherTousLesVoyages() {
        layoutVoyages.removeAllViews();

        File dir = getFilesDir();
        File[] fichiers = dir.listFiles((dir1, name) -> name.startsWith("voyage_") && name.endsWith(".txt"));

        if (fichiers == null || fichiers.length == 0) {
            TextView tv = new TextView(this);
            tv.setText("Aucun voyage trouvé.");
            layoutVoyages.addView(tv);
            return;
        }

        for (File fichier : fichiers) {
            FileInputStream fis = null;
            BufferedReader reader = null;

            try {
                fis = openFileInput(fichier.getName());
                reader = new BufferedReader(new InputStreamReader(fis));

                StringBuilder contenu = new StringBuilder();
                String ligne;
                while ((ligne = reader.readLine()) != null) {
                    contenu.append(ligne).append("\n");
                }

                final String nomFichier = fichier.getName();

                TextView tv = new TextView(this);
                tv.setText(contenu.toString());
                tv.setPadding(0, 16, 0, 16);
                tv.setOnClickListener(v -> {
                    Intent intent = new Intent(this, com.example.budgetbuddytravel.utils.DetailVoyageActivity.class);
                    intent.putExtra("fichier", nomFichier);
                    startActivity(intent);
                });

                layoutVoyages.addView(tv);

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Erreur de lecture du fichier : " + fichier.getName(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Une erreur est survenue pour : " + fichier.getName(), Toast.LENGTH_SHORT).show();
            } finally {
                try {
                    if (reader != null) reader.close();
                    if (fis != null) fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Erreur lors de la fermeture du fichier : " + fichier.getName(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        afficherTousLesVoyages();
    }

}
