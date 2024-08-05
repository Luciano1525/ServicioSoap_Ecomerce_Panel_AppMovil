package com.example.pasteleria;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.serialization.PropertyInfo;

import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.Cursor;

public class producto extends AppCompatActivity {
    private static final String NAMESPACE = "https://espaciotecnologicodigital.com/SOAPPasteleriaApp/";
    private static final String WSDL_URL = "https://espaciotecnologicodigital.com/SOAPPasteleriaApp/Pasteleria.php?wsdl";
    private static final String METHOD = "selectProducto";
    private static final String URL_IMG = "https://espaciotecnologicodigital.com/pasteleria/";
    private static final String METHOD_USUARIO = "selectUsuario";
    private static final String SOAPACTION_USUARIO = "https://espaciotecnologicodigital.com/SOAPPasteleriaApp/Pasteleria.php?method=selectUsuario";

    private Button btnComprar, btnAgregarCarrito, btnDisminuirCantidad, btnAumentarCantidad;
    private TextView tvCantidad, tvPrecioProducto, tvNombreProducto, tvCategoriaProducto, tvDescripcionProducto, tvIdProducto, tvIdCliente;
    private ImageView ivProducto;

    private int cantidad = 1;
    private double precioProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
        setSupportActionBar(findViewById(R.id.toolbar));

        btnComprar = findViewById(R.id.btnComprar);
        btnAgregarCarrito = findViewById(R.id.btnAgregarCarrito);
        btnDisminuirCantidad = findViewById(R.id.btnDisminuirCantidad);
        btnAumentarCantidad = findViewById(R.id.btnAumentarCantidad);
        tvCantidad = findViewById(R.id.tvCantidad);
        tvPrecioProducto = findViewById(R.id.tvPrecioProducto);
        tvNombreProducto = findViewById(R.id.tvNombreProducto);
        tvCategoriaProducto = findViewById(R.id.tvCategoriaProducto);
        tvDescripcionProducto = findViewById(R.id.tvDescripcionProducto);
        ivProducto = findViewById(R.id.ivProducto);
        tvIdProducto = findViewById(R.id.tvIdProducto);
        tvIdCliente = findViewById(R.id.tvIdCliente);

        // Recupera el Usuario cuando se logeo
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String usuario1 = sharedPreferences.getString("usuario", "defaultUsuario");

        // Creacion de objeto de enlace a las base de datos
        TablaDetalle oper = new TablaDetalle(this, "operacion", null, 1);

        // Obtener el nombre del producto del Intent
        Intent intent = getIntent();
        String nombreProducto = intent.getStringExtra("nombre_producto");

        // Obtener los datos del producto desde el servicio SOAP
        if (nombreProducto != null) {
            new ObtenerProductoTask().execute(nombreProducto);
        } else {
            Toast.makeText(getApplicationContext(), "Nombre del producto no disponible", Toast.LENGTH_SHORT).show();
        }

        btnDisminuirCantidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cantidad > 1) {
                    cantidad--;
                    actualizarCantidadYTotal();
                } else {
                    Toast.makeText(getApplicationContext(), "La cantidad no puede ser menor a 1", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnAumentarCantidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cantidad++;
                actualizarCantidadYTotal();
            }
        });

        new ObtenerProductoIdTask().execute(nombreProducto, usuario1);

        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Obtener una instancia de SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MiPreferencias", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("dato", 0);
                editor.apply(); // o editor.commit();


                String Nombre = tvNombreProducto.getText().toString();
                String Cantidad = tvCantidad.getText().toString();
                int cantidadInt = Integer.parseInt(Cantidad);
                String Precio = tvPrecioProducto.getText().toString();
                double PrecioDou = Double.parseDouble(Precio);
                double TotalInt = cantidadInt * PrecioDou;
                String Total = Double.toString(TotalInt);
                String IdUsuario = "1";
                String Estado = "1";
                String Tipo = "0";

                String IdProductoS = tvIdProducto.getText().toString();
                String IdClienteS = tvIdCliente.getText().toString();

                //Guardar en la base de datos local
                SQLiteDatabase DetallesBD = oper.getWritableDatabase();
                ContentValues registroI = new ContentValues();
                registroI.put("nombre", Nombre);
                registroI.put("cantidad", Cantidad);
                registroI.put("precio", Precio);
                registroI.put("total", Total);
                registroI.put("id_producto", IdProductoS);
                registroI.put("id_usuario", IdUsuario);
                registroI.put("estado", Estado);
                registroI.put("tipo", Tipo);
                registroI.put("id_cliente", IdClienteS);

                DetallesBD.insert("detalle_tempo", null, registroI);
                DetallesBD.close();

                // Llamar a la función para mostrar los productos
                mostrarProductos1();

                Log.i("INFO:", "Compra");
                Intent intent = new Intent(producto.this, ticket.class);
                startActivity(intent);
                finish();
            }
        });

        btnAgregarCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nombreProducto != null && !usuario1.equals("defaultUsuario")) {
                    String Nombre = tvNombreProducto.getText().toString();
                    String Cantidad = tvCantidad.getText().toString();
                    int cantidadInt = Integer.parseInt(Cantidad);
                    String Precio = tvPrecioProducto.getText().toString();
                    double PrecioDou = Double.parseDouble(Precio);
                    double TotalInt = cantidadInt * PrecioDou;
                    String Total = Double.toString(TotalInt);
                    String IdUsuario = "1";
                    String Estado = "1";
                    String Tipo = "1"; // Producto en carrito

                    String IdProductoS = tvIdProducto.getText().toString();
                    String IdClienteS = tvIdCliente.getText().toString();

                    SQLiteDatabase DetallesBD = oper.getWritableDatabase();

                    // Verificar si el producto ya está en el carrito
                    Cursor cursor = DetallesBD.query(
                            "detalle_temp",
                            null,
                            "id_producto=? AND tipo=? AND id_cliente=?",
                            new String[]{IdProductoS, Tipo, IdClienteS},
                            null,
                            null,
                            null
                    );

                    if (cursor != null && cursor.moveToFirst()) {
                        // Si ya está en el carrito, mostrar un mensaje de Toast
                        Toast.makeText(getApplicationContext(), "Este producto ya está en tu carrito", Toast.LENGTH_SHORT).show();
                        cursor.close();
                    } else {
                        // Si no está en el carrito, agregar nuevo registro
                        ContentValues registroI = new ContentValues();
                        registroI.put("nombre", Nombre);
                        registroI.put("cantidad", Cantidad);
                        registroI.put("precio", Precio);
                        registroI.put("total", Total);
                        registroI.put("id_producto", IdProductoS);
                        registroI.put("id_usuario", IdUsuario);
                        registroI.put("estado", Estado);
                        registroI.put("tipo", Tipo);
                        registroI.put("id_cliente", IdClienteS);

                        DetallesBD.insert("detalle_temp", null, registroI);
                        DetallesBD.close();

                        // Llamar a la función para mostrar los productos
                        mostrarProductos();

                        Toast.makeText(getApplicationContext(), "Producto Agregado al Carrito con Éxito", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Nombre del producto o usuario no disponible", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

    private void actualizarCantidadYTotal() {
        tvCantidad.setText(String.valueOf(cantidad));
        double total = cantidad * precioProducto;
        Toast.makeText(getApplicationContext(), "Total: $" + total, Toast.LENGTH_SHORT).show();
    }

    private class ObtenerProductoTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            if (params.length == 0) {
                return null; // No hay parámetros para enviar
            }

            String nombreProducto = params[0]; // Obtén el nombre del producto del parámetro

            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD);

                // Configura el parámetro
                PropertyInfo nombreProperty = new PropertyInfo();
                nombreProperty.setName("nombreProducto"); // Asegúrate de que este nombre coincide con el nombre del parámetro en el servicio SOAP
                nombreProperty.setValue(nombreProducto);
                nombreProperty.setType(String.class);
                request.addProperty(nombreProperty);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(WSDL_URL);
                androidHttpTransport.call(NAMESPACE + METHOD, envelope);

                Object response = envelope.getResponse();
                if (response instanceof SoapObject) {
                    SoapObject result = (SoapObject) response;
                    return result.toString();
                } else {
                    return response.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                Log.d("PRODUCTO_JSON", result); // Log the raw JSON response
                try {
                    Gson gson = new Gson();
                    JsonObject jsonObject = gson.fromJson(result, JsonObject.class);

                    String nombre = jsonObject.has("nombre") ? jsonObject.get("nombre").getAsString() : "No disponible";
                    String categoria = jsonObject.has("categoria") ? jsonObject.get("categoria").getAsString() : "No disponible";
                    String precio = jsonObject.has("precio") ? jsonObject.get("precio").getAsString() : "No disponible";
                    String descripcion = jsonObject.has("descripcion") ? jsonObject.get("descripcion").getAsString() : "No disponible";
                    String imagen = jsonObject.has("imagen") ? jsonObject.get("imagen").getAsString() : "no_imagen.png";

                    String imagenUrl = URL_IMG + imagen;

                    tvNombreProducto.setText(nombre);
                    tvCategoriaProducto.setText("Categoría: " + categoria);
                    tvPrecioProducto.setText(precio);
                    tvDescripcionProducto.setText(descripcion);

                    Glide.with(producto.this)
                            .load(imagenUrl)
                            .into(ivProducto);

                    precioProducto = Double.parseDouble(precio.replace("$", "").trim());
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error al cargar el producto", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "No se encontró el producto", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class ObtenerProductoIdTask extends AsyncTask<String, Void, String[]> {
        @Override
        protected String[] doInBackground(String... params) {
            if (params.length < 2) {
                return null; // No hay parámetros suficientes para enviar
            }

            String nombreProducto = params[0]; // Obtén el nombre del producto del parámetro
            String usuario = params[1]; // Obtén el nombre del usuario del parámetro

            String idProducto = obtenerIdProducto(nombreProducto);
            String idUsuario = obtenerIdUsuario(usuario);

            return new String[]{idProducto, idUsuario};
        }

        private String obtenerIdProducto(String nombreProducto) {
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD);

                // Configura el parámetro
                PropertyInfo nombreProperty = new PropertyInfo();
                nombreProperty.setName("nombreProducto");
                nombreProperty.setValue(nombreProducto);
                nombreProperty.setType(String.class);
                request.addProperty(nombreProperty);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(WSDL_URL);
                androidHttpTransport.call(NAMESPACE + METHOD, envelope);

                Object response = envelope.getResponse();
                if (response instanceof SoapObject) {
                    SoapObject result = (SoapObject) response;
                    return result.toString();
                } else {
                    return response.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private String obtenerIdUsuario(String usuario) {
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_USUARIO);

                // Configura el parámetro
                PropertyInfo usuarioProperty = new PropertyInfo();
                usuarioProperty.setName("usuario");
                usuarioProperty.setValue(usuario);
                usuarioProperty.setType(String.class);
                request.addProperty(usuarioProperty);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(WSDL_URL);
                androidHttpTransport.call(SOAPACTION_USUARIO, envelope);

                Object response = envelope.getResponse();
                if (response instanceof SoapObject) {
                    SoapObject result = (SoapObject) response;
                    return result.toString();
                } else {
                    return response.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] result) {
            if (result != null && result.length == 2) {
                String idProducto = result[0];
                String idUsuario = result[1];

                Log.d("PRODUCTO_JSON", idProducto); // Log the raw JSON response
                Log.d("USUARIO_JSON", idUsuario); // Log the raw JSON response

                try {
                    Gson gson = new Gson();
                    JsonObject jsonObjectProducto = gson.fromJson(idProducto, JsonObject.class);
                    JsonObject jsonObjectUsuario = gson.fromJson(idUsuario, JsonObject.class);

                    int idProductoValue = jsonObjectProducto.has("id") ? jsonObjectProducto.get("id").getAsInt() : -1;
                    int idUsuarioValue = jsonObjectUsuario.has("id") ? jsonObjectUsuario.get("id").getAsInt() : -1;

                    String IdProducto = Integer.toString(idProductoValue);
                    String IdCliente = Integer.toString(idUsuarioValue);

                    if (idProductoValue != -1 && idUsuarioValue != -1) {
                        // Aquí puedes manejar el ID del producto y del usuario
                        tvIdProducto.setText(IdProducto);
                        tvIdCliente.setText(IdCliente);
                    } else {
                        Toast.makeText(getApplicationContext(), "ID de producto o usuario no disponible", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error al cargar el producto o usuario", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "No se encontró el producto o usuario", Toast.LENGTH_SHORT).show();
            }
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

    public void mostrarProductos1() {
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
                "detalle_tempo",   // La tabla a consultar
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

    //Funciones del Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overflow, menu);
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
            Intent intentCarrito = new Intent(this, carrito.class);
            startActivity(intentCarrito);
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

}
