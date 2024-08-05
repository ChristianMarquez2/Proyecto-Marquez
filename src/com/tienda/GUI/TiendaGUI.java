package com.tienda.GUI;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class TiendaGUI {
    public static void main(String[] args) {
        // Configurar el look and feel del sistema
        try {
            // Establecer el look and feel del sistema para que la aplicación se vea más integrada con el sistema operativo
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Iniciar la ventana de acceso al sistema
        java.awt.EventQueue.invokeLater(() -> {
            new LoginWindow().setVisible(true);
        });
    }
}
