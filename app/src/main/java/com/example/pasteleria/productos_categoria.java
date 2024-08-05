package com.example.pasteleria;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import android.widget.AdapterView;
import java.util.ArrayList;
import java.util.List;

public class productos_categoria extends AppCompatActivity {
    private static final String NAMESPACE = "https://espaciotecnologicodigital.com/SOAPPasteleriaApp/";
    private static final String WSDL_URL = "https://espaciotecnologicodigital.com/SOAPPasteleriaApp/Pasteleria.php?wsdl";
    private static final String METHOD = "selectProductos";
    private static final String URL_IMG = "https://espaciotecnologicodigital.com/pasteleria/";
    private int categoriaId;
    private ListView LVP;
    private List<Contacto> lst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos_categoria);
        // Activar Toolbar
        setSupportActionBar(findViewById(R.id.toolbar));

        LVP = findViewById(R.id.LVP);
        lst = new ArrayList<>();

        // Recupera el ID de la categoría desde el Intent
        Intent intent = getIntent();
        categoriaId = intent.getIntExtra("categoria", -1);

            // Ejecuta la tarea para obtener los productos
            new FetchProductosTask(categoriaId).execute();

        // Dentro del OnItemClickListener del ListView
        LVP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el ítem seleccionado como un objeto Contacto
                Contacto selectedItem = (Contacto) parent.getItemAtPosition(position);

                // Obtener el nombre del producto
                String selectedItemName = selectedItem.getNombre();

                // Navegar a la actividad del detalle
                Intent intent = new Intent(productos_categoria.this, producto.class);
                intent.putExtra("nombre_producto", selectedItemName); // Pasar el nombre del producto
                startActivity(intent);
            }
        });


    }

    private class FetchProductosTask extends AsyncTask<Void, Void, String> {
        private int categoriaId;

        // Constructor que recibe el id de la categoría
        public FetchProductosTask(int categoriaId) {
            this.categoriaId = categoriaId;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                // Crear la solicitud
                SoapObject request = new SoapObject(NAMESPACE, METHOD);

                // Agregar el parámetro idCategoria
                request.addProperty("idCategoria", String.valueOf(categoriaId));

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);

                // Configurar el transporte
                HttpTransportSE transport = new HttpTransportSE(WSDL_URL);
                transport.call(NAMESPACE + METHOD, envelope);

                // Obtener la respuesta como una cadena
                return envelope.getResponse().toString();
            } catch (Exception e) {
                Log.e("SOAP Error", "Error: " + e.getMessage(), e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    // Procesar la respuesta
                    JSONArray jsonArray = new JSONArray(result);

                    // Limpiar lista
                    lst.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject producto = jsonArray.getJSONObject(i);

                        String nombre = producto.getString("nombre");
                        String estado = producto.getInt("estado") == 1 ? "Activo" : "Inactivo";
                        String precio = "$ " + producto.getString("precio");
                        String imagen = URL_IMG + producto.getString("imagen"); // Usa URL_IMG aquí

                        lst.add(new Contacto(nombre, estado, precio, imagen));
                    }
                    CustomAdapter adapter = new CustomAdapter(productos_categoria.this, lst);
                    LVP.setAdapter(adapter);
                } catch (JSONException e) {
                    Log.e("JSON Error", "Error parsing JSON: " + e.getMessage(), e);
                }
            } else {
                Log.i("SOAP Response", "No se recibieron productos");
            }
        }
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
