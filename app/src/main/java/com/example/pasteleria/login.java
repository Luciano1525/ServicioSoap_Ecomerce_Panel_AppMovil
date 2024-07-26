package com.example.pasteleria;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.xmlpull.v1.XmlPullParserException;

public class login extends AppCompatActivity {
    private EditText txtUsuario, txtContraseña;
    private String respuest = "";
    private Button btnIngresar;
    private TextView tvRegistrar;
    private String usuario1, contraseña;
    private String resultString;
    private String mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        setSupportActionBar(findViewById(R.id.toolbar));

        txtUsuario = findViewById(R.id.txtUsuario);
        txtContraseña = findViewById(R.id.txtContraseña);
        btnIngresar = findViewById(R.id.btnIngresar);
        tvRegistrar = findViewById(R.id.tvRegistrar);

        // Registro del Usuario
        tvRegistrar.setOnClickListener(view -> {
            Intent intent = new Intent(login.this, register.class);
            startActivity(intent);
        });

        // Login del Usuario
        btnIngresar.setOnClickListener(view -> {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());
            executor.execute(() -> {
                respuest = Login();
                handler.post(() -> {
                    try {
                        JSONObject userJson = new JSONObject(respuest);
                        String usuarioJson = userJson.getString("usuario");
                        String contrasenaJson = userJson.getString("contraseña");
                        String nombreUsuario = userJson.getString("nombre");

                        // Obtener los valores de los EditText
                        usuario1 = txtUsuario.getText().toString().trim();
                        contraseña = txtContraseña.getText().toString().trim();

                        // Validar usuario y contraseña
                        if (usuario1.equals(usuarioJson) && contraseña.equals(contrasenaJson)) {
                            Datos dbManager = new Datos(login.this);
                            dbManager.open();
                            dbManager.insertUsuarioCliente(userJson);
                            dbManager.close();
                            String mensajeBienvenida = "Login exitoso. Bienvenido " + nombreUsuario;
                            Toast.makeText(getApplicationContext(), mensajeBienvenida, Toast.LENGTH_SHORT).show();

                            // Navegar a la actividad principal
                            Intent intent = new Intent(login.this, principal.class);
                            startActivity(intent);
                            finish(); // Finalizar la actividad de login
                        } else {
                            Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Debes completar los campos", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        });


    }

    public String Login() {
        String METHOD = "selectUsuario";
        String NAMESPACE = "https://espaciotecnologicodigital.com/SOAPPasteleriaApp/";
        String SOAPACTION = "https://espaciotecnologicodigital.com/SOAPPasteleriaApp/Pasteleria.php?method=selectUsuario";
        final String URL = "https://espaciotecnologicodigital.com/SOAPPasteleriaApp/Pasteleria.php?wsdl";
        SoapObject request = new SoapObject(NAMESPACE, METHOD);
        request.addProperty("usuario", txtUsuario.getText().toString().trim());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        envelope.setOutputSoapObject(request);
        HttpTransportSE transportSE = new HttpTransportSE(URL);
        try {
            transportSE.call(SOAPACTION, envelope);
            SoapObject response = (SoapObject) envelope.bodyIn;
            String result = (String) response.getProperty("selectUsuarioReturn");
            return result;
        } catch (IOException | XmlPullParserException e) {
            return "Error: " + e.getMessage();
        }
    }

}
