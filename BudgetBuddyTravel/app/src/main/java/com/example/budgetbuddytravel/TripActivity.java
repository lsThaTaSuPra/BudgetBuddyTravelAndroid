package com.example.budgetbuddytravel;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.budgetbuddytravel.model.CategorieDepense;
import com.example.budgetbuddytravel.model.Voyage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.io.FileOutputStream;
import java.io.IOException;

public class TripActivity extends AppCompatActivity {

    private EditText nomVoyageInput, destinationInput, dateDepartInput, dateRetourInput, budgetInput;
    private LinearLayout layoutCategories;
    private Voyage voyage;
    private int nextCategorieId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voyage_creation);

        nomVoyageInput = findViewById(R.id.editTextNomVoyage);
        destinationInput = findViewById(R.id.editTextDestination);
        dateDepartInput = findViewById(R.id.editTextDateDepart);
        dateRetourInput = findViewById(R.id.editTextDateRetour);
        budgetInput = findViewById(R.id.editTextBudget);
        layoutCategories = findViewById(R.id.layoutCategories);

        Button addCategorieBtn = findViewById(R.id.buttonAjouterCategorie);
        Button validerBtn = findViewById(R.id.buttonValiderVoyage);

        voyage = new Voyage();

        addCategorieBtn.setOnClickListener(v -> showAddCategorieDialog());

        validerBtn.setOnClickListener(v -> {
            String nom = nomVoyageInput.getText().toString().trim();
            String destination = destinationInput.getText().toString().trim();
            String dateDepartStr = dateDepartInput.getText().toString().trim();
            String dateRetourStr = dateRetourInput.getText().toString().trim();
            String budgetStr = budgetInput.getText().toString().trim();

            if (nom.isEmpty() || destination.isEmpty() || dateDepartStr.isEmpty()
                    || dateRetourStr.isEmpty() || budgetStr.isEmpty()) {
                Toast.makeText(this, "Tous les champs doivent être remplis", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

                voyage.setNom(nom);
                voyage.setDestination(destination);
                voyage.setDateDepart(sdf.parse(dateDepartStr));
                voyage.setDateRetour(sdf.parse(dateRetourStr));
                voyage.setBudgetGlobal(Float.parseFloat(budgetStr));

                sauvegarderVoyageDansFichier(voyage);
                finish();

            } catch (ParseException e) {
                Toast.makeText(this, "Format de date invalide. Utilisez jj/MM/aaaa", Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Format de budget invalide", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Erreur inattendue : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
    }

    private void showAddCategorieDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nouvelle catégorie");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText inputNom = new EditText(this);
        inputNom.setHint("Nom de la catégorie");
        layout.addView(inputNom);

        final EditText inputBudget = new EditText(this);
        inputBudget.setHint("Budget prévu (€)");
        inputBudget.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
        layout.addView(inputBudget);

        builder.setView(layout);

        builder.setPositiveButton("Ajouter", (dialog, which) -> {
            String nom = inputNom.getText().toString();
            String budgetStr = inputBudget.getText().toString();

            if (!nom.isEmpty() && !budgetStr.isEmpty()) {
                try {
                    float budget = Float.parseFloat(budgetStr);
                    CategorieDepense categorie = new CategorieDepense(nextCategorieId++, nom, budget);
                    voyage.ajouterCategorie(categorie);

                    TextView tv = new TextView(this);
                    tv.setText("- " + nom + " : " + budget + " €");
                    layoutCategories.addView(tv);

                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Budget invalide", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(this, "Erreur inattendue", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        builder.setNegativeButton("Annuler", null);
        builder.show();
    }

    private void sauvegarderVoyageDansFichier(Voyage voyage) {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        try {
            sb.append("Voyage : ").append(voyage.getNom()).append("\n");
            sb.append("Destination : ").append(voyage.getDestination()).append("\n");
            sb.append("Départ : ").append(sdf.format(voyage.getDateDepart())).append("\n");
            sb.append("Retour : ").append(sdf.format(voyage.getDateRetour())).append("\n");
            sb.append("Budget global : ").append(voyage.getBudgetGlobal()).append(" €\n");
            sb.append("Catégories :\n");

            for (CategorieDepense cat : voyage.getCategories()) {
                sb.append("- ").append(cat.getNom())
                        .append(" : ").append(cat.getBudgetPrevu())
                        .append(" €\n");
            }

            String filename = "voyage_" + System.currentTimeMillis() + ".txt";

            try (FileOutputStream fos = openFileOutput(filename, MODE_PRIVATE)) {
                fos.write(sb.toString().getBytes());
                Toast.makeText(this, "Voyage sauvegardé dans : " + filename, Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(this, "Erreur lors de la sauvegarde du fichier", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Erreur lors de la génération du contenu du fichier", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
