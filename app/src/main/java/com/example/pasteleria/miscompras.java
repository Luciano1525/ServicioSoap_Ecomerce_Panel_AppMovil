package com.example.pasteleria;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class miscompras extends AppCompatActivity {
    private static final String NAMESPACE = "https://espaciotecnologicodigital.com/SOAPPasteleriaApp/";
    private static final String WSDL_URL = "https://espaciotecnologicodigital.com/SOAPPasteleriaApp/Pasteleria.php?wsdl";
    private static final String METHOD = "misCompras";
    private static final String URL_IMG = "https://espaciotecnologicodigital.com/pasteleria/";
    private ListView LVPC;
    private List<Compra> lst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miscompras);

        LVPC = findViewById(R.id.LVPC);
        lst = new ArrayList<>();

        // Obtener el nombre de usuario desde SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String usuario1 = sharedPreferences.getString("usuario", "defaultUsuario");

        // Llamar al AsyncTask para recuperar las compras del usuario
        new ObtenerComprasTask().execute(usuario1);
    }

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
            Toast.makeText(getApplicationContext(), "Ya estás en la opción", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.mMP) {
            // Ir a la actividad "Principal"
            Intent intenPrincipal = new Intent(this, principal.class);
            startActivity(intenPrincipal);
            return true;
        } else if (id == R.id.mSalir) {
            // Manejar la acción de salir
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    // AsyncTask para recuperar las compras del usuario desde el servicio web
    private class ObtenerComprasTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String usuario = params[0];
            String SOAP_ACTION = "https://espaciotecnologicodigital.com/SOAPPasteleriaApp/misCompras";

            // Crear el objeto SOAP y configurar el sobre
            SoapObject request = new SoapObject(NAMESPACE, METHOD);
            request.addProperty("usuario", usuario);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE transport = new HttpTransportSE(WSDL_URL);
            try {
                transport.call(SOAP_ACTION, envelope);
                return (String) envelope.getResponse();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    // Formato original de la fecha
                    SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    // Formato deseado para la salida
                    SimpleDateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String nombre = jsonObject.getString("nombre");
                        String precio = jsonObject.getString("total");
                        String fechaStr = jsonObject.getString("fecha");
                        String imagen = URL_IMG + jsonObject.getString("imagen");

                        // Convertir la fecha al formato deseado
                        Date fecha = originalFormat.parse(fechaStr);
                        String fechaFormateada = targetFormat.format(fecha);

                        Compra compra = new Compra(nombre, precio, fechaFormateada, imagen);
                        lst.add(compra);
                    }

                    if (lst.isEmpty()) {
                        // Mostrar mensaje si no hay compras
                        Toast.makeText(miscompras.this, "No has realizado ninguna compra", Toast.LENGTH_SHORT).show();
                    } else {
                        // Configurar el adaptador y mostrar las compras
                        CompraAdapter adapter = new CompraAdapter(miscompras.this, lst);
                        LVPC.setAdapter(adapter);
                    }

                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(miscompras.this, "Error al procesar las compras", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(miscompras.this, "Error al recuperar las compras", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
