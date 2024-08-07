package com.tienda.Clases;

import java.sql.SQLException;
import java.util.List;

/**
 * La clase {@code Cajero} representa a un cajero en la tienda.
 * Hereda de la clase {@link Usuario} y tiene la funcionalidad específica para gestionar transacciones.
 */
public class Cajero extends Usuario {

    /**
     * Constructor de la clase {@code Cajero}.
     *
     * @param id          El ID del cajero.
     * @param nombre      El nombre del cajero.
     * @param contraseña  La contraseña del cajero.
     */
    public Cajero(int id, String nombre, String contraseña) {
        super(id, nombre, contraseña, "Cajero");
    }

    /**
     * Registra una transacción para una lista de productos.
     *
     * @param productos La lista de productos a registrar en la transacción.
     */
    public void registrarTransacción(List<Producto> productos) {
        TransacciónDAO transacciónDAO = new TransacciónDAO();
        ProductoDAO productoDAO = new ProductoDAO();
        double total = calcularTotal(productos);
        Transacción transacción = new Transacción(0, productos, total, this);

        try {
            transacciónDAO.agregarTransacción(transacción);
            for (Producto producto : productos) {
                producto.setStock(producto.getStock() - 1);
                productoDAO.actualizarProducto(producto);
            }
            System.out.println("Transacción registrada con éxito.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al registrar la transacción.");
        }
    }

    /**
     * Calcula el total de una lista de productos sumando sus precios.
     *
     * @param productos La lista de productos.
     * @return El total de los precios de los productos.
     */
    private double calcularTotal(List<Producto> productos) {
        double total = 0.0;
        for (Producto producto : productos) {
            total += producto.getPrecio();
        }
        return total;
    }

    /**
     * Muestra los detalles de una transacción específica en la consola.
     *
     * @param transacciónId El ID de la transacción a ver.
     */
    public void verDetalleTransacción(int transacciónId) {
        TransacciónDAO transacciónDAO = new TransacciónDAO();
        try {
            Transacción transacción = transacciónDAO.obtenerTransacciónPorId(transacciónId);
            System.out.println("Transacción ID: " + transacción.getId());
            System.out.println("Cajero: " + transacción.getCajero().getNombre());
            System.out.println("Productos:");
            for (Producto producto : transacción.getProductos()) {
                System.out.println(" - " + producto.getNombre() + ": " + producto.getPrecio());
            }
            System.out.println("Total: " + transacción.getTotal());
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al obtener los detalles de la transacción.");
        }
    }

    /**
     * Busca productos por nombre en la base de datos.
     *
     * @param nombre El nombre del producto a buscar.
     * @return Una lista de productos que coinciden con el nombre buscado.
     */
    public List<Producto> buscarProductosPorNombre(String nombre) {
        ProductoDAO productoDAO = new ProductoDAO();
        try {
            return productoDAO.obtenerProductosPorNombre(nombre);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al buscar productos por nombre.");
            return null;
        }
    }

    /**
     * Muestra todos los productos disponibles en la base de datos en la consola.
     */
    public void verProductos() {
        ProductoDAO productoDAO = new ProductoDAO();
        try {
            List<Producto> productos = productoDAO.obtenerProductos();
            for (Producto producto : productos) {
                System.out.println(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al obtener los productos.");
        }
    }
}
