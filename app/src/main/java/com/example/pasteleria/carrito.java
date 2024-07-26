package com.example.pasteleria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class carrito extends AppCompatActivity {
    ListView listViewCarrito;
    List<Productos> productos;
    double total = 0;
    TextView tvTotal;
    CarritoAdapter adapter;
    private Button btnComprar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);
        // Activar Toolbar
        setSupportActionBar(findViewById(R.id.toolbar));

        listViewCarrito = findViewById(R.id.listViewCarrito);
        tvTotal = findViewById(R.id.tvTotal);

        // Obtener los productos del carrito (esto es un ejemplo, debes implementar tu lógica para obtener los productos)
        productos = obtenerProductosDelCarrito();

        // Crear adaptador personalizado para el ListView
        adapter = new CarritoAdapter(this, productos);
        listViewCarrito.setAdapter(adapter);

        // Calcular y mostrar el total
        actualizarTotal();

        // Configurar el listener para el botón de comprar
        Button btnComprar = findViewById(R.id.btnComprar);
        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Ingresar a la Vista de ticket
        btnComprar = (Button) findViewById(R.id.btnComprar);
        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Compra", Toast.LENGTH_SHORT).show();
                Log.i("INFO:", "Carrito");
                Intent intent = new Intent(carrito.this, ticket.class);
                startActivity(intent);
            }
        });
    }

    // Método para obtener los productos del carrito
    private List<Productos> obtenerProductosDelCarrito() {
        List<Productos> lista = new ArrayList<>();
        lista.add(new Productos(R.drawable.frutal, "Fruta", "$ 300.00", 1));
        lista.add(new Productos(R.drawable.capuccino, "Capuccino 3 Leches", "$ 450.00", 1));
        lista.add(new Productos(R.drawable.cupcake, "Básico", "$ 20.00", 1));
        return lista;
    }

    // Método para actualizar el total y mostrarlo en el TextView correspondiente
    public void actualizarTotal() {
        total = 0;
        for (Productos producto : productos) {
            // Eliminamos el símbolo de dólar y cualquier espacio en blanco antes de convertir a double
            String precio = producto.getPrecio().replace("$", "").trim();
            total += producto.getCantidad() * Double.parseDouble(precio);
        }
        tvTotal.setText("Total: $" + String.format("%.2f", total));
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Hacer visible el menu
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }
}
