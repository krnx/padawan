package com.example.krnx.padawan.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.krnx.padawan.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by krnx on 01/07/2016.
 */
public class rankingHelper extends SQLiteOpenHelper {
    //Declaracion del nombre de la base de datos
    public static final int DATABASE_VERSION = 7;
    //Declaracion global de la version de la base de datos
    public static final String DATABASE_NAME = "padawan";
    //Declaracion del nombre de la tabla
    public static final String TABLE_NAME = "ranking";
    //sentencia global de cracion de la base de datos
    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            "id INT PRIMARY KEY UNIQUE AUTO_INCREMENT, " +
            "email VARCHAR(50)," +
            "points INT , " +
            "date DATE " +
            "FOREIGN KEY (email) REFERENCES user(email)" +
            ");";

    public rankingHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL(TABLE_CREATE);
    }

    public Map<String, String> getRanking() {
        Map<String, String> fila;
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"email", "points"};
        fila = new HashMap<String, String>();

        if (db != null) {
            try {
                Cursor cursor = db.query(
                        TABLE_NAME,          // The table to query
                        columns,            // The columns to return
                        null,               // The columns for the WHERE clause
                        null,               // The values for the WHERE clause
                        null,               // don't group the rows
                        null,               // don't filter by row groups
                        null                // The sort order
                );
                if (cursor.moveToFirst()) {
                    do {
                        String email = cursor.getString(cursor.getColumnIndex("email"));
                        String points = cursor.getString(cursor.getColumnIndex("points"));
                        fila.put(email, points);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            } catch (Exception e) {
                Log.e("Padawan-sql", e.toString());
            }
            db.close();
        }
        return fila;
    }

    public Boolean insertRanking(Context context, ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();

        if (db != null) {
            try {
                db.insertOrThrow(
                        TABLE_NAME,
                        null,
                        values);
                db.close();
                return true;
            } catch (SQLiteConstraintException e) {
                Log.v("Padawan", e.getMessage());
                return false;
            }
        }
        return false;
    }
}
