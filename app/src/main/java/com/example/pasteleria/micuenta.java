package com.example.pasteleria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class micuenta extends AppCompatActivity {

    private EditText txtNombreCompleto, txtDireccion, txtTelefono, txtCorreo, txtUsuari, txtContra, txtVeContra;
    private Spinner spMunicipio, spinnerGenero;
    private TextView txtEstado;
    private GridLayout dlCategorias;
    private Button btnActualizar;
    private CheckBox cbPasteles, cbTartas, cbCupCakes, cbFrutas, cbTresLeches, cbGalletas, cbFondan, cbMenrengue, cbChantilly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_micuenta);

        // Initialize views
        btnActualizar = findViewById(R.id.btnActualizar);
        txtNombreCompleto = findViewById(R.id.txtNombreCompleto);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtUsuari = findViewById(R.id.txtUsuari);
        txtContra = findViewById(R.id.txtContra);
        txtVeContra = findViewById(R.id.txtVeContra);
        spMunicipio = findViewById(R.id.spMunicipio);
        spinnerGenero = findViewById(R.id.spinnerGenero);
        dlCategorias = findViewById(R.id.dlCategorias);
        cbPasteles = findViewById(R.id.cbPasteles);
        cbTartas = findViewById(R.id.cbTartas);
        cbCupCakes = findViewById(R.id.cbCupCakes);
        cbFrutas = findViewById(R.id.cbFrutas);
        cbTresLeches = findViewById(R.id.cbTresLeches);
        cbGalletas = findViewById(R.id.cbGalletas);
        cbFondan = findViewById(R.id.cbFondan);
        cbMenrengue = findViewById(R.id.cbMenrengue);
        cbChantilly = findViewById(R.id.cbChantilly);
        txtEstado = findViewById(R.id.etEstado);

        // Get user from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String usuario = sharedPreferences.getString("usuario", "");

        // Fetch user data from SOAP web service using AsyncTask
        new FetchUserDataTask().execute(usuario);


        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActualizarInformacion();
                // Volver a cargar la actividad
                Intent intent = getIntent();
                finish(); // Termina la actividad actual
                startActivity(intent); // Inicia la misma actividad de nuevo
            }
        });


        //FIN
    }

    private class FetchUserDataTask extends AsyncTask<String, Void, String> {

        private static final String NAMESPACE = "https://espaciotecnologicodigital.com/SOAPPasteleriaApp/";
        private static final String URL = "https://espaciotecnologicodigital.com/SOAPPasteleriaApp/Pasteleria.php?wsdl";
        private static final String SOAP_ACTION = "https://espaciotecnologicodigital.com/SOAPPasteleriaApp/Pasteleria.php?method=selectUsuario";
        private static final String METHOD_NAME = "selectUsuario";

        @Override
        protected String doInBackground(String... params) {
            String usuario = params[0];
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("usuario", usuario);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);
                envelope.implicitTypes = true;

                HttpTransportSE transport = new HttpTransportSE(URL);
                transport.call(SOAP_ACTION, envelope);

                // Obtener respuesta como String en lugar de SoapObject
                return envelope.getResponse().toString(); // Este es el cambio clave

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    // Parse JSON response
                    Gson gson = new Gson();
                    JsonObject jsonResponse = JsonParser.parseString(result).getAsJsonObject();

                    // Set user data to UI components
                    txtNombreCompleto.setText(jsonResponse.get("nombre").getAsString());
                    txtDireccion.setText(jsonResponse.get("direccion").getAsString());
                    txtTelefono.setText(jsonResponse.get("telefono").getAsString());
                    txtCorreo.setText(jsonResponse.get("correo").getAsString());
                    txtUsuari.setText(jsonResponse.get("usuario").getAsString());
                    txtContra.setText(jsonResponse.get("contraseña").getAsString());
                    txtVeContra.setText(jsonResponse.get("contraseña").getAsString());

                    // Set Spinner values
                    String municipio = jsonResponse.get("municipio").getAsString();
                    String genero = jsonResponse.get("genero").getAsString();
                    setSpinnerSelection(spMunicipio, municipio);
                    setSpinnerSelection(spinnerGenero, genero);

                    // Set CheckBox values
                    String[] preferencias = jsonResponse.get("preferencias").getAsString().split(" ");
                    setCheckboxes(preferencias);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(micuenta.this, "Error al procesar los datos", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(micuenta.this, "Error al recuperar los datos", Toast.LENGTH_SHORT).show();
            }
        }

        private void setSpinnerSelection(Spinner spinner, String value) {
            ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spinner.getAdapter();
            int position = adapter.getPosition(value);
            if (position >= 0) {
                spinner.setSelection(position);
            }
        }

        private void setCheckboxes(String[] preferencias) {
            for (String pref : preferencias) {
                switch (pref) {
                    case "Pasteles":
                        cbPasteles.setChecked(true);
                        break;
                    case "Tartas":
                        cbTartas.setChecked(true);
                        break;
                    case "CupCakes":
                        cbCupCakes.setChecked(true);
                        break;
                    case "Frutas":
                        cbFrutas.setChecked(true);
                        break;
                    case "Tres Leches":
                        cbTresLeches.setChecked(true);
                        break;
                    case "Galletas":
                        cbGalletas.setChecked(true);
                        break;
                    case "Fondan":
                        cbFondan.setChecked(true);
                        break;
                    case "Menrengue":
                        cbMenrengue.setChecked(true);
                        break;
                    case "Chantilly":
                        cbChantilly.setChecked(true);
                        break;
                }
            }
        }
    }


    private void ActualizarInformacion() {
        // Recuperar el usuario de Shared Preferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String usuarioGuardado = sharedPreferences.getString("usuario", "");

        // Recuperar el estado del municipio del TextView o establecer valor por defecto
        String estadoMunicipio = txtEstado.getText().toString().replace("Estado: ", "");
        if (estadoMunicipio.isEmpty()) {
            estadoMunicipio = "Yucatán"; // Valor por defecto
        }

        // Iniciar la tarea de actualización
        new UpdateUserTask().execute(
                txtNombreCompleto.getText().toString(),
                txtDireccion.getText().toString(),
                txtTelefono.getText().toString(),
                spMunicipio.getSelectedItem().toString(),
                txtUsuari.getText().toString(),
                txtCorreo.getText().toString(),
                spinnerGenero.getSelectedItem().toString(),
                usuarioGuardado, // Usa el usuario guardado para la actualización
                txtContra.getText().toString(),
                getCheckedPreferences()
        );
    }



    private class UpdateUserTask extends AsyncTask<String, Void, Boolean> {

        private static final String NAMESPACE = "https://espaciotecnologicodigital.com/SOAPPasteleriaApp/";
        private static final String URL = "https://espaciotecnologicodigital.com/SOAPPasteleriaApp/Pasteleria.php?wsdl";
        private static final String SOAP_ACTION = ""; // Puedes dejarlo vacío si no es necesario
        private static final String METHOD_NAME = "updateUsuario";

        @Override
        protected Boolean doInBackground(String... params) {
            if (params.length < 10) {
                Log.e("UPDATE_TASK", "No hay suficientes parámetros para la actualización");
                return false;
            }

            try {
                // Crear la solicitud SOAP
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("nombre", params[0]);
                request.addProperty("direccion", params[1]);
                request.addProperty("telefono", params[2]);
                request.addProperty("municipio", params[3]);
                request.addProperty("estado_municipio", params[4]);
                request.addProperty("correo", params[5]);
                request.addProperty("genero", params[6]);
                request.addProperty("usuario", params[7]); // Identificador para la actualización
                request.addProperty("contraseña", params[8]);
                request.addProperty("preferencias", params[9]);

                // Configurar el sobre de la solicitud SOAP
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);
                envelope.implicitTypes = true;

                // Enviar la solicitud
                HttpTransportSE transport = new HttpTransportSE(URL);
                transport.call(SOAP_ACTION, envelope);

                // Obtener la respuesta
                Object response = envelope.getResponse();
                if (response instanceof Boolean) {
                    boolean result = (Boolean) response;
                    Log.d("UPDATE_TASK", "Response: " + result);
                    return result;
                } else {
                    Log.e("SOAP_ERROR", "Respuesta inesperada del servicio SOAP");
                    return false;
                }

            } catch (Exception e) {
                Log.e("SOAP_ERROR", "Error en la comunicación con el servicio SOAP", e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(micuenta.this, "Información Actualizada", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(micuenta.this, "Error al actualizar la información", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private String getCheckedPreferences() {
        StringBuilder preferencias = new StringBuilder();
        if (cbPasteles.isChecked()) preferencias.append("Pasteles ");
        if (cbTartas.isChecked()) preferencias.append("Tartas ");
        if (cbCupCakes.isChecked()) preferencias.append("CupCakes ");
        if (cbFrutas.isChecked()) preferencias.append("Frutas ");
        if (cbTresLeches.isChecked()) preferencias.append("TresLeches ");
        if (cbGalletas.isChecked()) preferencias.append("Galletas ");
        if (cbFondan.isChecked()) preferencias.append("Fondan ");
        if (cbMenrengue.isChecked()) preferencias.append("Menrengue ");
        if (cbChantilly.isChecked()) preferencias.append("Chantilly ");

        // Eliminar el último espacio si es necesario
        return preferencias.toString().trim();
    }



    // ----------
}
