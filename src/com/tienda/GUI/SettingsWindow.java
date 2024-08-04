package com.tienda.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsWindow extends JFrame {
    public SettingsWindow() {
        setTitle("Configuración");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel para configuración
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridLayout(3, 2)); // 3 filas y 2 columnas

        // Ejemplo de configuraciones
        JLabel usernameLabel = new JLabel("Nombre de Usuario:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Contraseña:");
        JPasswordField passwordField = new JPasswordField();
        JButton saveButton = new JButton("Guardar");

        settingsPanel.add(usernameLabel);
        settingsPanel.add(usernameField);
        settingsPanel.add(passwordLabel);
        settingsPanel.add(passwordField);
        settingsPanel.add(saveButton);

        // Acción para guardar cambios
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción para guardar configuración
                JOptionPane.showMessageDialog(null, "Configuración guardada.");
            }
        });

        add(settingsPanel);
    }
}
