package com.example.pasteleria;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import java.util.ArrayList;
import java.util.List;

public class carrito extends AppCompatActivity {
    ListView listViewCarrito;
    List<Productos> productos;
    double total = 0;
    TextView tvTotal, tvUsuario;
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
        tvUsuario = findViewById(R.id.tvUsuario);

        //Recupera el Usuario cuando se logeo
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String usuario1 = sharedPreferences.getString("usuario", "defaultUsuario");
        tvUsuario.setText(usuario1);

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

        // Creacion de objeto de enlace a las base de datos
        TablaDetalle oper = new TablaDetalle(this, "operacion", null, 1);
        SQLiteDatabase db = oper.getWritableDatabase();

        // Realizar una consulta SQL para obtener los datos deseados
        String[] columnas = {"nombre", "cantidad", "precio"}; // Columnas que queremos obtener
        Cursor cursor = db.query("detalle_temp", columnas, null, null, null, null, null);

        // Iterar sobre el cursor y recuperar los datos
        if (cursor.moveToFirst()) {
            do {
                // Obtener los valores de las columnas
                String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                String cantidad = cursor.getString(cursor.getColumnIndex("cantidad"));
                String precio = cursor.getString(cursor.getColumnIndex("precio"));

                // Convertir la cantidad a un entero
                int cantidadInt = Integer.parseInt(cantidad);

                // Crear un objeto Productos con los datos recuperados
                Productos producto = new Productos(R.drawable.frutal, nombre, "$ "+ precio, cantidadInt);

                // Agregar el objeto a la lista
                lista.add(producto);

            } while (cursor.moveToNext());
        }

        // Cerrar el cursor y la base de datos
        cursor.close();
        db.close();

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



    public void actualizarCantidadEnBaseDeDatos(String nombreProducto, int nuevaCantidad) {
        // Crear una instancia de la base de datos
        TablaDetalle oper = new TablaDetalle(this, "operacion", null, 1);
        SQLiteDatabase db = oper.getWritableDatabase();

        // Preparar el contenido para la actualización
        ContentValues values = new ContentValues();
        values.put("cantidad", nuevaCantidad);

        // Actualizar la cantidad del producto en la base de datos
        String whereClause = "nombre = ?";
        String[] whereArgs = { nombreProducto };
        db.update("detalle_temp", values, whereClause, whereArgs);

        // Cerrar la base de datos
        db.close();
    }


    public void eliminarProductoDeBaseDeDatos(String nombreProducto) {
        // Crear una instancia de la base de datos
        TablaDetalle oper = new TablaDetalle(this, "operacion", null, 1);
        SQLiteDatabase db = oper.getWritableDatabase();

        // Eliminar el producto de la base de datos
        String whereClause = "nombre=?";
        String[] whereArgs = {nombreProducto};
        db.delete("detalle_temp", whereClause, whereArgs);

        // Cerrar la base de datos
        db.close();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Hacer visible el menu
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }
}
