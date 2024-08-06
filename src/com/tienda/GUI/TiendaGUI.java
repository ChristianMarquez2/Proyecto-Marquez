package com.tienda.GUI;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class TiendaGUI {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        java.awt.EventQueue.invokeLater(() -> {
            new LoginWindow().setVisible(true);
        });
    }
}
