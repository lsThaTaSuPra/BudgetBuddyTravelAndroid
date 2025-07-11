package com.example.budgetbuddytravel.model;

public class Personne {
    protected int id;
    protected String nom;
    protected String email;
    public Personne(int id, String nom, String email) {
        this.id = id;
        this.nom = nom;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
