package com.example.budgetbuddytravel;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.budgetbuddytravel.model.CategorieDepense;
import com.example.budgetbuddytravel.model.Depense;
import com.example.budgetbuddytravel.model.Voyage;

import java.util.Date;

import com.example.budgetbuddytravel.utils.Statistiques;


public class StatistiquesActivity extends AppCompatActivity {

    private TextView statsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistiques);

        statsTextView = findViewById(R.id.statsTextView);

        Voyage voyage = creerVoyageExemple();

        String stats = "📊 Dépenses totales : " + Statistiques.calculerTotalDepenses(voyage) + " €\n"
                + "🎯 Budget prévu : " + Statistiques.calculerBudgetPrevuTotal(voyage) + " €\n"
                + "📂 Moyenne par catégorie : " + Statistiques.calculerMoyenneDepenseParCategorie(voyage) + " €\n"
                + "📉 Écart au budget global : " + Statistiques.ecartBudgetGlobal(voyage) + " €\n"
                + "📁 Nombre de catégories : " + Statistiques.nombreDeCategories(voyage);

        statsTextView.setText(stats);
    }

    private Voyage creerVoyageExemple() {
        Voyage v = new Voyage(1, "Rome", "Italie", new Date(), new Date(), 1000);

        CategorieDepense logement = new CategorieDepense(1, "Logement", 400);
        logement.ajouterDepense(new Depense(1, "Airbnb", 350, new Date(), "3 nuits"));

        CategorieDepense transport = new CategorieDepense(2, "Transport", 300);
        transport.ajouterDepense(new Depense(2, "Vol A/R", 250, new Date(), "billet avion"));

        v.ajouterCategorie(logement);
        v.ajouterCategorie(transport);

        return v;
    }
}
