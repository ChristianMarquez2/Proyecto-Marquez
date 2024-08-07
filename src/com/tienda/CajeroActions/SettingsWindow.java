package com.tienda.CajeroActions;

import com.tienda.Clases.ConfiguracionDAO;
import com.tienda.Clases.DatabaseUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Ventana para gestionar la configuración del usuario, incluyendo nombre de usuario, contraseña, tema, idioma y notificaciones.
 * Permite al usuario guardar cambios en la configuración y cancelar la edición.
 */
public class SettingsWindow extends JFrame {

    private JTextField usernameField; // Campo para el nombre de usuario
    private JPasswordField passwordField; // Campo para la contraseña
    private JComboBox<String> themeComboBox; // ComboBox para seleccionar el tema
    private JComboBox<String> languageComboBox; // ComboBox para seleccionar el idioma
    private JCheckBox notificationsCheckBox; // CheckBox para activar/desactivar notificaciones
    private JButton saveButton; // Botón para guardar la configuración
    private JButton cancelButton; // Botón para cancelar la edición

    /**
     * Constructor que configura la ventana de configuración.
     */
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

        // Etiquetas y campos del formulario
        String[] labels = {"Nombre de Usuario:", "Contraseña:", "Tema:", "Idioma:", "Notificaciones:"};
        Component[] fields = {usernameField, passwordField, themeComboBox, languageComboBox, notificationsCheckBox};
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;

        // Configuración de los campos
        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            formPanel.add(label, gbc);
            gbc.gridx = 1;
            if (i == 1) { // Campo para la contraseña
                fields[i] = new JPasswordField(20);
            } else if (i == 2) { // ComboBox para el tema
                fields[i] = new JComboBox<>(new String[]{"Claro", "Oscuro"});
            } else if (i == 3) { // ComboBox para el idioma
                fields[i] = new JComboBox<>(new String[]{"Español", "Inglés"});
            } else if (i == 4) { // CheckBox para notificaciones
                fields[i] = new JCheckBox();
            } else { // Campo para el nombre de usuario
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

        // Botón para guardar la configuración
        saveButton = new JButton("Guardar");
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.setForeground(Color.WHITE);
        saveButton.setBackground(new Color(0, 122, 255)); // Azul
        saveButton.setOpaque(true);
        saveButton.setBorderPainted(false);
        saveButton.setFocusPainted(false);
        buttonPanel.add(saveButton);

        // Botón para cancelar la edición
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

        // Acción para guardar la configuración cuando se presiona el botón
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarConfiguracion();
            }
        });

        // Acción para cancelar la edición y cerrar la ventana
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cerrar la ventana
            }
        });
    }

    /**
     * Guarda la configuración ingresada por el usuario en la base de datos.
     * Valida las entradas antes de intentar guardar los datos.
     */
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
            connection = DatabaseUtils.getConnection();
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
            // Mostrar un mensaje de error si ocurre una excepción al conectar con la base de datos
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Cerrar la conexión a la base de datos
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
