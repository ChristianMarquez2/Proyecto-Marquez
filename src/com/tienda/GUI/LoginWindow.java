package com.tienda.GUI;

import com.tienda.Clases.Administrador;
import com.tienda.Clases.UsuarioDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginWindow extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton iniciarSesionButton;

    public LoginWindow() {
        // Configuración del JFrame
        setTitle("Acceso al Sistema");
        setSize(350, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Crear el panel para el logo
        JPanel logoPanel = new JPanel();
        JLabel logoLabel = new JLabel(new ImageIcon("imagenes/LogoCell.png")); // Ruta de la imagen
        logoPanel.add(logoLabel);
        add(logoPanel, BorderLayout.NORTH);

        // Crear el panel principal para los campos de texto y el botón
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espaciado entre componentes
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Usuario:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        usernameField = new JTextField(15);
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Contraseña:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        iniciarSesionButton = new JButton("Iniciar sesión");
        iniciarSesionButton.addActionListener(new LoginActionListener());
        panel.add(iniciarSesionButton, gbc);

        add(panel, BorderLayout.CENTER);
    }

    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            try {
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                String rol = usuarioDAO.autenticarUsuarioYObtenerRol(username, password);

                if (rol != null) {
                    if (rol.equals("Cajero")) {
                        // Abrir la ventana principal para Cajero y cerrar la ventana de login
                        new MainMenuWindow(username).setVisible(true);
                    } else if (rol.equals("Administrador")) {
                        // Obtener el objeto Administrador completo
                        Administrador administrador = usuarioDAO.obtenerAdministradorPorNombre(username);
                        // Abrir la ventana principal para Administrador y cerrar la ventana de login
                        new AdminMenuWindow(administrador).setVisible(true);
                    }
                    dispose();
                } else {
                    // Mostrar mensaje de error
                    JOptionPane.showMessageDialog(LoginWindow.this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                // Mostrar mensaje de error en caso de excepción
                JOptionPane.showMessageDialog(LoginWindow.this, "Error al conectar con la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginWindow().setVisible(true));
    }
}
