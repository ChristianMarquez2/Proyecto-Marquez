package com.tienda.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public void agregarProducto(Producto producto) throws SQLException {
        String query = "INSERT INTO productos (nombre, precio, stock, imagen) VALUES (?, ?, ?, ?)";
        try (Connection conn = BaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, producto.getNombre());
            stmt.setDouble(2, producto.getPrecio());
            stmt.setInt(3, producto.getStock());
            stmt.setString(4, producto.getImagenPath());
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
                        rs.getString("imagen")
                ));
            }
        }
        return productos;
    }

    // Otros métodos como actualizarStock(), eliminarProducto(), etc.
}
