package com.example.pasteleria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class producto extends AppCompatActivity {
    private Button btnComprar, btnAgregarCarrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);

        //Ingresar a la Vista de ticket
        btnComprar = (Button) findViewById(R.id.btnComprar);
        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Compra", Toast.LENGTH_SHORT).show();
                Log.i("INFO:", "Carrito");
                Intent intent = new Intent(producto.this, ticket.class);
                startActivity(intent);
            }
        });


        //Ingresar a la Vista de Carrito
        btnAgregarCarrito = (Button) findViewById(R.id.btnAgregarCarrito);
        btnAgregarCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Carrito", Toast.LENGTH_SHORT).show();
                Log.i("INFO:", "Carrito");
                Intent intent = new Intent(producto.this, carrito.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Hacer visible el menu
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }


}