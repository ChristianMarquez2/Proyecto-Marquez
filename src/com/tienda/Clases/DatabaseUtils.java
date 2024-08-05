package com.tienda.Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtils {

    private static final String URL = "jdbc:mysql://localhost:3306/tienda"; // Ajusta la URL
    private static final String USER = "root"; // Ajusta el usuario
    private static final String PASSWORD = "zznk"; // Ajusta la contraseña

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al conectar con la base de datos.");
        }
    }
}
