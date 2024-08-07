package com.tienda.Clases;

import java.util.List;

/**
 * La clase {@code Transacción} representa una transacción en la tienda.
 * Una transacción incluye una lista de productos comprados, el total de la compra,
 * y el cajero que realizó la transacción.
 */
public class Transacción {
    private int id;
    private List<Producto> productos;
    private double total;
    private Cajero cajero;

    /**
     * Crea una nueva instancia de {@code Transacción}.
     *
     * @param id El identificador de la transacción.
     * @param productos La lista de productos incluidos en la transacción.
     * @param total El total de la transacción.
     * @param cajero El cajero que realizó la transacción.
     */
    public Transacción(int id, List<Producto> productos, double total, Cajero cajero) {
        this.id = id;
        this.productos = productos;
        this.total = total;
        this.cajero = cajero;
    }

    /**
     * Obtiene el identificador de la transacción.
     *
     * @return El identificador de la transacción.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador de la transacción.
     *
     * @param id El identificador de la transacción.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene la lista de productos en la transacción.
     *
     * @return La lista de productos.
     */
    public List<Producto> getProductos() {
        return productos;
    }

    /**
     * Establece la lista de productos en la transacción.
     *
     * @param productos La lista de productos.
     */
    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    /**
     * Obtiene el total de la transacción.
     *
     * @return El total de la transacción.
     */
    public double getTotal() {
        return total;
    }

    /**
     * Establece el total de la transacción.
     *
     * @param total El total de la transacción.
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * Obtiene el cajero que realizó la transacción.
     *
     * @return El cajero.
     */
    public Cajero getCajero() {
        return cajero;
    }

    /**
     * Establece el cajero que realizó la transacción.
     *
     * @param cajero El cajero.
     */
    public void setCajero(Cajero cajero) {
        this.cajero = cajero;
    }
}
