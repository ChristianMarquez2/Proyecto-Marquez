package com.tienda.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public void agregarProducto(Producto producto) throws SQLException {
        String query = "INSERT INTO productos (nombre, precio, stock, imagen, marca_id, modelo) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = BaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, producto.getNombre());
            stmt.setDouble(2, producto.getPrecio());
            stmt.setInt(3, producto.getStock());
            stmt.setString(4, producto.getImagenPath());
            stmt.setInt(5, producto.getMarcaId());
            stmt.setString(6, producto.getModelo());
            stmt.executeUpdate();
        }
    }

    public List<Producto> obtenerProductos() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String query = "SELECT * FROM productos";
        try (Connection conn = BaseDeDatos.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                productos.add(new Producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getInt("stock"),
                        rs.getString("imagen"),
                        rs.getInt("marca_id"),
                        rs.getString("modelo")
                ));
            }
        }
        return productos;
    }

    public List<Producto> obtenerProductosPorMarca(int marcaId) throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String query = "SELECT * FROM productos WHERE marca_id = ?";
        try (Connection conn = BaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, marcaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    productos.add(new Producto(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getDouble("precio"),
                            rs.getInt("stock"),
                            rs.getString("imagen"),
                            rs.getInt("marca_id"),
                            rs.getString("modelo")
                    ));
                }
            }
        }
        return productos;
    }

    public List<Producto> obtenerProductosPorModelo(String modelo) throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String query = "SELECT * FROM productos WHERE modelo = ?";
        try (Connection conn = BaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, modelo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    productos.add(new Producto(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getDouble("precio"),
                            rs.getInt("stock"),
                            rs.getString("imagen"),
                            rs.getInt("marca_id"),
                            rs.getString("modelo")
                    ));
                }
            }
        }
        return productos;
    }

    // Otros métodos como actualizarStock(), eliminarProducto(), etc.
}
