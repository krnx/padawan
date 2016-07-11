package com.example.krnx.padawan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CalculadoraActivity extends BaseActivity implements View.OnClickListener {

    private TextView pantalla;
    private Double resultat;
    private Integer lastAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);

        Button u = (Button) findViewById(R.id.u);
        Button dos = (Button) findViewById(R.id.dos);
        Button tres = (Button) findViewById(R.id.tres);
        Button quatre = (Button) findViewById(R.id.quatre);
        Button cinc = (Button) findViewById(R.id.cinc);
        Button sis = (Button) findViewById(R.id.sis);
        Button set = (Button) findViewById(R.id.set);
        Button vuit = (Button) findViewById(R.id.vuit);
        Button nou = (Button) findViewById(R.id.nou);
        Button zero = (Button) findViewById(R.id.zero);
        Button mes = (Button) findViewById(R.id.mes);
        Button menys = (Button) findViewById(R.id.menys);
        Button multiplicar = (Button) findViewById(R.id.multiplicar);
        Button dividir = (Button) findViewById(R.id.dividir);
        Button coma = (Button) findViewById(R.id.coma);
        Button call = (Button) findViewById(R.id.call);
        Button back = (Button) findViewById(R.id.back);
        Button igual = (Button) findViewById(R.id.igual);
        Button contact = (Button) findViewById(R.id.contact);

        this.pantalla = (TextView) findViewById(R.id.pantalla);
        this.resultat = new Double("0");
        this.lastAction = new Integer("0");

        u.setOnClickListener(this);
        dos.setOnClickListener(this);
        tres.setOnClickListener(this);
        quatre.setOnClickListener(this);
        cinc.setOnClickListener(this);
        sis.setOnClickListener(this);
        set.setOnClickListener(this);
        vuit.setOnClickListener(this);
        nou.setOnClickListener(this);
        zero.setOnClickListener(this);
        mes.setOnClickListener(this);
        menys.setOnClickListener(this);
        multiplicar.setOnClickListener(this);
        dividir.setOnClickListener(this);
        coma.setOnClickListener(this);
        call.setOnClickListener(this);
        back.setOnClickListener(this);
        igual.setOnClickListener(this);
        contact.setOnClickListener(this);

        checkMenuItem(0);
    }

    @Override
    public void onClick(View v) {
        String textPantalla, hint;
        textPantalla = this.pantalla.getText().toString();
        switch (v.getId()) {
            case R.id.mes:
                if (textPantalla.toString().isEmpty()) {
                    this.resultat += Double.parseDouble("0");
                    hint = "0";
                } else {
                    this.resultat += Double.parseDouble(textPantalla);
                    hint = textPantalla;
                }
                this.pantalla.setText("");
                this.pantalla.setHint(hint);
                this.lastAction = Integer.valueOf(1);
                break;
            case R.id.menys:
                if (textPantalla.toString().isEmpty()) {
                    this.resultat -= Double.parseDouble("0");
                    hint = "0";
                } else if (this.resultat.equals(Double.valueOf("0"))) {
                    this.resultat = Double.parseDouble(textPantalla);
                    hint = textPantalla;
                } else {
                    this.resultat -= Double.parseDouble(textPantalla);
                    hint = textPantalla;
                }
                this.pantalla.setText("");
                this.pantalla.setHint(hint);
                this.lastAction = Integer.valueOf(2);
                break;
            case R.id.multiplicar:
                if (textPantalla.toString().isEmpty()) {
                    this.resultat = this.resultat * Double.parseDouble("0");
                    hint = "0";
                } else if (this.resultat.equals(Double.valueOf("0"))) {
                    this.resultat = Double.parseDouble(textPantalla);
                    hint = textPantalla;
                } else {
                    this.resultat = this.resultat * Double.parseDouble(textPantalla);
                    hint = textPantalla;
                }
                this.pantalla.setText("");
                this.pantalla.setHint(hint);
                this.lastAction = Integer.valueOf(3);
                break;
            case R.id.dividir:
                if (textPantalla.toString().isEmpty()) {
                    this.resultat = this.resultat / Double.parseDouble("0");
                    hint = "0";
                } else if (this.resultat.equals(Double.valueOf("0"))) {
                    this.resultat = Double.parseDouble(textPantalla);
                    hint = textPantalla;
                } else {
                    this.resultat = this.resultat / Double.parseDouble(textPantalla);
                    hint = textPantalla;
                }
                this.pantalla.setText("");
                this.pantalla.setHint(hint);
                this.lastAction = Integer.valueOf(4);
                break;
            case R.id.igual:
                Double igual = Double.valueOf("0");
                if (!textPantalla.toString().isEmpty())
                    igual = Double.parseDouble(textPantalla);
                switch (this.lastAction) {
                    case 1:
                        Log.v("calc", "igual case 2: " + this.resultat + " + " + igual);
                        this.resultat = this.resultat + igual;
                        break;
                    case 2:
                        Log.v("calc", "igual case 2: " + this.resultat + " - " + igual);
                        this.resultat = this.resultat - igual;
                        break;
                    case 3:
                        Log.v("calc", "igual case 3: " + this.resultat + " * " + igual);
                        this.resultat = this.resultat * igual;
                        break;
                    case 4:
                        Log.v("calc", "igual case 3: " + this.resultat + " / " + igual);
                        this.resultat = this.resultat / igual;
                        break;

                }
                //Instanciamos el SharedPreferences
                SharedPreferences settings = getSharedPreferences("Calculadora", Context.MODE_PRIVATE);
                //Obtenemos el editor
                SharedPreferences.Editor editor = settings.edit();
                //Editamos
                editor.putString("resultat", this.resultat.toString());
                //Guardamos los cambios
                editor.commit();

                this.pantalla.setText(this.resultat.toString());
                Log.v("calc", "igual: " + this.resultat);
                this.resultat = Double.valueOf(0);
                this.lastAction = 0;
                break;
            case R.id.call:
                Intent trucada = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + textPantalla));
                startActivity(trucada);
                break;
            case R.id.back:
                // TODO: Comprovar que no esborri quan no hi ha cap numero
                if (textPantalla.length() > 0)
                    textPantalla = textPantalla.substring(0, textPantalla.length() - 1);
                this.pantalla.setText(textPantalla);
                break;
            case R.id.contact:
                Intent contacte = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(contacte, 0);
                break;
            default:
                Button b = (Button) v;
                String textButton = b.getText().toString();
                this.pantalla.append(textButton);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.calculadora_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.toast:
                SharedPreferences settings = getSharedPreferences("Calculadora", Context.MODE_PRIVATE);
                Toast.makeText(getApplicationContext(), settings.getString("resultat", "No hi ha resultat"), Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v("Player-calc","Contacts:"+resultCode);
        Log.v("Player-calc","Contacts:"+requestCode);
        // Si la petición se hizo correctamente y PICK_CONTACT_REQUEST
        if (resultCode == Activity.RESULT_OK && requestCode == 0) {
            // Hacemos la consulta del nombre del contacto.
            Cursor cursor = getContentResolver().query(
                    ContactsContract.Contacts.CONTENT_URI, null,
                    "DISPLAY_NAME = '" + "NAME" + "'", null, null);

            if (cursor.moveToFirst()) {
                // True si el cursor no está vacío
                int columnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                String name = cursor.getString(columnIndex);
                Log.v("Player-calc","Nom: "+name);
                Toast.makeText(this, "Nom: "+name, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outstate) {
        super.onSaveInstanceState(outstate);
        outstate.putString("pantalla", this.pantalla.getText().toString());
        outstate.putDouble("resultat", this.resultat);
        outstate.putInt("lasAction", this.lastAction);
        Log.v("Calc", "SaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle outstate) {
        super.onRestoreInstanceState(outstate);
        this.pantalla.setText(outstate.getString("pantalla"));
        this.resultat = outstate.getDouble("resultat");
        this.lastAction = outstate.getInt("lasAction");
        Log.v("Calc", "RestoreInstanceState");
    }
}