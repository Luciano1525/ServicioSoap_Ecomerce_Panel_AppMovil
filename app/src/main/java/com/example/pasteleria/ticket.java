package com.example.pasteleria;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.HttpTransportSE;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.text.InputType;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.Editable;
import org.ksoap2.serialization.SoapPrimitive;

public class ticket extends AppCompatActivity {
    private Button btnCancelarCompra, btnComprarF1;
    private TextView Usuario, Total, Direcc;
    private ListView LVF1;
    private List<Tickets> productos;
    private TicketAdapter adapter;
    String METHOD = "selectUsuario";
    String NAMESPACE = "https://espaciotecnologicodigital.com/SOAPPasteleriaApp/";
    String SOAPACTION = "https://espaciotecnologicodigital.com/SOAPPasteleriaApp/Pasteleria.php?method=selectUsuario";
    final String URL = "https://espaciotecnologicodigital.com/SOAPPasteleriaApp/Pasteleria.php?wsdl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        setSupportActionBar(findViewById(R.id.toolbar));

        Usuario = findViewById(R.id.Usuario);
        Total = findViewById(R.id.Total);
        Direcc = findViewById(R.id.Direcc);
        LVF1 = findViewById(R.id.LVF1);
        btnCancelarCompra = findViewById(R.id.btnCancelarCompra);
        btnComprarF1 = findViewById(R.id.btnComprarF1);

        TablaDetalle oper = new TablaDetalle(this, "operacion", null, 1);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String usuario1 = sharedPreferences.getString("usuario", "defaultUsuario");
        String UsuarioS = "Usuairo: " + usuario1;
        Usuario.setText(UsuarioS);

        // Obtener la dirección del usuario
        obtenerDireccionUsuario(usuario1);

        SharedPreferences sharedPreferences1 = getSharedPreferences("MiPreferencias", MODE_PRIVATE);
        int dato = sharedPreferences1.getInt("dato", -1);

        if (dato == 1) {
            productos = obtenerProductos1();
            adapter = new TicketAdapter(this, productos);
            LVF1.setAdapter(adapter);

        } else if (dato == 0) {
            productos = obtenerProductos2();
            adapter = new TicketAdapter(this, productos);
            LVF1.setAdapter(adapter);

        } else {
            Toast.makeText(getApplicationContext(), "Venta no Recuperado", Toast.LENGTH_SHORT).show();
        }

        btnCancelarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase DetallesBD = oper.getWritableDatabase();
                int tipo = DetallesBD.delete("detalle_tempo", "tipo = 0", null);
                DetallesBD.close();

