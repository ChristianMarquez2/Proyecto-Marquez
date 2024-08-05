package com.tienda.Clases;

public class Producto {
    private int id;
    private String nombre;
    private double precio;
    private int stock;
    private String imagenPath; // Ruta a la imagen del producto
    private int marcaId; // Id de la marca del producto
    private String modelo; // Modelo del producto

    // Constructor
    public Producto(int id, String nombre, double precio, int stock, String imagenPath, int marcaId, String modelo) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.imagenPath = imagenPath;
        this.marcaId = marcaId;
        this.modelo = modelo;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        if (precio >= 0) {
            this.precio = precio;
        } else {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        if (stock >= 0) {
            this.stock = stock;
        } else {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }
    }

    public String getImagenPath() {
        return imagenPath;
    }

    public void setImagenPath(String imagenPath) {
        this.imagenPath = imagenPath;
    }

    public int getMarcaId() {
        return marcaId;
    }

    public void setMarcaId(int marcaId) {
        this.marcaId = marcaId;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                ", imagenPath='" + imagenPath + '\'' +
                ", marcaId=" + marcaId +
                ", modelo='" + modelo + '\'' +
                '}';
    }
}
