package com.tienda.Clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * La clase {@code ConfiguracionDAO} se encarga de manejar las operaciones relacionadas con la configuración en la base de datos.
 */
public class ConfiguracionDAO {
    private Connection connection;

    /**
     * Crea una instancia de {@code ConfiguracionDAO} con una conexión a la base de datos especificada.
     *
     * @param connection La conexión a la base de datos.
     */
    public ConfiguracionDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Guarda la configuración en la base de datos.
     *
     * @param username      El nombre de usuario.
     * @param password      La contraseña.
     * @param theme         El tema de la aplicación.
     * @param language      El idioma de la aplicación.
     * @param notifications El estado de las notificaciones (activadas o desactivadas).
     * @return {@code true} si la configuración se guardó correctamente; {@code false} en caso contrario.
     */
    public boolean guardarConfiguracion(String username, String password, String theme, String language, boolean notifications) {
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
