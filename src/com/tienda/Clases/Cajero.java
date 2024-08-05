package com.tienda.Clases;

import java.sql.SQLException;
import java.util.List;

public class Cajero extends Usuario {
    public Cajero(int id, String nombre, String contraseña) {
        super(id, nombre, contraseña, "Cajero");
    }

    // Método para registrar una nueva transacción
    public void registrarTransacción(List<Producto> productos) {
        TransacciónDAO transacciónDAO = new TransacciónDAO();
        ProductoDAO productoDAO = new ProductoDAO();
        double total = calcularTotal(productos);
        Transacción transacción = new Transacción(0, productos, total, this); // ID generado automáticamente

        try {
            transacciónDAO.agregarTransacción(transacción);
            // Reducir stock de los productos
            for (Producto producto : productos) {
                producto.setStock(producto.getStock() - 1); // Suponiendo que se reduce el stock en 1 por cada producto comprado
                productoDAO.actualizarProducto(producto);
            }
            System.out.println("Transacción registrada con éxito.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al registrar la transacción.");
        }
    }

    // Método para calcular el total de una lista de productos
    private double calcularTotal(List<Producto> productos) {
        double total = 0.0;
        for (Producto producto : productos) {
            total += producto.getPrecio();
        }
        return total;
    }

    // Método para ver detalles de una transacción específica
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

    // Método para buscar productos por nombre
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

    // Método para ver todos los productos
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
