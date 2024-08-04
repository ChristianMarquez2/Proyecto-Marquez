package com.tienda;

import com.tienda.Clases.BaseDeDatos;
import com.tienda.GUI.LoginWindow;

public class Main {
    public static void main(String[] args) {
        // Inicializar la conexión a la base de datos
        BaseDeDatos.inicializarConexion();

        // Ejecutar la interfaz gráfica
        javax.swing.SwingUtilities.invokeLater(() -> {
            LoginWindow loginWindow = new LoginWindow();
            loginWindow.setVisible(true);
        });
    }
}
