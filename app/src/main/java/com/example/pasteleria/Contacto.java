package com.example.pasteleria;

public class Contacto {
    public int id;
    public int Imagenlist;
    public String Productolist;
    public String Categorialist;
    public String Preciolist;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImagenlist() {
        return Imagenlist;
    }

    public void setImagenlist(int Imagenlist) {
        this.Imagenlist = Imagenlist;
    }

    public String getProductolist() {
        return Productolist;
    }

    public void setProductolist(String Productolist) {
        this.Productolist = Productolist;
    }

    public String getCategorialist() {
        return Categorialist;
    }

    public void setCategorialist(String Categorialist) {
        this.Categorialist = Categorialist;
    }

    public String getPreciolist() {
        return Preciolist;
    }

    public void setPreciolist(String Preciolist) {
        this.Preciolist = Preciolist;
    }

    public Contacto(int id, int Imagenlist, String Productolist, String Categorialist, String Preciolist) {
        this.id = id;
        this.Imagenlist = Imagenlist;
        this.Productolist = Productolist;
        this.Categorialist = Categorialist;
        this.Preciolist = Preciolist;
    }
}
