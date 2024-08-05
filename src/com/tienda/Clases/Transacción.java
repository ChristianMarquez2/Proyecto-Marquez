package com.tienda.Clases;

import java.util.List;

public class Transacción {
    private int id;
    private List<Producto> productos;
    private double total;
    private Cajero cajero;

    // Constructor
    public Transacción(int id, List<Producto> productos, double total, Cajero cajero) {
        this.id = id;
        this.productos = productos;
        this.total = total;
        this.cajero = cajero;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Cajero getCajero() {
        return cajero;
    }

    public void setCajero(Cajero cajero) {
        this.cajero = cajero;
    }
}
