package com.tienda.Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para gestionar la conexión a la base de datos MySQL.
 */
public class BaseDeDatos {

    /**
     * URL de la base de datos MySQL a la que se va a conectar.
     */
    private static final String URL = "jdbc:mysql://localhost:3306/tienda";

    /**
     * Nombre de usuario para acceder a la base de datos MySQL.
     */
    private static final String USUARIO = "root";

    /**
     * Contraseña para acceder a la base de datos MySQL.
     */
    private static final String CONTRASENA = "zznk";

    /**
     * Establece y devuelve una conexión a la base de datos MySQL.
     *
     * @return Un objeto {@link Connection} que representa la conexión a la base de datos.
     * @throws SQLException Si ocurre un error al establecer la conexión a la base de datos.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }
}
