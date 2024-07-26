package com.example.pasteleria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class productos_categoria extends AppCompatActivity {
    ListView LVP;
    List<Contacto> lst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos_categoria);
        // Activar Toolbar
        setSupportActionBar(findViewById(R.id.toolbar));

        // Configuración de ListView
        LVP = findViewById(R.id.LVP);

        CustomAdapter adapter = new CustomAdapter(this, GetData());
        LVP.setAdapter(adapter);

        LVP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Crear un Intent para navegar a TicketActivity
                Intent intent = new Intent(productos_categoria.this, producto.class);
                // Iniciar la nueva actividad
                startActivity(intent);
            }
        });


    }


    private List<Contacto> GetData() {
        lst = new ArrayList<>();

        lst.add(new Contacto(0, R.drawable.frutal, "Fruta", "Tartas", "$ 300.00"));
        lst.add(new Contacto(1, R.drawable.capuccino, "Capuccino 3 Leches", "Pasteles", "$ 450.00"));
        lst.add(new Contacto(2, R.drawable.cupcake, "Básico", "CupCakes", "$ 20.00"));
        return lst;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Hacer visible el menu
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }

}