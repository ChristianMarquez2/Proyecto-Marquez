package com.tienda.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginWindow extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginWindow() {
        setTitle("Acceso al Sistema");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear el panel principal
        JPanel panel = new JPanel(new GridLayout(3, 2));

        // Campo de usuario
        panel.add(new JLabel("Usuario:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        // Campo de contraseña
        panel.add(new JLabel("Contraseña:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        // Botones
        JButton loginButton = new JButton("Iniciar sesión");
        loginButton.addActionListener(new LoginActionListener());
        panel.add(loginButton);

        add(panel);
    }

    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            try {
                // Aquí deberías validar el usuario y la contraseña
                if (validarCredenciales(username, password)) {
                    new MainMenuWindow(username).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginWindow.this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(LoginWindow.this, "Error al conectar con la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private boolean validarCredenciales(String username, String password) throws SQLException {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            return usuarioDAO.verificarCredenciales(username, password);
        }
    }
}
