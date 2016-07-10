package com.example.krnx.padawan;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.krnx.padawan.db.userHelper;

/**
 * Created by arnau on 10/07/16.
 */
public class SigninActivity extends AppCompatActivity implements View.OnClickListener {

    Button registrar;
    EditText name;
    EditText surname;
    EditText email;
    EditText phone;

    @Override
    protected void onCreate(Bundle savedIinstanceState) {
        super.onCreate(savedIinstanceState);
        setContentView(R.layout.activity_signin);

        registrar = (Button) findViewById(R.id.register);
        name = (EditText) findViewById(R.id.name);
        surname = (EditText) findViewById(R.id.surname);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);

        registrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.register:
                userHelper userHelper = new userHelper(getApplicationContext());
                ContentValues valuesToStore = new ContentValues();
                valuesToStore.put("name", name.getText().toString());
                valuesToStore.put("surname", surname.getText().toString());
                valuesToStore.put("email", email.getText().toString());
                valuesToStore.put("phone", phone.getText().toString());

                userHelper.insertUser(valuesToStore);
                break;
        }
    }
}
