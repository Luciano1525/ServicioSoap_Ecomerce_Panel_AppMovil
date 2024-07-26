package com.example.pasteleria;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;

public class ticket extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        //Activar Toolbar
        setSupportActionBar(findViewById(R.id.toolbar));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Hacer visible el menu
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }
}