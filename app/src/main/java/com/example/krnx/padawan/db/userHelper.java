package com.example.krnx.padawan.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by inlab on 01/07/2016.
 */
public class userHelper extends SQLiteOpenHelper {
    //Declaracion del nombre de la base de datos
    public static final int DATABASE_VERSION = 5;
    //Declaracion global de la version de la base de datos
    public static final String DATABASE_NAME = "padawan";
    //Declaracion del nombre de la tabla
    public static final String TABLE_NAME = "user";
    //sentencia global de cracion de la base de datos
    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            "email VARCHAR(50) PRIMARY KEY UNIQUE, " +
            "name VARCHAR(15) , " +
            "surname VARCHAR(15), " +
            "birthdate DATE, " +
            "address VARCHAR(15), " +
            "pass VARCHAR(50)" +
            ");";

    public userHelper(Context context) {
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

    public List getUsers() {
        List<String> fila = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"email", "name", "surname", "birthdate", "address"};

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
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String surname = cursor.getString(cursor.getColumnIndex("surname"));
                        String birthdate = cursor.getString(cursor.getColumnIndex("birthdate"));
                        String address = cursor.getString(cursor.getColumnIndex("address"));
                        fila.add("<" + email + "> " + name + " " + surname + "; " + birthdate + "; " + address + "; ");
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

    public Boolean getUserByLogin(String[] login) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"email", "name", "surname", "birthdate", "address"};
        String whereClause = "email = ? AND pass = ?";
//        String[] whereArgs = new String[] {"value1","value2"};

        if (db != null) {
            try {
                Cursor cursor = db.query(
                        TABLE_NAME,          // The table to query
                        columns,            // The columns to return
                        whereClause,      // The columns for the WHERE clause
                        login,               // The values for the WHERE clause
                        null,               // don't group the rows
                        null,               // don't filter by row groups
                        null                // The sort order
                );
                if (cursor.moveToFirst()) {
                    cursor.close();
                    db.close();
                    return true;
                }else {
                    cursor.close();
                    db.close();
                    return false;
                }
            } catch (Exception e) {
                Log.e("Padawan-sql", e.toString());
            }
            db.close();
        }
        return false;
    }

    public void insertUser(ContentValues values){
        SQLiteDatabase db = this.getWritableDatabase();

        if (db != null) {
            db.insert(
                    TABLE_NAME,
                    null,
                    values);

            /*try {
                db.execSQL(query.toString());
//                        db.execSQL("Insert into user values ('arnau.pratc@gmail.com','Arnau','Prat','01/02/1984','sdfsd sdfds')");
            } catch (Exception e) {
                Log.e("Padawan", e.toString());
            }*/
            db.close();
        }
    }

    public void insertUserTest(String query){
        SQLiteDatabase db = this.getWritableDatabase();
        if (db != null) {
            try {
                db.execSQL(query);
//                db.execSQL("Insert into user values ('arnau.pratc@gmail.com','Arnau','Prat','01/02/1984','sdfsd sdfds')");
            } catch (Exception e) {
                Log.e("Padawan", e.toString());
            }
            db.close();
        }
    }
}
