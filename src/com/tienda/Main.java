package com.tienda;

import com.tienda.GUI.LoginWindow;

public class Main {
    public static void main(String[] args) {
        // Ejecutar la interfaz gráfica
        javax.swing.SwingUtilities.invokeLater(() -> {
            LoginWindow loginWindow = new LoginWindow();
            loginWindow.setVisible(true);
        });
    }
}
