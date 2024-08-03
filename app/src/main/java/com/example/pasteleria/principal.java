package com.example.pasteleria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.PopupMenu;
import androidx.appcompat.view.menu.MenuBuilder;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.util.Log;
import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class principal extends AppCompatActivity {

    private static final String URL_IMG = "https://espaciotecnologicodigital.com/pasteleria/";
    private static final String NAMESPACE = "https://espaciotecnologicodigital.com/SOAPPasteleriaApp/";
    private static final String URL = "https://espaciotecnologicodigital.com/SOAPPasteleriaApp/Pasteleria.php?wsdl";
    private static final String METHOD1 = "ProductosMasVendidos";
    private static final String METHOD2 = "Sugerencias";
    private static final String SOAPACTION1 = "https://espaciotecnologicodigital.com/SOAPPasteleriaApp/Pasteleria.php?method=ProductosMasVendidos";
    private static final String SOAPACTION2 = "https://espaciotecnologicodigital.com/SOAPPasteleriaApp/Pasteleria.php?method=Sugerencias";

    private ViewPager viewPagerMasVendidos;
    private ViewPager viewPagerSugerencias;
    private SliderAdapter sliderAdapterMasVendidos;
    private SliderAdapter sliderAdapterSugerencias;
    private ImageButton imgCategoria1;
    private ImageButton imgCategoria2;
    private ImageButton imgCategoria3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        // Activar Toolbar
        setSupportActionBar(findViewById(R.id.toolbar));

        // Enlazar los ImageButton
        imgCategoria1 = findViewById(R.id.imgCategoria1);
        imgCategoria2 = findViewById(R.id.imgCategoria2);
        imgCategoria3 = findViewById(R.id.imgCategoria3);

// Configurar el OnClickListener para cada ImageButton
        imgCategoria1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("INFO:", "Categoría 1");
                Intent intent = new Intent(principal.this, productos_categoria.class);
                intent.putExtra("categoria", 1); // Agregar el número de categoría
                startActivity(intent);
            }
        });

        imgCategoria2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("INFO:", "Categoría 2");
                Intent intent = new Intent(principal.this, productos_categoria.class);
                intent.putExtra("categoria", 2); // Agregar el número de categoría
                startActivity(intent);
            }
        });

        imgCategoria3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("INFO:", "Categoría 3");
                Intent intent = new Intent(principal.this, productos_categoria.class);
                intent.putExtra("categoria", 3); // Agregar el número de categoría
                startActivity(intent);
            }
        });


        // Configurar los ViewPager
        viewPagerMasVendidos = findViewById(R.id.viewPagerMasVendido);
        viewPagerSugerencias = findViewById(R.id.viewPagerRecomendado);

        // Inicializar adaptadores con la base URL
        sliderAdapterMasVendidos = new SliderAdapter(new ArrayList<>(), URL_IMG);
        sliderAdapterSugerencias = new SliderAdapter(new ArrayList<>(), URL_IMG);

        viewPagerMasVendidos.setAdapter(sliderAdapterMasVendidos);
        viewPagerSugerencias.setAdapter(sliderAdapterSugerencias);

        // Ejecutar las tareas asíncronas
        new ProductosMasVendidosTask().execute();
        new SugerenciasTask().execute();
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
            return true;
        } else if (id == R.id.mC) {
            // Ir a la actividad "Carrito"
            Intent intentCarrito = new Intent(this, carrito.class);
            startActivity(intentCarrito);
            return true;
        } else if (id == R.id.mCP) {
            // Ir a la actividad "Mis Compras"
            return true;
        } else if (id == R.id.mMP) {
            // Ir a la actividad "Menu Principal"
            Toast.makeText(getApplicationContext(), "Ya estas en la opcion", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.mSalir) {
            // Manejar la acción de salir
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    // Asynctask para obtener productos más vendidos
    private class ProductosMasVendidosTask extends AsyncTask<Void, Void, List<Pastel>> {
        @Override
        protected List<Pastel> doInBackground(Void... voids) {
            List<Pastel> pasteles = new ArrayList<>();
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD1);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);
                HttpTransportSE transport = new HttpTransportSE(URL);
                transport.call(SOAPACTION1, envelope);

                // Verifica el tipo de respuesta
                Object response = envelope.getResponse();
                Log.i("SOAP Response", "Response type: " + response.getClass().getSimpleName());

                // Verifica si la respuesta es un String
                if (response instanceof String) {
                    String jsonResponse = (String) response;
                    Log.i("SOAP Response", jsonResponse);

                    JSONArray jsonArray = new JSONArray(jsonResponse);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String nombre = jsonObject.getString("nombre");
                        String imagen = jsonObject.getString("imagen");
                        pasteles.add(new Pastel(nombre, imagen));
                    }
                } else {
                    Log.e("SOAP Response Error", "Unexpected response type");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("SOAP Error", e.getMessage());
            }
            return pasteles;
        }

        @Override
        protected void onPostExecute(List<Pastel> pasteles) {
            if (pasteles.isEmpty()) {
                Log.e("Pasteles Error", "No se recibieron pasteles");
            } else {
                sliderAdapterMasVendidos.setPasteles(pasteles);
            }
        }
    }


    // Asynctask para obtener productos sugeridos
    private class SugerenciasTask extends AsyncTask<Void, Void, List<Pastel>> {
        @Override
        protected List<Pastel> doInBackground(Void... voids) {
            List<Pastel> pasteles = new ArrayList<>();
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD2);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);
                HttpTransportSE transport = new HttpTransportSE(URL);
                transport.call(SOAPACTION2, envelope);

                // Verifica el tipo de respuesta
                Object response = envelope.getResponse();
                Log.i("SOAP Response", "Response type: " + response.getClass().getSimpleName());

                // Verifica si la respuesta es un String
                if (response instanceof String) {
                    String jsonResponse = (String) response;
                    Log.i("SOAP Response", jsonResponse);

                    JSONArray jsonArray = new JSONArray(jsonResponse);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String nombre = jsonObject.getString("nombre");
                        String imagen = jsonObject.getString("imagen");
                        pasteles.add(new Pastel(nombre, imagen));
                    }
                } else {
                    Log.e("SOAP Response Error", "Unexpected response type");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("SOAP Error", e.getMessage());
            }
            return pasteles;
        }

        @Override
        protected void onPostExecute(List<Pastel> pasteles) {
            if (pasteles.isEmpty()) {
                Log.e("Pasteles Error", "No se recibieron pasteles");
            } else {
                sliderAdapterSugerencias.setPasteles(pasteles);
            }
        }
    }

}
