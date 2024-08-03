package com.example.pasteleria;

public class Pastel {
    private String nombre;
    private String imagen; // Esta debe ser la URL parcial

    public Pastel(String nombre, String imagen) {
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public String getImagen() {
        return imagen;
    }
}
