package com.example.krnx.padawan;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.example.krnx.padawan.db.userHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    private static final String TAG = "Padawan-Login";
    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;
    private TextView mStatusTextView;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        // Customize sign-in button. The sign-in button can be displayed in
        // multiple sizes and color schemes. It can also be contextually
        // rendered based on the requested scopes. For example. a red button may
        // be displayed when Google+ scopes are requested, but a white button
        // may be displayed when only basic profile is requested. Try adding the
        // Scopes.PLUS_LOGIN scope to the GoogleSignInOptions to see the
        // difference.
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());

        findViewById(R.id.sign_in_button).setOnClickListener(this);

        Button logInButton = (Button) findViewById(R.id.login);
        findViewById(R.id.login).setOnClickListener(this);

        /*Button insert = (Button) findViewById(R.id.insert);
        insert.setOnClickListener(this);

        userInsert = (EditText) findViewById(R.id.userInsert);
        userInsert.setOnClickListener(this);

        result = (TextView) findViewById(R.id.result);
        userInsert.setOnClickListener(this);*/
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.login:
                logIn();
                break;
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        Log.v("Padawan-Login", "Abans de startActivityForResult");
        startActivityForResult(signInIntent, RC_SIGN_IN);
        Log.v("Padawan-Login", "Després de startActivityForResult");
    }

    private void logIn() {
        EditText usuari = (EditText) findViewById(R.id.usuari);
        EditText contrsenya = (EditText) findViewById(R.id.contrsenya);

        String[] login = {usuari.getText().toString(), contrsenya.getText().toString()};
        userHelper userHelper = new userHelper(getApplicationContext());

//        userHelper.insertUserTest("Insert into user values ('arnau.pratc@gmail.com','Arnau','Prat','01/02/1984','sdfsd sdfds','1234')");


        if (userHelper.getUserByLogin(login)) {
            startActivity(new Intent(getApplicationContext(), BaseActivity.class));
        } else {
            Toast.makeText(getApplicationContext(), R.string.loginError, Toast.LENGTH_SHORT).show();
        }


    }

   /* private Integer insertUser() {
        //ContentValues.put("Name", queryValues.get("name"));
    }*/


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v(TAG, "A onActivityResult");
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        Log.v(TAG, "Despres onActivityResult");
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "Login result: " + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
//            mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            //updateUI(false);
        }
    }

    private void updateUI(boolean signedIn) {
        if (signedIn) {
            Toast.makeText(getApplicationContext(), R.string.signed_in_fmt, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "update UI:" + signedIn);
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
//            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);

            startActivity(new Intent(getApplicationContext(), CalculadoraActivity.class));

        } else {
//            mStatusTextView.setText(R.string.signed_out);

            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
