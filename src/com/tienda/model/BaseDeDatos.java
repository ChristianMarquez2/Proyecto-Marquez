package com.tienda.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDeDatos {
    private static final String URL = "jdbc:mysql://localhost:3306/tienda"; // Ajusta la URL de tu base de datos
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "zznk"; // Cambia a la contraseña de tu base de datos

    private static Connection conexion;

    public static void inicializarConexion() {
        try {
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            System.out.println("Conexión a la base de datos establecida.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return conexion;
    }
}
