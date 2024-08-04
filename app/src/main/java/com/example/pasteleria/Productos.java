package com.example.pasteleria;

public class Productos {
    private String imagen; // Cambia de int a String
    private String nombre;
    private String precio;
    private int cantidad;

    public Productos(String imagen, String nombre, String precio, int cantidad) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String getImagen() { // Cambia el tipo de retorno a String
        return imagen;
    }

    public void setImagen(String imagen) { // Cambia el tipo del parámetro a String
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
