package com.tienda.GUI;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Clase principal de la aplicación que configura la apariencia del sistema y lanza la ventana de inicio de sesión.
 *
 * <p>Establece el Look and Feel del sistema y muestra la ventana de inicio de sesión.</p>
 *
 * @since 1.0
 */
public class TiendaGUI {

    /**
     * Método principal que se ejecuta al iniciar la aplicación.
     * <p>Configura el Look and Feel del sistema y luego muestra la ventana de inicio de sesión.</p>
     *
     * @param args Argumentos de línea de comandos que se pasan al programa (no se utilizan en esta implementación).
     */
    public static void main(String[] args) {
        try {
            // Establece el Look and Feel del sistema
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Inicia la ventana de inicio de sesión en el hilo de eventos de Swing
        java.awt.EventQueue.invokeLater(() -> {
            new LoginWindow().setVisible(true);
        });
    }
}
