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
    private JComboBox<String> themeComboBox;
    private JComboBox<String> languageComboBox;
    private JCheckBox notificationsCheckBox;
    private JButton saveButton;
    private JButton cancelButton;

    public SettingsWindow() {
        setTitle("Configuración");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título en la parte superior
        JLabel titleLabel = new JLabel("Configuración", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel de formulario
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Etiquetas y campos
        String[] labels = {"Nombre de Usuario:", "Contraseña:", "Tema:", "Idioma:", "Notificaciones:"};
        Component[] fields = {usernameField, passwordField, themeComboBox, languageComboBox, notificationsCheckBox};
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;

        // Agregar componentes al panel del formulario
        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            formPanel.add(label, gbc);
            gbc.gridx = 1;
            if (i == 1) { // Para la contraseña
                fields[i] = new JPasswordField(20);
            } else if (i == 2) { // Para el tema
                fields[i] = new JComboBox<>(new String[]{"Claro", "Oscuro"});
            } else if (i == 3) { // Para el idioma
                fields[i] = new JComboBox<>(new String[]{"Español", "Inglés"});
            } else if (i == 4) { // Para las notificaciones
                fields[i] = new JCheckBox();
            } else {
                fields[i] = new JTextField(20);
            }
            formPanel.add(fields[i], gbc);
            gbc.gridx = 0;
            gbc.gridy++;
        }

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        saveButton = new JButton("Guardar");
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.setForeground(Color.WHITE);
        saveButton.setBackground(new Color(0, 122, 255)); // Azul
        saveButton.setOpaque(true);
        saveButton.setBorderPainted(false);
        saveButton.setFocusPainted(false);
        buttonPanel.add(saveButton);

        cancelButton = new JButton("Cancelar");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBackground(new Color(0, 122, 255)); // Azul
        cancelButton.setOpaque(true);
        cancelButton.setBorderPainted(false);
        cancelButton.setFocusPainted(false);
        buttonPanel.add(cancelButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // Acción para guardar cambios
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarConfiguracion();
            }
        });

        // Acción para cancelar
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cerrar la ventana
            }
        });
    }

    private void guardarConfiguracion() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String theme = (String) themeComboBox.getSelectedItem();
        String language = (String) languageComboBox.getSelectedItem();
        boolean notifications = notificationsCheckBox.isSelected();

        // Validar entradas
        if (username.isEmpty() || password.isEmpty() || theme == null || language == null) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear una conexión a la base de datos
        Connection connection = null;
        try {
            connection = DatabaseUtils.getConnection(); // Implementa esta función para obtener la conexión
            ConfiguracionDAO configuracionDAO = new ConfiguracionDAO(connection);

            // Guardar la configuración
            boolean savedSuccessfully = configuracionDAO.guardarConfiguracion(username, password, theme, language, notifications);

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
