package com.example.pasteleria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class ticket extends AppCompatActivity {
    private Button btnCancelarCompra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        //Activar Toolbar
        setSupportActionBar(findViewById(R.id.toolbar));

        btnCancelarCompra = findViewById(R.id.btnCancelarCompra);

        // Creacion de objeto de enlace a las base de datos
        TablaDetalle oper = new TablaDetalle(this, "operacion", null, 1);

        btnCancelarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase DetallesBD = oper.getWritableDatabase();
                int tipo = DetallesBD.delete("detalle_tempo", "tipo = 0", null);
                DetallesBD.close();

                Intent intent = new Intent(ticket.this, principal.class);
                startActivity(intent);
                finish();
            }
        });



    }


}