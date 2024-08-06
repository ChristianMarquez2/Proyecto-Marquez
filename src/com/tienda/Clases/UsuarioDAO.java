package com.tienda.Clases;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // Método para verificar credenciales de usuario
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

    // Método para agregar un usuario
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

    // Método para obtener todos los usuarios
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

    // Método para autenticar un usuario
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
        return null; // Usuario o contraseña incorrectos
    }

    // Método para actualizar un usuario
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
        return null; // Usuario o contraseña incorrectos
    }

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
        return null; // No se encontró el administrador
    }
    // Método para eliminar un usuario
    public void eliminarUsuario(int id) throws SQLException {
        String query = "DELETE FROM usuarios WHERE id = ?";
        try (Connection conn = BaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
