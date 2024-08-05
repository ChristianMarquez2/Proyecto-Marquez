package com.tienda.Clases;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

public class TransacciónDAO {

    // Método para encriptar la contraseña antes de almacenarla (considerar mover a una clase de usuario)
    public void agregarUsuario(Usuario usuario) throws SQLException {
        String hashedPassword = BCrypt.hashpw(usuario.getContraseña(), BCrypt.gensalt());
        String query = "INSERT INTO usuarios (nombre, rol, contraseña) VALUES (?, ?, ?)";
        try (Connection conn = BaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getRol());
            stmt.setString(3, hashedPassword);
            stmt.executeUpdate();
        }
    }

    // Método para verificar la contraseña durante el login (considerar mover a una clase de usuario)
    public boolean verificarContraseña(String nombreUsuario, String contraseña) throws SQLException {
        String query = "SELECT contraseña FROM usuarios WHERE nombre = ?";
        try (Connection conn = BaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombreUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String hashedPassword = rs.getString("contraseña");
                    return BCrypt.checkpw(contraseña, hashedPassword);
                }
            }
        }
        return false;
    }

    // Método para agregar una nueva transacción a la base de datos
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

    // Método privado para agregar una relación producto-transacción
    private void agregarProductoTransacción(int transacciónId, int productoId) throws SQLException {
        String query = "INSERT INTO productos_transacciones (transacción_id, producto_id) VALUES (?, ?)";
        try (Connection conn = BaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, transacciónId);
            stmt.setInt(2, productoId);
            stmt.executeUpdate();
        }
    }

    // Método para obtener todas las transacciones de la base de datos
    public List<Transacción> obtenerTransacciones() throws SQLException {
        List<Transacción> transacciones = new ArrayList<>();
        String query = "SELECT * FROM transacciones";
        try (Connection conn = BaseDeDatos.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                double total = rs.getDouble("total");
                int cajeroId = rs.getInt("cajero_id");

                // Crear un objeto Cajero para asociarlo con la transacción
                Cajero cajero = obtenerCajeroPorId(cajeroId);

                // Crear y agregar la transacción a la lista
                List<Producto> productos = obtenerProductosPorTransacción(id); // Obtener productos primero
                Transacción transacción = new Transacción(id, productos, total, cajero); // Crear Transacción
                transacciones.add(transacción);
            }
        }
        return transacciones;
    }

    // Método para obtener una transacción por ID
    public Transacción obtenerTransacciónPorId(int transacciónId) throws SQLException {
        String query = "SELECT * FROM transacciones WHERE id = ?";
        try (Connection conn = BaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, transacciónId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    double total = rs.getDouble("total");
                    int cajeroId = rs.getInt("cajero_id");

                    // Crear un objeto Cajero para asociarlo con la transacción
                    Cajero cajero = obtenerCajeroPorId(cajeroId);

                    // Obtener productos asociados a la transacción
                    List<Producto> productos = obtenerProductosPorTransacción(transacciónId);

                    // Crear y devolver la transacción
                    return new Transacción(transacciónId, productos, total, cajero);
                }
            }
        }
        return null; // o lanzar una excepción si no se encuentra
    }

    // Método privado para obtener un Cajero por ID
    private Cajero obtenerCajeroPorId(int cajeroId) throws SQLException {
        String query = "SELECT * FROM usuarios WHERE id = ? AND rol = 'Cajero'";
        try (Connection conn = BaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, cajeroId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Obtener datos del resultado
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    String contraseña = rs.getString("contraseña"); // Obtener la contraseña

                    // Crear y devolver el objeto Cajero
                    return new Cajero(id, nombre, contraseña);
                }
            }
        }
        return null; // o lanzar una excepción si no se encuentra
    }

    // Método privado para obtener productos asociados a una transacción
    private List<Producto> obtenerProductosPorTransacción(int transacciónId) throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String query = "SELECT p.* FROM productos p " +
                "JOIN productos_transacciones pt ON p.id = pt.producto_id " +
                "WHERE pt.transacción_id = ?";
        try (Connection conn = BaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, transacciónId);
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
