package com.tienda.Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * La clase {@code DatabaseUtils} proporciona métodos utilitarios para la conexión con la base de datos.
 */
public class DatabaseUtils {

    private static final String URL = "jdbc:mysql://localhost:3306/tienda";
    private static final String USER = "root";
    private static final String PASSWORD = "zznk";

    /**
     * Obtiene una conexión a la base de datos.
     *
     * @return La conexión a la base de datos.
     * @throws SQLException Si ocurre un error al conectar con la base de datos.
     */
    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al conectar con la base de datos.", e);
        }
    }
}
