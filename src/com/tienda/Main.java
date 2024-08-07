package com.tienda;

import com.tienda.GUI.LoginWindow;

/**
 * Clase principal de la aplicación que lanza la ventana de inicio de sesión.
 *
 * <p>Este es otro punto de entrada para la aplicación que muestra la ventana de inicio de sesión en el hilo de eventos de Swing.</p>
 *
 * @since 1.0
 */
public class Main {

    /**
     * Método principal que se ejecuta al iniciar la aplicación.
     * <p>Inicializa y muestra la ventana de inicio de sesión.</p>
     *
     * @param args Argumentos de línea de comandos que se pasan al programa (no se utilizan en esta implementación).
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            LoginWindow loginWindow = new LoginWindow();
            loginWindow.setVisible(true);
        });
    }
}
