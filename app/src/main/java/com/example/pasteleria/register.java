package com.example.pasteleria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import java.util.Random;

public class register extends AppCompatActivity {
    private Spinner spinnerGenero, spMunicipio;
    private EditText txtNombreCompleto, txtDireccion, txtTelefono, txtCorreo, txtUsuario, txtContra, txtVeContra;
    private CheckBox cbTerminos, cbNotificacion;
    private CheckBox cbPasteles, cbTartas, cbCupCakes, cbFrutas, cbTresLeches, cbGalletas, cbFondan, cbMenrengue, cbChantilly;
    private Button btnRegistrar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Configuración de los Spinners y EditTexts
        spinnerGenero = findViewById(R.id.spinnerGenero);
        spMunicipio = findViewById(R.id.spMunicipio);
        txtNombreCompleto = findViewById(R.id.txtNombreCompleto);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtUsuario = findViewById(R.id.txtUsuari);
        txtContra = findViewById(R.id.txtContra);
        txtVeContra = findViewById(R.id.txtVeContra);

        // CheckBoxes de categorías
        cbPasteles = findViewById(R.id.cbPasteles);
        cbTartas = findViewById(R.id.cbTartas);
        cbCupCakes = findViewById(R.id.cbCupCakes);
        cbFrutas = findViewById(R.id.cbFrutas);
        cbTresLeches = findViewById(R.id.cbTresLeches);
        cbGalletas = findViewById(R.id.cbGalletas);
        cbFondan = findViewById(R.id.cbFondan);
        cbMenrengue = findViewById(R.id.cbMenrengue);
        cbChantilly = findViewById(R.id.cbChantilly);

        // CheckBoxes de términos y notificaciones
        cbTerminos = findViewById(R.id.cbTerminos);
        cbNotificacion = findViewById(R.id.cbNotificacion);

        // Configuración del Spinner de Género
        ArrayAdapter<CharSequence> adapterGenero = ArrayAdapter.createFromResource(this,
                R.array.genero_array, android.R.layout.simple_spinner_item);
        adapterGenero.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenero.setAdapter(adapterGenero);

        // Configuración del Spinner de Municipio
        ArrayAdapter<CharSequence> adapterMunicipio = ArrayAdapter.createFromResource(this,
                R.array.municipios_yucatan, android.R.layout.simple_spinner_item);
        adapterMunicipio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMunicipio.setAdapter(adapterMunicipio);

        // Botón Registrar
        btnRegistrar2 = findViewById(R.id.btnRegistrar2);
        btnRegistrar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarCliente();
            }
        });
    }

    private void registrarCliente() {
        // Validar que todos los campos estén completos
        if (txtNombreCompleto.getText().toString().isEmpty() ||
                txtDireccion.getText().toString().isEmpty() ||
                txtTelefono.getText().toString().isEmpty() ||
                txtCorreo.getText().toString().isEmpty() ||
                txtUsuario.getText().toString().isEmpty() ||
                txtContra.getText().toString().isEmpty() ||
                txtVeContra.getText().toString().isEmpty() ||
                spinnerGenero.getSelectedItemPosition() == 0 ||
                spMunicipio.getSelectedItemPosition() == 0 ||
                !cbTerminos.isChecked()) {
            Toast.makeText(this, "Por favor complete todos los campos y acepte los términos y condiciones.", Toast.LENGTH_LONG).show();
            return;
        }

        // Validar que las contraseñas coincidan
        if (!txtContra.getText().toString().equals(txtVeContra.getText().toString())) {
            Toast.makeText(this, "Las contraseñas no coinciden.", Toast.LENGTH_LONG).show();
            return;
        }

        // Construir la cadena de preferencias
        StringBuilder preferencias = new StringBuilder();
        if (cbPasteles.isChecked()) preferencias.append("Pasteles ");
        if (cbTartas.isChecked()) preferencias.append("Tartas ");
        if (cbCupCakes.isChecked()) preferencias.append("CupCakes ");
        if (cbFrutas.isChecked()) preferencias.append("Frutas ");
        if (cbTresLeches.isChecked()) preferencias.append("Tres Leches ");
        if (cbGalletas.isChecked()) preferencias.append("Galletas ");
        if (cbFondan.isChecked()) preferencias.append("Fondan ");
        if (cbMenrengue.isChecked()) preferencias.append("Menrengue ");
        if (cbChantilly.isChecked()) preferencias.append("Chantilly ");

        // Eliminar el último espacio
        if (preferencias.length() > 0) {
            preferencias.setLength(preferencias.length() - 1);
        }

        // Generar un número aleatorio de 6 dígitos para el RUC
        Random random = new Random();
        int ruc = 100000 + random.nextInt(900000);

        // Datos del cliente
        String nombre = txtNombreCompleto.getText().toString();
        String direccion = txtDireccion.getText().toString();
        String telefono = txtTelefono.getText().toString();
        String municipio = spMunicipio.getSelectedItem().toString();
        String estadoMunicipio = "Yucatán";
        String correo = txtCorreo.getText().toString();
        String genero = spinnerGenero.getSelectedItem().toString();
        String usuario = txtUsuario.getText().toString();
        String contraseña = txtContra.getText().toString();
        String estado = "Activo"; // O el valor que corresponda

        // Ejecutar la tarea asíncrona para llamar al servicio web
        new RegistrarClienteTask().execute(String.valueOf(ruc), nombre, direccion, telefono, municipio, estadoMunicipio, correo, genero, usuario, contraseña, preferencias.toString(), estado);
    }

    private class RegistrarClienteTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            String ruc = params[0];
            String nombre = params[1];
            String direccion = params[2];
            String telefono = params[3];
            String municipio = params[4];
            String estadoMunicipio = params[5];
            String correo = params[6];
            String genero = params[7];
            String usuario = params[8];
            String contraseña = params[9];
            String preferencias = params[10];
            String estado = params[11];

            String NAMESPACE = "https://espaciotecnologicodigital.com/SOAPPasteleriaApp/";
            String URL = "https://espaciotecnologicodigital.com/SOAPPasteleriaApp/Pasteleria.php?wsdl";
            String METHOD_NAME = "insertarCliente";
            String SOAP_ACTION  = "https://espaciotecnologicodigital.com/SOAPPasteleriaApp/Pasteleria.php?method=insertarCliente";

            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("ruc", ruc);
                request.addProperty("nombre", nombre);
                request.addProperty("direccion", direccion);
                request.addProperty("telefono", telefono);
                request.addProperty("municipio", municipio);
                request.addProperty("estado_municipio", estadoMunicipio);
                request.addProperty("correo", correo);
                request.addProperty("genero", genero);
                request.addProperty("usuario", usuario);
                request.addProperty("contraseña", contraseña);
                request.addProperty("preferencias", preferencias);
                request.addProperty("estado", estado);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);
                HttpTransportSE transport = new HttpTransportSE(URL);
                transport.call(SOAP_ACTION, envelope);

                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                String responseString = response.toString().trim();

                // Log de respuesta para depuración
                Log.d("SOAP_RESPONSE", "Response: " + responseString);

                return "true".equalsIgnoreCase(responseString);
            } catch (Exception e) {
                Log.e("SOAP_ERROR", e.getMessage(), e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(register.this, "Registro exitoso", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(register.this, login.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(register.this, "Registro exitoso", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(register.this, login.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
