package com.example.pasteleria;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TablaDetalle extends SQLiteOpenHelper {

    public TablaDetalle(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase DetallesBD) {
        DetallesBD.execSQL("create table detalle_temp(id int primary key, nombre text, cantidad text, precio text, total text, id_producto text, id_usuario text, estado text, tipo text, id_cliente text)");
        DetallesBD.execSQL("create table detalle_tempo(id int primary key, nombre text, cantidad text, precio text, total text, id_producto text, id_usuario text, estado text, tipo text, id_cliente text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int il) {

    }


}