                Intent intent = new Intent(ticket.this, principal.class);
                startActivity(intent);
                finish();
            }
        });


        //Realizar compra
        btnComprarF1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear el AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(ticket.this);
                builder.setTitle("Finalizar Compra");

                // Crear un layout para el AlertDialog
                LinearLayout layout = new LinearLayout(ticket.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                // Crear los EditText para los datos de pago
                final EditText cardNumber = new EditText(ticket.this);
                cardNumber.setHint("Número de tarjeta");
                cardNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
                cardNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(19)});
                cardNumber.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() > 0 && s.length() % 5 == 0 && !s.toString().endsWith("-")) {
                            cardNumber.setText(s.toString() + "-");
                            cardNumber.setSelection(cardNumber.getText().length());
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) { }
                });
                layout.addView(cardNumber);

                final EditText cardHolder = new EditText(ticket.this);
                cardHolder.setHint("Nombre del titular");
                layout.addView(cardHolder);

                final EditText expiryDate = new EditText(ticket.this);
                expiryDate.setHint("Fecha de vencimiento (MM/AA)");
                layout.addView(expiryDate);

                final EditText cvv = new EditText(ticket.this);
                cvv.setHint("CVV");
                cvv.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                cvv.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
                layout.addView(cvv);

                builder.setView(layout);

                // Configurar el botón "Confirmar"
                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Aquí puedes agregar el código para simular la compra
                        // y registrar la venta en la base de datos

                        String cardNumberStr = cardNumber.getText().toString();
                        String cardHolderStr = cardHolder.getText().toString();
                        String expiryDateStr = expiryDate.getText().toString();
                        String cvvStr = cvv.getText().toString();

                        Comprar();
                        FinalizarCompra();
                        mostrarAlertaAgradecimiento();
                    }
                });

                // Configurar el botón "Cancelar"
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                // Deshabilitar el cierre al tocar fuera del AlertDialog
                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });




        //FIN
    }

    private void obtenerDireccionUsuario(String usuario) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Crear la solicitud SOAP
                    SoapObject request = new SoapObject(NAMESPACE, METHOD);
                    request.addProperty("usuario", usuario);

                    // Crear el sobre SOAP
                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope.setOutputSoapObject(request);
                    envelope.implicitTypes = true;

                    // Enviar la solicitud
                    HttpTransportSE transport = new HttpTransportSE(URL);
                    transport.call(SOAPACTION, envelope);

                    // Obtener la respuesta
                    Object response = envelope.getResponse();

                    if (response instanceof String) {
                        String jsonResponse = (String) response;

                        // Parsear la respuesta JSON
                        JSONObject jsonObject = new JSONObject(jsonResponse);
                        String direccion = jsonObject.getString("direccion");

                        // Crear el texto con el prefijo
                        final String textoConPrefijo = "Dirección de envío: " + direccion;

                        // Actualizar la UI en el hilo principal
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Direcc.setText(textoConPrefijo);
                            }
                        });
                    } else {
                        Log.e("Ticket", "La respuesta no es del tipo esperado.");
                    }

                } catch (Exception e) {
                    Log.e("Ticket", "Error al obtener la dirección del usuario: " + e.getMessage());
                }
            }
        }).start();
    }



    private List<Tickets> obtenerProductos1() {
        List<Tickets> lista = new ArrayList<>();
        double subtotalTotal = 0.0;
        TablaDetalle oper = new TablaDetalle(this, "operacion", null, 1);
        SQLiteDatabase db = oper.getWritableDatabase();
        String[] columnas = {"nombre", "cantidad", "total"};
        Cursor cursor = db.query("detalle_temp", columnas, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                String cantidad = cursor.getString(cursor.getColumnIndex("cantidad"));
                String precioStr = cursor.getString(cursor.getColumnIndex("total"));

                double precio = 0.0;
                try {
                    precio = Double.parseDouble(precioStr.replace("$", "").trim());
                } catch (NumberFormatException e) {
                    Log.e("Ticket", "Error al convertir el precio: " + e.getMessage());
                }

                Tickets producto = new Tickets(nombre, cantidad, "Subtotal $ " + precio, null);
                lista.add(producto);

                subtotalTotal += precio;
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        Total.setText(String.format("$ %.2f", subtotalTotal));

        return lista;
    }

    private List<Tickets> obtenerProductos2() {
        List<Tickets> lista = new ArrayList<>();
        double subtotalTotal = 0.0;
        TablaDetalle oper = new TablaDetalle(this, "operacion", null, 1);
        SQLiteDatabase db = oper.getWritableDatabase();
        String[] columnas = {"nombre", "cantidad", "total"};
        Cursor cursor = db.query("detalle_tempo", columnas, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                String cantidad = cursor.getString(cursor.getColumnIndex("cantidad"));
                String precioStr = cursor.getString(cursor.getColumnIndex("total"));

                double precio = 0.0;
                try {
                    precio = Double.parseDouble(precioStr.replace("$", "").trim());
                } catch (NumberFormatException e) {
                    Log.e("Ticket", "Error al convertir el precio: " + e.getMessage());
                }

                Tickets producto = new Tickets(nombre, cantidad, "Subtotal $ " + precio, null);
                lista.add(producto);

                subtotalTotal += precio;
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        Total.setText(String.format("$ %.2f", subtotalTotal));

        return lista;
    }

    private void Comprar() {
        // Definición de constantes para el servicio SOAP
        String NAMESPACE = "https://espaciotecnologicodigital.com/SOAPPasteleriaApp/";
        String URL = "https://espaciotecnologicodigital.com/SOAPPasteleriaApp/Pasteleria.php?wsdl";
        String METHOD_NAME1 = "registrarCompra";
        String SOAP_ACTION1  = "https://espaciotecnologicodigital.com/SOAPPasteleriaApp/Pasteleria.php?method=registrarCompra";
        String METHOD_NAME2 = "buscaridC";
        String SOAP_ACTION2  = "https://espaciotecnologicodigital.com/SOAPPasteleriaApp/Pasteleria.php?method=buscaridC";
        String METHOD_NAME3 = "registrarDetalle";
        String SOAP_ACTION3  = "https://espaciotecnologicodigital.com/SOAPPasteleriaApp/Pasteleria.php?method=registrarDetalle";

        // Obtener el tipo de venta desde SharedPreferences
        SharedPreferences sharedPreferences1 = getSharedPreferences("MiPreferencias", MODE_PRIVATE);
        int tipodeVenta = sharedPreferences1.getInt("dato", -1);

        // Recuperar el total de la venta desde el TextView
        TextView totalTextView = findViewById(R.id.Total); // Asegúrate de que 'Total' es el ID correcto
        String totalVentas = totalTextView.getText().toString();

// Limpiar el formato del total
        String totalVenta = totalVentas.replace("$", "").trim().replace(",", "");

        // Variable para almacenar el id_cliente
        final String[] idCliente = {""}; // Usar un array para mantener la referencia

        TablaDetalle oper = new TablaDetalle(this, "operacion", null, 1);
        SQLiteDatabase db = oper.getWritableDatabase();

        if (tipodeVenta == 1) {
            // Tipo de Venta por Carrito (Usamos la tabla detalle_temp)
            Cursor cursor = db.rawQuery("SELECT id_cliente FROM detalle_temp LIMIT 1", null);
            if (cursor.moveToFirst()) {
                idCliente[0] = cursor.getString(0); // Obtener el id_cliente de la primera fila
            }
            cursor.close();

            // Crear y enviar la solicitud SOAP para el tipo de venta 1
            new Thread(() -> {
                try {
                    // Configurar la solicitud SOAP
                    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME1);
                    request.addProperty("cliente", idCliente[0]);
                    request.addProperty("total", totalVenta);

                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope.setOutputSoapObject(request);
                    envelope.implicitTypes = true;

                    HttpTransportSE transport = new HttpTransportSE(URL);
                    transport.call(SOAP_ACTION1, envelope);

                    // Procesar la respuesta
                    Object response = envelope.getResponse();
                    String result = response.toString();

                    // Mostrar la respuesta en un Toast
                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Respuesta del servidor: " + result, Toast.LENGTH_LONG).show());

                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Error al enviar la solicitud SOAP", Toast.LENGTH_LONG).show());
                }
            }).start();

        } else if (tipodeVenta == 0) {
            // Tipo de Venta por Producto Directo (Usamos la tabla detalle_tempo)
            Cursor cursor = db.rawQuery("SELECT id_cliente FROM detalle_tempo LIMIT 1", null);
            if (cursor.moveToFirst()) {
                idCliente[0] = cursor.getString(0); // Obtener el id_cliente de la primera fila
            }
            cursor.close();

            // Crear y enviar la solicitud SOAP para el tipo de venta 0
            new Thread(() -> {
                try {
                    // Configurar la solicitud SOAP
                    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME1);
                    request.addProperty("cliente", idCliente[0]);
                    request.addProperty("total", totalVenta);

                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope.setOutputSoapObject(request);
                    envelope.implicitTypes = true;

                    HttpTransportSE transport = new HttpTransportSE(URL);
                    transport.call(SOAP_ACTION1, envelope);

                    // Procesar la respuesta
                    Object response = envelope.getResponse();
                    String result = response.toString();

                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Error al enviar la solicitud SOAP", Toast.LENGTH_LONG).show());
                }
            }).start();
        } else {
            Toast.makeText(getApplicationContext(), "Venta no Recuperado", Toast.LENGTH_SHORT).show();
        }

        db.close(); // Cerrar la base de datos
    }


    private void FinalizarCompra() {
        // Definición de constantes para el servicio SOAP
        String NAMESPACE = "https://espaciotecnologicodigital.com/SOAPPasteleriaApp/";
        String URL = "https://espaciotecnologicodigital.com/SOAPPasteleriaApp/Pasteleria.php?wsdl";
        String METHOD_NAME2 = "buscaridC";
        String SOAP_ACTION2 = "https://espaciotecnologicodigital.com/SOAPPasteleriaApp/Pasteleria.php?method=buscaridC";

        new Thread(() -> {
            try {
                // Configurar la solicitud SOAP
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME2);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);
                envelope.implicitTypes = true;

                HttpTransportSE transport = new HttpTransportSE(URL);
                transport.call(SOAP_ACTION2, envelope);

                // Procesar la respuesta
                Object response = envelope.getResponse();
                String jsonResponse = response.toString();

                // Log la respuesta JSON
                Log.d("SOAP", "Respuesta JSON: " + jsonResponse);

                // Parsear la respuesta JSON para extraer el ID
                JSONObject jsonObject = new JSONObject(jsonResponse);
                String idMayor = jsonObject.getString("id");

                // Log el ID extraído
                Log.d("SOAP", "ID Mayor: " + idMayor);

                String METHOD_NAME3 = "registrarDetalle";
                String SOAP_ACTION3 = "https://espaciotecnologicodigital.com/SOAPPasteleriaApp/Pasteleria.php?method=registrarDetalle";

                // Obtener el tipo de venta desde SharedPreferences
                SharedPreferences sharedPreferences1 = getSharedPreferences("MiPreferencias", MODE_PRIVATE);
                int tipodeVenta = sharedPreferences1.getInt("dato", -1);

                // Instancia a la Base de datos Local
                TablaDetalle oper = new TablaDetalle(this, "operacion", null, 1);
                SQLiteDatabase db = oper.getWritableDatabase();

                // Determinar la tabla a usar según el tipo de venta
                String tableName = (tipodeVenta == 1) ? "detalle_temp" : (tipodeVenta == 0) ? "detalle_tempo" : null;

                if (tableName != null) {
                    // Obtener los detalles de la compra desde la tabla correspondiente
                    Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);

                    // Recorrer los resultados del cursor y enviar los detalles al servidor
                    while (cursor.moveToNext()) {
                        // Extraer los datos de la fila actual
                        String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                        String id_producto = cursor.getString(cursor.getColumnIndex("id_producto"));
                        String cantidad = cursor.getString(cursor.getColumnIndex("cantidad"));
                        String precio = cursor.getString(cursor.getColumnIndex("precio"));
                        String id_usuario = cursor.getString(cursor.getColumnIndex("id_usuario"));

                        // Configurar la solicitud SOAP para registrar detalle
                        SoapObject requestDetail = new SoapObject(NAMESPACE, METHOD_NAME3);
                        requestDetail.addProperty("id_venta", idMayor);
                        requestDetail.addProperty("nombre", nombre);
                        requestDetail.addProperty("id_producto", id_producto);
                        requestDetail.addProperty("cantidad", cantidad);
                        requestDetail.addProperty("precio", precio);
                        requestDetail.addProperty("id_usuario", id_usuario);

                        SoapSerializationEnvelope envelopeDetail = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        envelopeDetail.setOutputSoapObject(requestDetail);
                        envelopeDetail.implicitTypes = true;

                        HttpTransportSE transportDetail = new HttpTransportSE(URL);
                        transportDetail.call(SOAP_ACTION3, envelopeDetail);

                        // Procesar la respuesta del registro de detalle
                        Object responseDetail = envelopeDetail.getResponse();
                        String result = responseDetail.toString();

                        // Log la respuesta del registro de detalle
                        Log.d("SOAP", "Respuesta detalle: " + result);


                    }

                    cursor.close();
                } else {
                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Tipo de venta no válido", Toast.LENGTH_SHORT).show());
                }

                db.close();

            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Error al enviar la solicitud SOAP", Toast.LENGTH_LONG).show());
            }
        }).start();

    }



    // Método para mostrar la alerta de agradecimiento
    private void mostrarAlertaAgradecimiento() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ticket.this);
        builder.setTitle("Gracias por su compra");
        builder.setMessage("Su compra se ha realizado con éxito.");

        // Configurar el botón "Continuar"
        builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                VaciarCarrito();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }



    private void VaciarCarrito(){
        TablaDetalle oper = new TablaDetalle(this, "operacion", null, 1);
        SQLiteDatabase DetallesBD = oper.getWritableDatabase();
        int tipo1 = DetallesBD.delete("detalle_tempo", "tipo = 0", null);
        int tipo2 = DetallesBD.delete("detalle_temp", "tipo = 1", null);
        DetallesBD.close();

        Intent intent = new Intent(ticket.this, principal.class);
        startActivity(intent);
        finish();
    }

}
