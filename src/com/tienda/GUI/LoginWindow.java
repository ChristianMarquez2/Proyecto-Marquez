package com.tienda.GUI;

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
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear el panel principal con GridLayout
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10)); // Añadido espaciado entre componentes

        // Campo de usuario
        panel.add(new JLabel("Usuario:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        // Campo de contraseña
        panel.add(new JLabel("Contraseña:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        // Botón de inicio de sesión
        iniciarSesionButton = new JButton("Iniciar sesión");
        iniciarSesionButton.addActionListener(new LoginActionListener());
        panel.add(iniciarSesionButton);

        // Añadir el panel al JFrame
        add(panel);
    }

    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            try {
                // Validar credenciales
                if (validarCredenciales(username, password)) {
                    // Abrir la ventana principal y cerrar la ventana de login
                    new MainMenuWindow(username).setVisible(true);
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

        private boolean validarCredenciales(String username, String password) throws SQLException {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            return usuarioDAO.verificarCredenciales(username, password);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginWindow().setVisible(true));
    }
}
