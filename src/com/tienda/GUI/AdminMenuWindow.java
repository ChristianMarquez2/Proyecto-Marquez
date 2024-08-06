package com.tienda.GUI;

import com.tienda.Clases.Administrador;
import com.tienda.Clases.AdminReportsWindow;
import com.tienda.Clases.Producto;
import com.tienda.Clases.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminMenuWindow extends JFrame {
    private Administrador administrador;
    private JPanel navigationPanel;
    private JButton productButton;
    private JButton billingButton;
    private JButton cartButton;
    private JButton reportsButton;
    private JButton settingsButton;
    private JButton logoutButton;
    private JButton adminReportsButton; // Botón para reportes administrativos
    private JTextPane infoTextPane;
    private JLabel logoLabel;
    private CartWindow cartWindow; // Añadido


        public AdminMenuWindow(Administrador administrador) {
            this.administrador = administrador;
            setTitle("Menú Principal - Administrador");
            setSize(1200, 800);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());

            // Inicializa la ventana del carrito
            cartWindow = new CartWindow();

            // Logo
            ImageIcon logoIcon = new ImageIcon("path/to/your/logo.png");
            logoLabel = new JLabel(logoIcon);
            add(logoLabel, BorderLayout.NORTH);

            // Panel de navegación
            navigationPanel = new JPanel();
            navigationPanel.setLayout(new GridLayout(8, 1, 10, 10));

            // Botones y paneles
            // ... código para inicializar botones y paneles

            // Botón de productos
            productButton = createNavigationButton("Productos", "path/to/product_icon.png");
            navigationPanel.add(productButton);

            // Botón de facturación
            billingButton = createNavigationButton("Facturación", "path/to/billing_icon.png");
            navigationPanel.add(billingButton);

            // Botón de carrito
            cartButton = createNavigationButton("Carrito", "path/to/cart_icon.png");
            navigationPanel.add(cartButton);

            // Botón de reportes
            reportsButton = createNavigationButton("Reportes", "path/to/reports_icon.png");
            navigationPanel.add(reportsButton);

            // Botón de configuración
            settingsButton = createNavigationButton("Configuración", "path/to/settings_icon.png");
            navigationPanel.add(settingsButton);

            // Botón de reportes administrativos
            adminReportsButton = new JButton("Reportes Administrativos");
            adminReportsButton.setIcon(new ImageIcon("path/to/admin_reports_icon.png"));
            adminReportsButton.addActionListener(e -> new AdminReportsWindow().setVisible(true));
            navigationPanel.add(adminReportsButton);

            // Botón de salir
            logoutButton = new JButton("Salir");
            logoutButton.setIcon(new ImageIcon("path/to/logout_icon.png"));
            logoutButton.addActionListener(e -> System.exit(0));
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
            button.setIcon(new ImageIcon(iconPath));
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.addActionListener(e -> {
                switch (text) {
                    case "Productos":
                        new ProductSelectionWindow(cartWindow).setVisible(true);
                        break;
                    case "Facturación":
                        new BillingWindow().setVisible(true);
                        break;
                    case "Carrito":
                        cartWindow.setVisible(true);
                        break;
                    case "Reportes":
                        new ReportsWindow().setVisible(true);
                        break;
                    case "Configuración":
                        new SettingsWindow().setVisible(true);
                        break;
                }
            });
            return button;
        }

    }
