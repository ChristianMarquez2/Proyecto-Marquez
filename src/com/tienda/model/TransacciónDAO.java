package com.tienda.model;

import java.sql.*;

public class TransacciónDAO {

    public void agregarTransacción(Transacción transacción) throws SQLException {
        String query = "INSERT INTO transacciones (total, cajero_id) VALUES (?, ?)";
        try (Connection conn = BaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDouble(1, transacción.getTotal());
            stmt.setInt(2, transacción.getCajero().getId());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int transacciónId = generatedKeys.getInt(1);
                    for (Producto producto : transacción.getProductos()) {
                        agregarProductoTransacción(transacciónId, producto.getId());
                    }
                }
            }
        }
    }

    private void agregarProductoTransacción(int transacciónId, int productoId) throws SQLException {
        String query = "INSERT INTO productos_transacciones (transacción_id, producto_id) VALUES (?, ?)";
        try (Connection conn = BaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, transacciónId);
            stmt.setInt(2, productoId);
            stmt.executeUpdate();
        }
    }

    // Otros métodos como obtenerTransacciones(), etc.
}
