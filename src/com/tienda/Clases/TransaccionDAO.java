package com.tienda.Clases;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

/**
 * La clase {@code TransacciónDAO} proporciona métodos para interactuar con la base de datos
 * en relación con las transacciones. Incluye la capacidad de agregar usuarios y transacciones,
 * verificar contraseñas, y recuperar información sobre transacciones y productos.
 */
public class TransaccionDAO {

    /**
     * Encripta la contraseña del usuario y la almacena en la base de datos.
     *
     * @param usuario El usuario cuyo contraseña se va a almacenar.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
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

    /**
     * Verifica si la contraseña proporcionada coincide con la almacenada en la base de datos.
     *
     * @param nombreUsuario El nombre del usuario.
     * @param contraseña La contraseña proporcionada por el usuario.
     * @return {@code true} si la contraseña es correcta, {@code false} en caso contrario.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
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

    /**
     * Agrega una nueva transacción a la base de datos.
     *
     * @param transacción La transacción a agregar.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public void agregarTransacción(Transaccion transacción) throws SQLException {
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

    /**
     * Agrega una relación entre una transacción y un producto.
     *
     * @param transacciónId El identificador de la transacción.
     * @param productoId El identificador del producto.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    private void agregarProductoTransacción(int transacciónId, int productoId) throws SQLException {
        String query = "INSERT INTO productos_transacciones (transacción_id, producto_id) VALUES (?, ?)";
        try (Connection conn = BaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, transacciónId);
            stmt.setInt(2, productoId);
            stmt.executeUpdate();
        }
    }

    /**
     * Obtiene todas las transacciones almacenadas en la base de datos.
     *
     * @return Una lista de transacciones.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public List<Transaccion> obtenerTransacciones() throws SQLException {
        List<Transaccion> transacciones = new ArrayList<>();
        String query = "SELECT * FROM transacciones";
        try (Connection conn = BaseDeDatos.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                double total = rs.getDouble("total");
                int cajeroId = rs.getInt("cajero_id");

                Cajero cajero = obtenerCajeroPorId(cajeroId);

                List<Producto> productos = obtenerProductosPorTransacción(id);
                Transaccion transacción = new Transaccion(id, productos, total, cajero);
                transacciones.add(transacción);
            }
        }
        return transacciones;
    }

    /**
     * Obtiene una transacción específica por su identificador.
     *
     * @param transacciónId El identificador de la transacción.
     * @return La transacción con el identificador especificado, o {@code null} si no existe.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public Transaccion obtenerTransacciónPorId(int transacciónId) throws SQLException {
        String query = "SELECT * FROM transacciones WHERE id = ?";
        try (Connection conn = BaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, transacciónId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    double total = rs.getDouble("total");
                    int cajeroId = rs.getInt("cajero_id");

                    Cajero cajero = obtenerCajeroPorId(cajeroId);

                    List<Producto> productos = obtenerProductosPorTransacción(transacciónId);

                    return new Transaccion(transacciónId, productos, total, cajero);
                }
            }
        }
        return null;
    }

    /**
     * Obtiene un cajero específico por su identificador.
     *
     * @param cajeroId El identificador del cajero.
     * @return El cajero con el identificador especificado, o {@code null} si no existe.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    private Cajero obtenerCajeroPorId(int cajeroId) throws SQLException {
        String query = "SELECT * FROM usuarios WHERE id = ? AND rol = 'Cajero'";
        try (Connection conn = BaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, cajeroId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    String contraseña = rs.getString("contraseña");

                    return new Cajero(id, nombre, contraseña);
                }
            }
        }
        return null;
    }

    /**
     * Obtiene todos los productos asociados a una transacción específica.
     *
     * @param transacciónId El identificador de la transacción.
     * @return Una lista de productos asociados a la transacción.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
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
