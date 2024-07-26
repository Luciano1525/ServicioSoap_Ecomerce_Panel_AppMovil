package com.example.pasteleria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.List;

public class principal extends AppCompatActivity {

    private ViewPager viewPagerMasVendido, viewPagerRecomendado;
    private SliderAdapter sliderAdapterMasVendido, sliderAdapterRecomendado;
    private List<Integer> imageListMasVendido, imageListRecomendado;
    private Handler handler = new Handler();
    private Runnable runnableMasVendido, runnableRecomendado;
    private ImageButton imgCategoria1;
    private ImageButton imgCategoria2;
    private ImageButton imgCategoria3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        // Activar Toolbar
        setSupportActionBar(findViewById(R.id.toolbar));

        // Configuración de ViewPager para "Más Vendidos"
        viewPagerMasVendido = findViewById(R.id.viewPagerMasVendido);
        imageListMasVendido = new ArrayList<>();
        // Agrega tus imágenes aquí (reemplaza con tus propias imágenes)
        imageListMasVendido.add(R.drawable.capuccino);
        imageListMasVendido.add(R.drawable.cupcakechocolate);
        imageListMasVendido.add(R.drawable.multisabor);
        imageListMasVendido.add(R.drawable.oreo);
        imageListMasVendido.add(R.drawable.frutal);

        sliderAdapterMasVendido = new SliderAdapter(this, imageListMasVendido);
        viewPagerMasVendido.setAdapter(sliderAdapterMasVendido);

        runnableMasVendido = new Runnable() {
            @Override
            public void run() {
                int currentItem = viewPagerMasVendido.getCurrentItem();
                int totalItems = sliderAdapterMasVendido.getCount();
                currentItem = (currentItem + 1) % totalItems;
                viewPagerMasVendido.setCurrentItem(currentItem, true);
                handler.postDelayed(this, 3000); // Cambio cada 3 segundos
            }
        };

        // Configuración de ViewPager para "Recomendaciones"
        viewPagerRecomendado = findViewById(R.id.viewPagerRecomendado);
        imageListRecomendado = new ArrayList<>();
        // Agrega tus imágenes aquí (reemplaza con tus propias imágenes)
        imageListRecomendado.add(R.drawable.capuccino);
        imageListRecomendado.add(R.drawable.cupcakechocolate);
        imageListRecomendado.add(R.drawable.oreo);
        imageListRecomendado.add(R.drawable.multisabor);
        imageListRecomendado.add(R.drawable.frutal);

        sliderAdapterRecomendado = new SliderAdapter(this, imageListRecomendado);
        viewPagerRecomendado.setAdapter(sliderAdapterRecomendado);

        runnableRecomendado = new Runnable() {
            @Override
            public void run() {
                int currentItem = viewPagerRecomendado.getCurrentItem();
                int totalItems = sliderAdapterRecomendado.getCount();
                currentItem = (currentItem + 1) % totalItems;
                viewPagerRecomendado.setCurrentItem(currentItem, true);
                handler.postDelayed(this, 3000); // Cambio cada 3 segundos
            }
        };

        // Iniciar el cambio automático de imágenes
        handler.post(runnableMasVendido);
        handler.post(runnableRecomendado);


        // Enlazar los ImageButton
        imgCategoria1 = findViewById(R.id.imgCategoria1);
        imgCategoria2 = findViewById(R.id.imgCategoria2);
        imgCategoria3 = findViewById(R.id.imgCategoria3);

        // Configurar el OnClickListener para cada ImageButton
        imgCategoria1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Categoría 1", Toast.LENGTH_SHORT).show();
                Log.i("INFO:", "Categoría 1");
                Intent intent = new Intent(principal.this, productos_categoria.class);
                startActivity(intent);
            }
        });

        imgCategoria2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Categoría 2", Toast.LENGTH_SHORT).show();
                Log.i("INFO:", "Categoría 2");
                Intent intent = new Intent(principal.this, productos_categoria.class);
                startActivity(intent);
            }
        });

        imgCategoria3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Categoría 3", Toast.LENGTH_SHORT).show();
                Log.i("INFO:", "Categoría 3");
                Intent intent = new Intent(principal.this, productos_categoria.class);
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnableMasVendido);
        handler.removeCallbacks(runnableRecomendado);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Hacer visible el menu
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }


}
