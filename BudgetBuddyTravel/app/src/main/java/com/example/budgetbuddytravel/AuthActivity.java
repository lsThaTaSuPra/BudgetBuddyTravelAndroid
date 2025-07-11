package com.example.budgetbuddytravel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.budgetbuddytravel.model.Utilisateur;

public class AuthActivity extends AppCompatActivity {

    private static final String TAG = "AuthActivity";

    private EditText editEmail, editPassword;
    private Button buttonLogin, buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);

        // Pré-remplir l'email s'il existe dans le fichier
        try {
            String savedEmail = Utilisateur.getEmailDepuisFichier(this);
            if (!savedEmail.isEmpty()) {
                editEmail.setText(savedEmail);
                Log.d(TAG, "Email chargé depuis fichier.");
            }
        } catch (Exception e) {
            Log.e(TAG, "Erreur lors du chargement de l'email depuis fichier", e);
        }

        // Connexion
        buttonLogin.setOnClickListener(v -> {
            String email = editEmail.getText().toString().trim();
            String password = editPassword.getText().toString().trim();

            Utilisateur user = new Utilisateur(0, "", email, password);

            try {
                if (user.seConnecter(this)) {
                    Log.d(TAG, "Connexion réussie pour l'email : " + email);
                    Toast.makeText(this, "Connexion réussie !", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AuthActivity.this, HomeActivity.class));
                    finish();
                } else {
                    Log.d(TAG, "Échec de la connexion : identifiants incorrects.");
                    Toast.makeText(this, "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e(TAG, "Erreur pendant la tentative de connexion", e);
                Toast.makeText(this, "Erreur lors de la connexion", Toast.LENGTH_LONG).show();
            }
        });

        // Inscription
        buttonRegister.setOnClickListener(v -> {
            String email = editEmail.getText().toString().trim();
            String password = editPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            Utilisateur user = new Utilisateur(0, "", email, password);

            try {
                user.creerCompte(this);
                Log.d(TAG, "Compte enregistré pour : " + email);
                Toast.makeText(this, "Compte créé ! Vous pouvez maintenant vous connecter.", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e(TAG, "Erreur lors de l'enregistrement de l'utilisateur", e);
                Toast.makeText(this, "Erreur lors de l'enregistrement", Toast.LENGTH_LONG).show();
            }
        });
    }
}
