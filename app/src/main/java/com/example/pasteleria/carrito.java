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
        // Hacer visible el menú
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }
}
