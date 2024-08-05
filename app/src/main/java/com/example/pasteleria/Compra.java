package com.example.pasteleria;

public class Compra {
    private String nombre;
    private String precio;
    private String fecha;
    private String imagen;

    public Compra(String nombre, String precio, String fecha, String imagen) {
        this.nombre = nombre;
        this.precio = precio;
        this.fecha = fecha;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
