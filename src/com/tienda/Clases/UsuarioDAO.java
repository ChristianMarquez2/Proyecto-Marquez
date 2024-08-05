package com.tienda.Clases;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

public class UsuarioDAO {

    public boolean verificarCredenciales(String nombre, String contraseña) throws SQLException {
        String query = "SELECT contraseña FROM usuarios WHERE nombre = ?";
        try (Connection conn = BaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombre);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String contraseñaAlmacenada = rs.getString("contraseña");
                    return BCrypt.checkpw(contraseña, contraseñaAlmacenada);
                }
            }
        }
        return false;
    }

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

    public void actualizarUsuario(Usuario usuario) throws SQLException {
        String query = "UPDATE usuarios SET nombre = ?, rol = ? WHERE id = ?";
        try (Connection conn = BaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getRol());
            stmt.setInt(3, usuario.getId());
            stmt.executeUpdate();
        }
    }

    public void eliminarUsuario(int usuarioId) throws SQLException {
        String query = "DELETE FROM usuarios WHERE id = ?";
        try (Connection conn = BaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, usuarioId);
            stmt.executeUpdate();
        }
    }

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

    public Usuario autenticarUsuario(String nombre, String contraseña) throws SQLException {
        String query = "SELECT * FROM usuarios WHERE nombre = ?";
        try (Connection conn = BaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombre);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String contraseñaAlmacenada = rs.getString("contraseña");
                    if (BCrypt.checkpw(contraseña, contraseñaAlmacenada)) {
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
}
