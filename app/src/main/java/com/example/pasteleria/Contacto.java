package com.example.pasteleria;

public class Contacto {
    private String nombre;
    private String estado;
    private String precio;
    private String imagen;

    // Constructor con parámetros
    public Contacto(String nombre, String estado, String precio, String imagen) {
        this.nombre = nombre;
        this.estado = estado;
        this.precio = precio;
        this.imagen = imagen;
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}

