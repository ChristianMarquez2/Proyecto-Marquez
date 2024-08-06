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
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Crear el panel para el logo
        JPanel logoPanel = new JPanel();
        // Redimensionar el logo
        ImageIcon originalLogo = new ImageIcon("src/com/tienda/Imagenes/LogoCell.png");
        Image logoImage = originalLogo.getImage().getScaledInstance(300, 100, Image.SCALE_SMOOTH); // Ajusta el tamaño aquí
        JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
        logoPanel.add(logoLabel);
        add(logoPanel, BorderLayout.NORTH);

        // Crear el panel principal para los campos de texto y el botón
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espaciado entre componentes

        // Usuario
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel userLabel = new JLabel("Usuario:");
        userLabel.setIcon(new ImageIcon("src/com/tienda/Imagenes/user_icon.png")); // Icono de usuario
        panel.add(userLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        usernameField = new JTextField(15);
        panel.add(usernameField, gbc);

        // Contraseña
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setIcon(new ImageIcon("src/com/tienda/Imagenes/password_icon.png")); // Icono de contraseña
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        // Botón de iniciar sesión
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        iniciarSesionButton = new JButton("Iniciar sesión") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Radio de bordes redondeados
                super.paintComponent(g);
                g2d.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getForeground());
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20); // Radio de bordes redondeados
                g2d.dispose();
            }
        };
        iniciarSesionButton.setFont(new Font("Arial", Font.BOLD, 14));
        iniciarSesionButton.setBackground(new Color(30, 144, 255));
        iniciarSesionButton.setForeground(Color.WHITE);
        iniciarSesionButton.setFocusPainted(false);
        iniciarSesionButton.setPreferredSize(new Dimension(150, 40));
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
