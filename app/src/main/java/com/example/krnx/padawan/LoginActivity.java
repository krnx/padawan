package com.example.krnx.padawan;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krnx.padawan.model.User;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.example.krnx.padawan.db.userHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Padawan-Login";
    private static final int RC_SIGN_IN = 9001;
    private EditText usuari;
    private EditText contrasenya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button logInButton = (Button) findViewById(R.id.login);
        logInButton.setOnClickListener(this);

        Button signInButton = (Button) findViewById(R.id.signin);
        signInButton.setOnClickListener(this);

        usuari = (EditText) findViewById(R.id.usuari);
        contrasenya = (EditText) findViewById(R.id.contrsenya);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                logIn();
                break;
            case R.id.signin:
                signIn();
                break;
        }
    }

    private void signIn() {
        startActivity(new Intent(getApplicationContext(), SigninActivity.class));
    }

    private void logIn() {
        String[] login = {usuari.getText().toString(), SigninActivity.md5(contrasenya.getText().toString())};
        userHelper userHelper = new userHelper(getApplicationContext());

        if (userHelper.getUserByLogin(login)) {
            SharedPreferences.Editor editor = getSharedPreferences("Padawan-prefs", MODE_PRIVATE).edit();
            editor.putString("email", usuari.getText().toString());  //Guardem l'email per a poder accedir a les dades de l'usuari des d'altres activityes de l'app
            editor.commit();
            startActivity(new Intent(getApplicationContext(), CalculadoraActivity.class));
        } else {
            Toast.makeText(getApplicationContext(), R.string.loginError, Toast.LENGTH_SHORT).show();
        }

    }
}
