package com.example.pasteleria;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Tablas extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "pasteleria.db";
    private static final int DATABASE_VERSION = 1;

    // Tabla UsuarioCliente
    public static final String TABLE_USUARIO_CLIENTE = "UsuarioCliente";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_DIRECCION = "direccion";
    public static final String COLUMN_TELEFONO = "telefono";
    public static final String COLUMN_MUNICIPIO = "municipio";
    public static final String COLUMN_ESTADO = "estado_municipio";
    public static final String COLUMN_CORREO = "correo";
    public static final String COLUMN_GENERO = "genero";
    public static final String COLUMN_USUARIO = "usuario";
    public static final String COLUMN_CONTRASENA = "contraseña";
    public static final String COLUMN_PREFERENCIAS = "preferencias";

    private static final String TABLE_CREATE_USUARIO_CLIENTE =
            "CREATE TABLE " + TABLE_USUARIO_CLIENTE + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_NOMBRE + " TEXT, " +
                    COLUMN_DIRECCION + " TEXT, " +
                    COLUMN_TELEFONO + " TEXT, " +
                    COLUMN_MUNICIPIO + " TEXT, " +
                    COLUMN_ESTADO + " TEXT, " +
                    COLUMN_CORREO + " TEXT, " +
                    COLUMN_GENERO + " TEXT, " +
                    COLUMN_USUARIO + " TEXT, " +
                    COLUMN_CONTRASENA + " TEXT, " +
                    COLUMN_PREFERENCIAS + " TEXT);";

    public Tablas(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_USUARIO_CLIENTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIO_CLIENTE);
        onCreate(db);
    }
}
