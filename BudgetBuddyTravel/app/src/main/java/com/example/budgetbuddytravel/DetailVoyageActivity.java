package com.example.budgetbuddytravel.utils;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.budgetbuddytravel.R;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class DetailVoyageActivity extends AppCompatActivity {

    private LinearLayout layoutCategories;
    private Button buttonAjouterCategorie;
    private String nomFichier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_voyage);

        layoutCategories = findViewById(R.id.layoutDetailVoyage);
        buttonAjouterCategorie = findViewById(R.id.buttonAjouterCategorie);

        try {
            nomFichier = getIntent().getStringExtra("fichier");
            if (nomFichier == null) {
                Toast.makeText(this, "Fichier voyage introuvable", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            afficherInfosVoyage();

            buttonAjouterCategorie.setOnClickListener(v -> showAddCategorieDialog());
        } catch (Exception e) {
            Toast.makeText(this, "Erreur lors de l'initialisation", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void afficherInfosVoyage() {
        layoutCategories.removeAllViews();

        try (FileInputStream fis = openFileInput(nomFichier);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {

            StringBuilder infos = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.contains(",")) {
                    String[] parts = line.split(",");
                    if (parts.length >= 2) {
                        String nomCat = parts[0].trim();
                        String budgetCat = parts[1].trim();

                        TextView tvCat = new TextView(this);
                        tvCat.setText("- " + nomCat + " : " + budgetCat + " €");
                        tvCat.setTextSize(16);
                        layoutCategories.addView(tvCat);
                    }
                } else {
                    TextView tvInfo = new TextView(this);
                    tvInfo.setText(line);
                    tvInfo.setTextSize(18);
                    layoutCategories.addView(tvInfo);
                }
            }

        } catch (Exception e) {
            Toast.makeText(this, "Erreur lecture fichier : " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void showAddCategorieDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nouvelle catégorie");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 10);

        final EditText inputNom = new EditText(this);
        inputNom.setHint("Nom de la catégorie");
        layout.addView(inputNom);

        final EditText inputBudget = new EditText(this);
        inputBudget.setHint("Budget prévu (€)");
        inputBudget.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
        layout.addView(inputBudget);

        builder.setView(layout);

        builder.setPositiveButton("Ajouter", (dialog, which) -> {
            try {
                String nom = inputNom.getText().toString().trim();
                String budgetStr = inputBudget.getText().toString().trim();

                if (nom.isEmpty() || budgetStr.isEmpty()) {
                    Toast.makeText(this, "Tous les champs doivent être remplis", Toast.LENGTH_SHORT).show();
                    return;
                }

                float budget = Float.parseFloat(budgetStr);
                ajouterCategorieAuFichier(nom, budget);

                TextView tvCat = new TextView(this);
                tvCat.setText("- " + nom + " : " + budget + " €");
                tvCat.setTextSize(16);
                layoutCategories.addView(tvCat);

                Toast.makeText(this, "Catégorie ajoutée", Toast.LENGTH_SHORT).show();

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Budget invalide", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Erreur lors de l'ajout : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });

        builder.setNegativeButton("Annuler", null);
        builder.show();
    }

    private void ajouterCategorieAuFichier(String nom, float budget) {
        try {
            String ligne = nom + "," + budget + "\n";
            try (FileOutputStream fos = openFileOutput(nomFichier, MODE_APPEND)) {
                fos.write(ligne.getBytes());
            }
        } catch (Exception e) {
            Toast.makeText(this, "Erreur lors de l'ajout au fichier", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
