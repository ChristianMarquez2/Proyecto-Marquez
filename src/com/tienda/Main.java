package com.tienda;

import com.tienda.GUI.LoginWindow;
import com.tienda.GUI.MainMenuWindow;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            LoginWindow loginWindow = new LoginWindow();
            loginWindow.setVisible(true);
        });
    }
}
