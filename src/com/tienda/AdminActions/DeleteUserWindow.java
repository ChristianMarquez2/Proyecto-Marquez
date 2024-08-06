package com.tienda.AdminActions;

import com.tienda.Clases.DatabaseUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DeleteUserWindow extends JFrame {
    private JComboBox<String> userComboBox;
    private JButton deleteButton;

    public DeleteUserWindow() {
        setTitle("Eliminar Usuario");
        setSize(500, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Logo en la parte superior
        JLabel titleLabel = new JLabel("Eliminar Usuario Cajero", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel de formulario
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Etiqueta y combo box
        JLabel userLabel = new JLabel("Seleccionar Usuario:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(userLabel, gbc);

        userComboBox = new JComboBox<>(getCajeroUsuarios());
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(userComboBox, gbc);

        // Botón eliminar
        deleteButton = new JButton("Eliminar");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBackground(new Color(255, 69, 58)); // Rojo
        deleteButton.setOpaque(true);
        deleteButton.setBorderPainted(false);
        deleteButton.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(deleteButton, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Acción para eliminar usuario
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarUsuario();
            }
        });

        add(mainPanel);
    }

    private String[] getCajeroUsuarios() {
        List<String> cajeroUsuarios = new ArrayList<>();
        try (Connection connection = DatabaseUtils.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT nombre FROM usuarios WHERE rol = 'Cajero'")) {

            while (rs.next()) {
                cajeroUsuarios.add(rs.getString("nombre"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return cajeroUsuarios.toArray(new String[0]);
    }

    private void eliminarUsuario() {
        String selectedUser = (String) userComboBox.getSelectedItem();
        if (selectedUser == null) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar al usuario " + selectedUser + "?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            try (Connection connection = DatabaseUtils.getConnection();
                 PreparedStatement pstmt = connection.prepareStatement("DELETE FROM usuarios WHERE nombre = ? AND rol = 'Cajero'")) {

                pstmt.setString(1, selectedUser);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Usuario eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    dispose(); // Cerrar la ventana
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
