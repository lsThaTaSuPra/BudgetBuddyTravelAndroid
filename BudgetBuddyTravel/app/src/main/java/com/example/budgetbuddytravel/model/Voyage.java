package com.example.budgetbuddytravel.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Voyage {
    private int id;
    private String nom;
    private String destination;
    private Date dateDepart;
    private Date dateRetour;
    private float budgetGlobal;
    private final List<CategorieDepense> categories = new ArrayList<>();

    public Voyage() {
    }

    public Voyage(int id, String nom, String destination, Date dateDepart, Date dateRetour, float budgetGlobal) {
        this.id = id;
        this.nom = nom;
        this.destination = destination;
        this.dateDepart = dateDepart;
        this.dateRetour = dateRetour;
        this.budgetGlobal = budgetGlobal;
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

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(Date dateDepart) {
        this.dateDepart = dateDepart;
    }

    public Date getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(Date dateRetour) {
        this.dateRetour = dateRetour;
    }

    public float getBudgetGlobal() {
        return budgetGlobal;
    }

    public void setBudgetGlobal(float budgetGlobal) {
        this.budgetGlobal = budgetGlobal;
    }

    public List<CategorieDepense> getCategories() {
        return categories;
    }

    public void ajouterCategorie(CategorieDepense categorie) {
        categories.add(categorie);
    }

    public void supprimerCategorie(CategorieDepense categorie) {
        categories.remove(categorie);
    }

    @NonNull
    @Override
    public String toString() {
        return "Voyage{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", destination='" + destination + '\'' +
                ", dateDepart=" + dateDepart +
                ", dateRetour=" + dateRetour +
                ", budgetGlobal=" + budgetGlobal +
                ", nbCategories=" + categories.size() +
                '}';
    }
}
