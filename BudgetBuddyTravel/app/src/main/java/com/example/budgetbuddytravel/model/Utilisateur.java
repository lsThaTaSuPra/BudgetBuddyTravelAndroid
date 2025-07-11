package com.example.budgetbuddytravel.model;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class Utilisateur extends Personne {
    private final String motDePasse;
    private boolean estConnecte = false;

    private static final String FILE_NAME = "utilisateur.txt";
    public Utilisateur(int id, String nom, String email, String motDePasse) {
        super(id, nom, email);
        this.motDePasse = motDePasse;
    }
     // Enregistre le compte dans le fichier local
    public void creerCompte(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            String data = this.email + ";" + this.motDePasse;
            fos.write(data.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     // Vérifie les identifiants stockés et connecte l’utilisateur si OK
    public boolean seConnecter(Context context) {
        try {
            FileInputStream fis = context.openFileInput(FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String ligne = reader.readLine();
            reader.close();
            fis.close();

            if (ligne != null) {
                String[] parts = ligne.split(";");
                if (parts.length == 2 && parts[0].equals(this.email) && parts[1].equals(this.motDePasse)) {
                    this.estConnecte = true;
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static String getEmailDepuisFichier(Context context) {
        try {
            FileInputStream fis = context.openFileInput(FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String ligne = reader.readLine();
            reader.close();
            fis.close();

            if (ligne != null) {
                String[] parts = ligne.split(";");
                if (parts.length == 2) {
                    return parts[0]; // retourne l'email
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
