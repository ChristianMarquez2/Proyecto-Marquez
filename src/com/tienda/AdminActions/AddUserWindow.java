package com.tienda.AdminActions;

import com.tienda.Clases.BaseDeDatos;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddUserWindow extends JFrame {

    private JTextField usernameField, passwordField;

    public AddUserWindow() {
        setTitle("Agregar Usuario");
        setSize(400, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 5, 5));

        // Campos de entrada
        add(new JLabel("Nombre de Usuario:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Contraseña:"));
        passwordField = new JTextField();
        add(passwordField);

        JButton saveButton = new JButton("Guardar");
        add(saveButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveUser();
            }
        });
    }

    private void saveUser() {
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = BaseDeDatos.getConnection();
            String query = "INSERT INTO usuarios (nombre, contraseña) VALUES (?, ?)";
            stmt = connection.prepareStatement(query);

            stmt.setString(1, usernameField.getText());
            stmt.setString(2, passwordField.getText());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Usuario agregado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar el usuario", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
