package com.tienda.Clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConfiguracionDAO {
    private Connection connection;

    public ConfiguracionDAO(Connection connection) {
        this.connection = connection;
    }
    public boolean guardarConfiguracion(String username, String password) {
        String sql = "UPDATE configuracion SET username = ?, password = ? WHERE id = 1";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
