package com.tienda.AdminActions;

import com.tienda.Clases.BaseDeDatos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Ventana para agregar un nuevo usuario al sistema.
 * Permite ingresar el nombre de usuario, contraseña y rol.
 */
public class AddUserWindow extends JFrame {

    private JTextField usernameField, passwordField;
    private JComboBox<String> roleComboBox;
    private JButton saveButton;
    private JButton cancelButton;

    /**
     * Constructor que configura la ventana para agregar un nuevo usuario.
     */
    public AddUserWindow() {
        setTitle("Agregar Usuario");
        setSize(500, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título en la parte superior
        JLabel titleLabel = new JLabel("Agregar Nuevo Usuario", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel de formulario
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Etiqueta y campo de nombre de usuario
        JLabel usernameLabel = new JLabel("Nombre de Usuario:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(usernameField, gbc);

        // Etiqueta y campo de contraseña
        JLabel passwordLabel = new JLabel("Contraseña:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(passwordField, gbc);

        // Etiqueta y combo box para rol
        JLabel roleLabel = new JLabel("Rol:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(roleLabel, gbc);

        roleComboBox = new JComboBox<>(new String[]{"Administrador", "Cajero"});
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(roleComboBox, gbc);

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

        // Agregar paneles al marco
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // Acción para guardar usuario
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveUser();
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

    /**
     * Método para guardar un nuevo usuario en la base de datos.
     * Obtiene los datos del formulario y los inserta en la base de datos.
     */
    private void saveUser() {
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = BaseDeDatos.getConnection();
            String query = "INSERT INTO usuarios (nombre, rol, contraseña) VALUES (?, ?, ?)";
            stmt = connection.prepareStatement(query);

            // Configurar los parámetros de la consulta
            stmt.setString(1, usernameField.getText());
            stmt.setString(2, (String) roleComboBox.getSelectedItem());
            stmt.setString(3, new String(((JPasswordField) passwordField).getPassword()));

            // Ejecutar la consulta
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Usuario agregado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Cerrar la ventana si se guardó correctamente
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar el usuario", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                // Cerrar los recursos
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
