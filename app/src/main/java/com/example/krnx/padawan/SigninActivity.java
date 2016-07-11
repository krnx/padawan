package com.example.krnx.padawan;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.krnx.padawan.db.userHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by arnau on 10/07/16.
 */
public class SigninActivity extends AppCompatActivity implements View.OnClickListener {
    String TAG = "Padawan-Signin";
    Button registrar;
    EditText name;
    EditText surname;
    EditText email;
    EditText phone;
    EditText pass;

    @Override
    protected void onCreate(Bundle savedIinstanceState) {
        super.onCreate(savedIinstanceState);
        setContentView(R.layout.activity_signin);

        registrar = (Button) findViewById(R.id.register);
        name = (EditText) findViewById(R.id.name);
        surname = (EditText) findViewById(R.id.surname);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        pass = (EditText) findViewById(R.id.pass);

        registrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.register:
                userHelper userHelper = new userHelper(getApplicationContext());
                ContentValues valuesToStore = new ContentValues();
                valuesToStore.put("name", this.name.getText().toString());
                valuesToStore.put("surname", this.surname.getText().toString());
                valuesToStore.put("email", this.email.getText().toString());
                valuesToStore.put("phone", this.phone.getText().toString());
                valuesToStore.put("pass", md5(this.pass.getText().toString()));

                Log.v(TAG, md5(this.pass.getText().toString()));

                if(userHelper.insertUser(this, valuesToStore)){
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }

                break;
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outstate) {
        super.onSaveInstanceState(outstate);
        outstate.putString("name", this.name.getText().toString());
        outstate.putString("surname", this.surname.getText().toString());
        outstate.putString("email", this.email.getText().toString());
        outstate.putString("phone", this.phone.getText().toString());
        outstate.putString("pass", this.pass.getText().toString());

        Log.v(TAG, "SaveInstanceState");

    }

    @Override
    protected void onRestoreInstanceState(Bundle outstate) {
        super.onRestoreInstanceState(outstate);
        this.name.setText(outstate.getString("name"));
        this.surname.setText(outstate.getString("surname"));
        this.email.setText(outstate.getString("email"));
        this.phone.setText(outstate.getString("phone"));
        this.pass.setText(outstate.getString("pass"));

        Log.v(TAG, "RestoreInstanceState");
    }

    public static final String md5(final String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
