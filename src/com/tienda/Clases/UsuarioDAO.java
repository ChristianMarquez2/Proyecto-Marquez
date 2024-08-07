package com.tienda.Clases;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase {@code UsuarioDAO} proporciona métodos para interactuar con la base de datos de usuarios,
 * incluyendo operaciones como verificar credenciales, agregar, obtener, actualizar y eliminar usuarios.
 */
public class UsuarioDAO {

    /**
     * Verifica si las credenciales proporcionadas coinciden con las almacenadas en la base de datos.
     *
     * @param nombre El nombre de usuario.
     * @param contraseña La contraseña del usuario.
     * @return {@code true} si las credenciales coinciden, {@code false} en caso contrario.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public boolean verificarCredenciales(String nombre, String contraseña) throws SQLException {
        String query = "SELECT contraseña FROM usuarios WHERE nombre = ?";
        try (Connection conn = BaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombre);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String contraseñaAlmacenada = rs.getString("contraseña");
                    return contraseña.equals(contraseñaAlmacenada);
                }
            }
        }
        return false;
    }

    /**
     * Agrega un nuevo usuario a la base de datos.
     *
     * @param usuario El usuario a agregar.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public void agregarUsuario(Usuario usuario) throws SQLException {
        String query = "INSERT INTO usuarios (nombre, rol, contraseña) VALUES (?, ?, ?)";
        try (Connection conn = BaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getRol());
            stmt.setString(3, usuario.getContraseña());
            stmt.executeUpdate();
        }
    }

    /**
     * Obtiene una lista de todos los usuarios en la base de datos.
     *
     * @return Una lista de objetos {@code Usuario}, que pueden ser instancias de {@code Administrador} o {@code Cajero}.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public List<Usuario> obtenerUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT * FROM usuarios";
        try (Connection conn = BaseDeDatos.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String rol = rs.getString("rol");
                if (rol.equals("Administrador")) {
                    usuarios.add(new Administrador(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("contraseña")
                    ));
                } else if (rol.equals("Cajero")) {
                    usuarios.add(new Cajero(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("contraseña")
                    ));
                }
            }
        }
        return usuarios;
    }

    /**
     * Autentica a un usuario con las credenciales proporcionadas.
     *
     * @param nombre El nombre de usuario.
     * @param contraseña La contraseña del usuario.
     * @return Un objeto {@code Usuario} que puede ser una instancia de {@code Administrador} o {@code Cajero},
     *         o {@code null} si las credenciales no son válidas.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public Usuario autenticarUsuario(String nombre, String contraseña) throws SQLException {
        String query = "SELECT * FROM usuarios WHERE nombre = ?";
        try (Connection conn = BaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombre);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String contraseñaAlmacenada = rs.getString("contraseña");
                    if (contraseña.equals(contraseñaAlmacenada)) {
                        String rol = rs.getString("rol");
                        if (rol.equals("Administrador")) {
                            return new Administrador(
                                    rs.getInt("id"),
                                    rs.getString("nombre"),
                                    rs.getString("contraseña")
                            );
                        } else if (rol.equals("Cajero")) {
                            return new Cajero(
                                    rs.getInt("id"),
                                    rs.getString("nombre"),
                                    rs.getString("contraseña")
                            );
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Actualiza la información de un usuario existente en la base de datos.
     *
     * @param usuario El usuario con la información actualizada.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public void actualizarUsuario(Usuario usuario) throws SQLException {
        String query = "UPDATE usuarios SET nombre = ?, rol = ?, contraseña = ? WHERE id = ?";
        try (Connection conn = BaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getRol());
            stmt.setString(3, usuario.getContraseña());
            stmt.setInt(4, usuario.getId());
            stmt.executeUpdate();
        }
    }

    /**
     * Autentica al usuario y obtiene su rol.
     *
     * @param nombre El nombre de usuario.
     * @param contraseña La contraseña del usuario.
     * @return El rol del usuario si las credenciales son válidas, {@code null} en caso contrario.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public String autenticarUsuarioYObtenerRol(String nombre, String contraseña) throws SQLException {
        String query = "SELECT rol FROM usuarios WHERE nombre = ? AND contraseña = ?";
        try (Connection conn = BaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombre);
            stmt.setString(2, contraseña);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("rol");
                }
            }
        }
        return null;
    }

    /**
     * Obtiene un {@code Administrador} a partir del nombre de usuario.
     *
     * @param nombre El nombre de usuario.
     * @return Un objeto {@code Administrador} si el usuario es un administrador, {@code null} en caso contrario.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public Administrador obtenerAdministradorPorNombre(String nombre) throws SQLException {
        String query = "SELECT * FROM usuarios WHERE nombre = ? AND rol = 'Administrador'";
        try (Connection conn = BaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombre);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Administrador(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("contraseña")
                    );
                }
            }
        }
        return null;
    }

    /**
     * Elimina un usuario de la base de datos.
     *
     * @param id El identificador del usuario a eliminar.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public void eliminarUsuario(int id) throws SQLException {
        String query = "DELETE FROM usuarios WHERE id = ?";
        try (Connection conn = BaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
