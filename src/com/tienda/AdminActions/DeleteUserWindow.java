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

/**
 * Ventana para eliminar un usuario del sistema.
 * Permite seleccionar un usuario del tipo 'Cajero' desde un combo box y eliminarlo de la base de datos.
 */
public class DeleteUserWindow extends JFrame {

    private JComboBox<String> userComboBox; // Combo box para seleccionar el usuario a eliminar
    private JButton deleteButton; // Botón para confirmar la eliminación del usuario

    /**
     * Constructor que configura la ventana para eliminar un usuario.
     */
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

        // Título en la parte superior
        JLabel titleLabel = new JLabel("Eliminar Usuario Cajero", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel de formulario
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Etiqueta y combo box para seleccionar el usuario
        JLabel userLabel = new JLabel("Seleccionar Usuario:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(userLabel, gbc);

        userComboBox = new JComboBox<>(getCajeroUsuarios()); // Poblar el combo box con los usuarios disponibles
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(userComboBox, gbc);

        // Botón para eliminar el usuario
        deleteButton = new JButton("Eliminar");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBackground(new Color(255, 69, 58)); // Rojo para indicar eliminación
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

        // Acción para eliminar el usuario cuando se presiona el botón
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarUsuario(); // Llamar al método que maneja la eliminación del usuario
            }
        });

        add(mainPanel);
    }

    /**
     * Obtiene la lista de usuarios con rol 'Cajero' desde la base de datos y la carga en el combo box.
     *
     * @return Un arreglo de Strings con los nombres de los usuarios de tipo 'Cajero'.
     */
    private String[] getCajeroUsuarios() {
        List<String> cajeroUsuarios = new ArrayList<>();
        try (Connection connection = DatabaseUtils.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT nombre FROM usuarios WHERE rol = 'Cajero'")) {

            // Procesar el resultado de la consulta y agregar usuarios a la lista
            while (rs.next()) {
                cajeroUsuarios.add(rs.getString("nombre"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al obtener usuarios.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return cajeroUsuarios.toArray(new String[0]); // Convertir la lista a un arreglo de Strings
    }

    /**
     * Elimina el usuario seleccionado del combo box de la base de datos.
     * Muestra un cuadro de confirmación antes de proceder con la eliminación.
     */
    private void eliminarUsuario() {
        String selectedUser = (String) userComboBox.getSelectedItem();
        if (selectedUser == null) {
            // Mostrar un mensaje de error si no se ha seleccionado ningún usuario
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Confirmar la eliminación con el usuario
        int confirmation = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar al usuario " + selectedUser + "?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            try (Connection connection = DatabaseUtils.getConnection();
                 PreparedStatement pstmt = connection.prepareStatement("DELETE FROM usuarios WHERE nombre = ? AND rol = 'Cajero'")) {

                pstmt.setString(1, selectedUser);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    // Mostrar un mensaje de éxito si la eliminación fue exitosa
                    JOptionPane.showMessageDialog(this, "Usuario eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    dispose(); // Cerrar la ventana después de la eliminación
                } else {
                    // Mostrar un mensaje de error si no se pudo eliminar el usuario
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                // Mostrar un mensaje de error si ocurrió un problema al conectar con la base de datos
                JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
