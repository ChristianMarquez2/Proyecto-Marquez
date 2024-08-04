package com.tienda.Clases;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    // Método para agregar un nuevo producto a la base de datos
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

    // Método para obtener todos los productos de la base de datos
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

    // Método para obtener productos por marca
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

    // Método para obtener productos por modelo
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

    // Método para actualizar un producto existente
    public void actualizarProducto(Producto producto) throws SQLException {
        String query = "UPDATE productos SET nombre = ?, precio = ?, stock = ?, imagen = ?, marca_id = ?, modelo = ? WHERE id = ?";
        try (Connection conn = BaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, producto.getNombre());
            stmt.setDouble(2, producto.getPrecio());
            stmt.setInt(3, producto.getStock());
            stmt.setString(4, producto.getImagenPath()); // Cambia 'imagen' por el nombre correcto del campo en tu base de datos
            stmt.setInt(5, producto.getMarcaId());
            stmt.setString(6, producto.getModelo());
            stmt.setInt(7, producto.getId());
            stmt.executeUpdate();
        }
    }

    // Método para actualizar el stock de un producto
    public void actualizarStock(int productoId, int nuevoStock) throws SQLException {
        String query = "UPDATE productos SET stock = ? WHERE id = ?";
        try (Connection conn = BaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, nuevoStock);
            stmt.setInt(2, productoId);
            stmt.executeUpdate();
        }
    }

    // Método para eliminar un producto por ID
    public void eliminarProducto(int productoId) throws SQLException {
        String query = "DELETE FROM productos WHERE id = ?";
        try (Connection conn = BaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, productoId);
            stmt.executeUpdate();
        }
    }

    // Método para buscar productos por nombre
    public List<Producto> obtenerProductosPorNombre(String nombre) throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String query = "SELECT * FROM productos WHERE nombre LIKE ?";
        try (Connection conn = BaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + nombre + "%");
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
}
