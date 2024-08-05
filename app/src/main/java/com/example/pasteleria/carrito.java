package com.example.pasteleria;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import android.os.AsyncTask;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.serialization.PropertyInfo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class carrito extends AppCompatActivity {
    ListView listViewCarrito;
    List<Productos> productos;
    double total = 0;
    TextView tvTotal, tvUsuario;
    CarritoAdapter adapter;
    private Button btnComprar;
    private static final String NAMESPACE = "https://espaciotecnologicodigital.com/SOAPPasteleriaApp/";
    private static final String WSDL_URL = "https://espaciotecnologicodigital.com/SOAPPasteleriaApp/Pasteleria.php?wsdl";
    private static final String METHOD = "selectProductoPorNombre"; // Método SOAP para obtener producto por nombre
    private static final String URL_IMG = "https://espaciotecnologicodigital.com/pasteleria/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);
        // Activar Toolbar
        setSupportActionBar(findViewById(R.id.toolbar));

        listViewCarrito = findViewById(R.id.listViewCarrito);
        tvTotal = findViewById(R.id.tvTotal);
        tvUsuario = findViewById(R.id.tvUsuario);
        btnComprar = findViewById(R.id.btnComprar);

        // Recuperar el Usuario cuando se logeó
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String usuario1 = sharedPreferences.getString("usuario", "defaultUsuario");
        tvUsuario.setText(usuario1);

        // Obtener los productos del carrito
        productos = obtenerProductosDelCarrito();

        // Crear adaptador personalizado para el ListView
        adapter = new CarritoAdapter(this, productos);
        listViewCarrito.setAdapter(adapter);

        // Calcular y mostrar el total
        actualizarTotal();

        // Configurar el listener para el botón de comprar
        btnComprar = findViewById(R.id.btnComprar);
        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener una instancia de SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MiPreferencias", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("dato", 1);
                editor.apply(); // o editor.commit();
                mostrarProductos();

                Toast.makeText(getApplicationContext(), "Compra", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(carrito.this, ticket.class);
                startActivity(intent);
            }
        });

        // Ejecutar ObtenerImagenTask para cada producto
        for (Productos producto : productos) {
            new ObtenerImagenTask(producto.getNombre()).execute();
        }
    }

    // Método para obtener los productos del carrito
    private List<Productos> obtenerProductosDelCarrito() {
        List<Productos> lista = new ArrayList<>();

        // Creación de objeto de enlace a la base de datos
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
                Productos producto = new Productos(null, nombre, "$ " + precio, cantidadInt); // Inicialmente sin URL de imagen

                // Agregar el objeto a la lista
                lista.add(producto);

            } while (cursor.moveToNext());
        }

        // Cerrar el cursor y la base de datos
        cursor.close();
        db.close();

        return lista;
    }

    // AsyncTask para obtener la imagen del producto
    private class ObtenerImagenTask extends AsyncTask<String, Void, String> {
        private String nombreProducto;

        public ObtenerImagenTask(String nombreProducto) {
            this.nombreProducto = nombreProducto;
        }

        @Override
        protected String doInBackground(String... params) {
            if (params.length == 0) {
                return null; // No hay parámetros para enviar
            }

            String urlImagen = "";
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD);
                PropertyInfo nombreProperty = new PropertyInfo();
                nombreProperty.setName("nombreProducto");
                nombreProperty.setValue(nombreProducto);
                nombreProperty.setType(String.class);
                request.addProperty(nombreProperty);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(WSDL_URL);
                androidHttpTransport.call(NAMESPACE + METHOD, envelope);

                // Obtener la respuesta SOAP y parsear el JSON
                SoapObject response = (SoapObject) envelope.getResponse();
                SoapObject result = (SoapObject) response.getProperty("selectProductoReturn");
                String jsonResponse = result.toString();

                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);

                // Obtener el nombre de la imagen de la respuesta
                String nombreImagen = jsonObject.has("imagen") ? jsonObject.get("imagen").getAsString() : "no_imagen.png";
                // Construir la URL completa de la imagen
                urlImagen = URL_IMG + nombreImagen;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return urlImagen;
        }

        @Override
        protected void onPostExecute(String imagen) {
            // Actualizar la URL de la imagen en la lista de productos
            for (Productos producto : productos) {
                if (producto.getNombre().equals(nombreProducto)) {
                    producto.setImagen(imagen); // Usa setImagen en lugar de setUrlImagen
                    adapter.notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
                    break;
                }
            }
        }
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

        // Obtener el precio del producto
        String[] projection = {"precio"};
        Cursor cursor = db.query("detalle_temp", projection, "nombre = ?", new String[]{nombreProducto}, null, null, null);
        String precio = "";
        if (cursor.moveToFirst()) {
            precio = cursor.getString(cursor.getColumnIndexOrThrow("precio"));
        }
        cursor.close();

        // Calcular el nuevo total
        double precioDouble = Double.parseDouble(precio.replace("$", "").trim());
        double nuevoTotal = nuevaCantidad * precioDouble;

        // Preparar el contenido para la actualización
        ContentValues values = new ContentValues();
        values.put("cantidad", nuevaCantidad);
        values.put("total", nuevoTotal);

        // Actualizar la cantidad y el total del producto en la base de datos
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
        // Hacer visible el menú
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mMC) {
            // Ir a la actividad "Mi Cuenta"
            Intent intentCuenta = new Intent(this, micuenta.class);
            startActivity(intentCuenta);
            return true;
        } else if (id == R.id.mC) {
            // Ir a la actividad "Carrito"
            Toast.makeText(getApplicationContext(), "Ya estas en la opcion", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.mCP) {
            // Ir a la actividad "Mis Compras"
            Intent intentCompras = new Intent(this, miscompras.class);
            startActivity(intentCompras);
            return true;
        } else if (id == R.id.mMP) {
            // Ir a la actividad "Menu Principal"
            Intent intentMenuPrincipal = new Intent(this, principal.class);
            startActivity(intentMenuPrincipal);
            return true;
        } else if (id == R.id.mSalir) {
            // Manejar la acción de salir
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }






    public void mostrarProductos() {
        //Creacion de objeto de enlace a las base de datos
        TablaDetalle oper = new TablaDetalle(this, "operacion", null, 1);

        // Obtener una instancia de lectura de la base de datos
        SQLiteDatabase DetallesBD = oper.getReadableDatabase();

        // Definir una proyección de las columnas que queremos leer
        String[] projection = {
                "id",
                "nombre",
                "cantidad",
                "precio",
                "total",
                "id_producto",
                "id_usuario",
                "estado",
                "tipo",
                "id_cliente"
        };

        // Ejecutar la consulta
        Cursor cursor = DetallesBD.query(
                "detalle_temp",   // La tabla a consultar
                projection,       // Las columnas a devolver
                null,             // No se aplica una cláusula WHERE
                null,             // No se aplican valores para la cláusula WHERE
                null,             // No agrupar las filas
                null,             // No filtrar por grupos de filas
                null              // No aplicar un ordenamiento
        );

        // Moverse a la primera fila del cursor y recorrer todas las filas
        if (cursor.moveToFirst()) {
            do {
                // Leer los datos de cada columna
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                String cantidad = cursor.getString(cursor.getColumnIndexOrThrow("cantidad"));
                String precio = cursor.getString(cursor.getColumnIndexOrThrow("precio"));
                String total = cursor.getString(cursor.getColumnIndexOrThrow("total"));
                String idProducto = cursor.getString(cursor.getColumnIndexOrThrow("id_producto"));
                String idUsuario = cursor.getString(cursor.getColumnIndexOrThrow("id_usuario"));
                String estado = cursor.getString(cursor.getColumnIndexOrThrow("estado"));
                String tipo = cursor.getString(cursor.getColumnIndexOrThrow("tipo"));
                String id_cliente = cursor.getString(cursor.getColumnIndexOrThrow("id_cliente"));

                // Mostrar los datos en el log de la consola
                Log.d("DB", "ID: " + id + ", Nombre: " + nombre + ", Cantidad: " + cantidad + ", Precio: " + precio + ", Total: " + total + ", ID Producto: " + idProducto + ", ID Usuario: " + idUsuario + ", Estado: " + estado + ", Tipo: " + tipo + ", Id Cliente: " + id_cliente);
            } while (cursor.moveToNext());
        }

        // Cerrar el cursor y la base de datos
        cursor.close();
        DetallesBD.close();
    }
}
