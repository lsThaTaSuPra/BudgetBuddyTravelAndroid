package com.example.budgetbuddytravel.model;

import androidx.annotation.NonNull;

import java.util.Date;

public class Depense {
    private int id;
    private final String nom;
    private final float montant;
    private final Date date;
    private final String description;

    public Depense(int id, String nom, float montant, Date date, String description) {
        this.id = id;
        this.nom = nom;
        this.montant = montant;
        this.date = date;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public float getMontant() {
        return montant;
    }

    @NonNull
    @Override
    public String toString() {
        return "Depense{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", montant=" + montant +
                ", date=" + date +
                ", description='" + description + '\'' +
                '}';
    }
}
