package com.tienda;

import com.tienda.model.TiendaGUI;
import com.tienda.model.BaseDeDatos;

public class Main {
    public static void main(String[] args) {
        // Inicializar la conexión a la base de datos
        BaseDeDatos.inicializarConexion();

        // Ejecutar la interfaz gráfica
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                TiendaGUI gui = new TiendaGUI();
                gui.setVisible(true);
            }
        });
    }
}
