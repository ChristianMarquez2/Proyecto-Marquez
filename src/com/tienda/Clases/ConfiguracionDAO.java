package com.tienda.Clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConfiguracionDAO {
    private Connection connection;

    public ConfiguracionDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean guardarConfiguracion(String username, String password, String theme, String language, boolean notifications) {
        // Actualiza o inserta configuración en la tabla
        String query = "UPDATE configuracion SET username = ?, password = ?, theme = ?, language = ?, notifications = ? WHERE id = 1";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, theme);
            stmt.setString(4, language);
            stmt.setBoolean(5, notifications);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
