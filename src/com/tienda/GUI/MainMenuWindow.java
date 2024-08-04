package com.tienda.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuWindow extends JFrame {
    private String userRole;
    private JPanel navigationPanel;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton productButton;
    private JButton billingButton;
    private JButton cartButton;
    private JButton reportsButton;
    private JButton settingsButton;
    private JButton logoutButton;
    private JTextPane infoTextPane;
    private JLabel logoLabel;

    public MainMenuWindow(String userRole) {
        this.userRole = userRole;
        setTitle("Menú Principal");
        setSize(1000, 600); // Tamaño ajustado para una mejor visualización
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Logo
        ImageIcon logoIcon = new ImageIcon("path/to/your/logo.png"); // Ruta al archivo del logo
        logoLabel = new JLabel(logoIcon);
        add(logoLabel, BorderLayout.NORTH);

        // Panel de navegación
        navigationPanel = new JPanel();
        navigationPanel.setLayout(new GridLayout(7, 1, 10, 10)); // 7 filas para incluir todos los botones

        // Botón de Productos
        productButton = createNavigationButton("Productos", "path/to/product_icon.png"); // Ruta al icono
        navigationPanel.add(productButton);

        // Botón de Facturación
        billingButton = createNavigationButton("Facturación", "path/to/billing_icon.png");
        navigationPanel.add(billingButton);

        // Botón de Carrito
        cartButton = createNavigationButton("Carrito", "path/to/cart_icon.png");
        navigationPanel.add(cartButton);

        // Botón de Reportes
        reportsButton = createNavigationButton("Reportes", "path/to/reports_icon.png");
        navigationPanel.add(reportsButton);

        // Botón de Configuración
        settingsButton = createNavigationButton("Configuración", "path/to/settings_icon.png");
        navigationPanel.add(settingsButton);

        // Botón de Salir
        logoutButton = new JButton("Salir");
        logoutButton.setIcon(new ImageIcon("path/to/logout_icon.png")); // Ruta al icono de salir
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Cierra la aplicación
            }
        });
        navigationPanel.add(logoutButton);

        // Panel de información
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoTextPane = new JTextPane();
        infoTextPane.setText("Bienvenido al Menú Principal de CellTechHub.");
        infoTextPane.setEditable(false);
        infoPanel.add(infoTextPane, BorderLayout.CENTER);

        // Agregar paneles al marco
        add(navigationPanel, BorderLayout.WEST);
        add(infoPanel, BorderLayout.CENTER);
    }

    // Método auxiliar para crear botones de navegación con iconos
    private JButton createNavigationButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setIcon(new ImageIcon(iconPath)); // Ruta al icono
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí puedes agregar la lógica para cada botón
                switch (text) {
                    case "Productos":
                        new ProductSelectionWindow().setVisible(true);
                        break;
                    case "Facturación":
                        new BillingWindow().setVisible(true);
                        break;
                    case "Carrito":
                        new CartWindow().setVisible(true);
                        break;
                    case "Reportes":
                        new ReportsWindow().setVisible(true);
                        break;
                    case "Configuración":
                        new SettingsWindow().setVisible(true);
                        break;
                }
            }
        });
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainMenuWindow("Admin").setVisible(true);
            }
        });
    }
}
