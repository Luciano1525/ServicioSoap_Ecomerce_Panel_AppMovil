package com.example.pasteleria;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONException;
import org.json.JSONObject;

public class Datos {

    private Tablas dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public Datos(Context c) {
        context = c;
    }

    public Datos open() throws SQLException {
        dbHelper = new Tablas(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insertUsuarioCliente(JSONObject userJson) throws JSONException {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Tablas.COLUMN_ID, userJson.getInt("id"));
        contentValues.put(Tablas.COLUMN_NOMBRE, userJson.getString("nombre"));
        contentValues.put(Tablas.COLUMN_DIRECCION, userJson.getString("direccion"));
        contentValues.put(Tablas.COLUMN_TELEFONO, userJson.getString("telefono"));
        contentValues.put(Tablas.COLUMN_MUNICIPIO, userJson.getString("municipio"));
        contentValues.put(Tablas.COLUMN_ESTADO, userJson.getString("estado_municipio"));
        contentValues.put(Tablas.COLUMN_CORREO, userJson.getString("correo"));
        contentValues.put(Tablas.COLUMN_GENERO, userJson.getString("genero"));
        contentValues.put(Tablas.COLUMN_USUARIO, userJson.getString("usuario"));
        contentValues.put(Tablas.COLUMN_CONTRASENA, userJson.getString("contraseña"));
        contentValues.put(Tablas.COLUMN_PREFERENCIAS, userJson.getString("preferencias"));

        database.insert(Tablas.TABLE_USUARIO_CLIENTE, null, contentValues);
    }

    public Cursor fetchUsuarioCliente() {
        String[] columns = new String[] {
                Tablas.COLUMN_ID,
                Tablas.COLUMN_NOMBRE,
                Tablas.COLUMN_DIRECCION,
                Tablas.COLUMN_TELEFONO,
                Tablas.COLUMN_MUNICIPIO,
                Tablas.COLUMN_ESTADO,
                Tablas.COLUMN_CORREO,
                Tablas.COLUMN_GENERO,
                Tablas.COLUMN_USUARIO,
                Tablas.COLUMN_CONTRASENA,
                Tablas.COLUMN_PREFERENCIAS
        };
        Cursor cursor = database.query(Tablas.TABLE_USUARIO_CLIENTE, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
}
