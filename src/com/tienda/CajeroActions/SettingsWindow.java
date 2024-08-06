package com.tienda.CajeroActions;

import com.tienda.Clases.ConfiguracionDAO;
import com.tienda.Clases.DatabaseUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class SettingsWindow extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

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
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordField = new JPasswordField();
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
                guardarConfiguracion();
            }
        });

        add(settingsPanel);
    }

    private void guardarConfiguracion() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        // Validar entradas
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear una conexión a la base de datos
        Connection connection = null;
        try {
            connection = DatabaseUtils.getConnection(); // Implementa esta función para obtener la conexión
            ConfiguracionDAO configuracionDAO = new ConfiguracionDAO(connection);

            // Guardar la configuración
            boolean savedSuccessfully = configuracionDAO.guardarConfiguracion(username, password);

            if (savedSuccessfully) {
                JOptionPane.showMessageDialog(this, "Configuración guardada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar la configuración.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Cerrar la conexión
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
