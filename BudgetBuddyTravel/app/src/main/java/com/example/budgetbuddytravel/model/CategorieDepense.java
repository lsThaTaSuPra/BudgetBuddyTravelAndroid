package com.example.budgetbuddytravel.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class CategorieDepense {
    private int id;
    private final String nom;
    private final float budgetPrevu;
    private float budgetReel;
    private final List<Depense> depenses = new ArrayList<>();

    public CategorieDepense(int id, String nom, float budgetPrevu) {
        this.id = id;
        this.nom = nom;
        this.budgetPrevu = budgetPrevu;
        this.budgetReel = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public float getBudgetPrevu() {
        return budgetPrevu;
    }
    public List<Depense> getDepenses() {
        return depenses;
    }

    public void ajouterDepense(Depense depense) {
        depenses.add(depense);
        budgetReel += depense.getMontant(); // exemple : recalcul automatique
    }

    @NonNull
    @Override
    public String toString() {
        return "CategorieDepense{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", budgetPrevu=" + budgetPrevu +
                ", budgetReel=" + budgetReel +
                ", depenses=" + depenses.size() +
                '}';
    }
}
