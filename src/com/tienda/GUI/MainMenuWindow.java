package com.tienda.GUI;

import com.tienda.CajeroActions.*;

import javax.swing.*;
import java.awt.*;

public class MainMenuWindow extends JFrame {
    private String userRole;
    private JPanel navigationPanel;
    private JButton productButton;
    private JButton billingButton;
    private JButton cartButton;
    private JButton reportsButton;
    private JButton logoutButton;
    private JTextPane infoTextPane;
    private JLabel logoLabel;
    private CartWindow cartWindow;

    public MainMenuWindow(String userRole) {
        this.userRole = userRole;
        setTitle("Menú Principal - CellTechHub");
        setSize(1000, 600); // Tamaño ajustado para una mejor visualización
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Inicializa la ventana del carrito
        cartWindow = new CartWindow(); // Crear una instancia de CartWindow

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel superior para el título y el logo
        JPanel topPanel = new JPanel(new BorderLayout());


        // Logo
        ImageIcon logoIcon = new ImageIcon("C:\\Users\\Christian\\IdeaProjects\\Proyecto-Marquez\\src\\com\\tienda\\Imagenes\\LogoCell.png"); // Ruta al archivo del logo
        logoLabel = new JLabel(logoIcon);
        topPanel.add(logoLabel, BorderLayout.EAST);

        // Texto de bienvenida
        infoTextPane = new JTextPane();
        infoTextPane.setText("Bienvenido al Menú Principal de CellTechHub.");
        infoTextPane.setEditable(false);
        infoTextPane.setFont(new Font("Arial", Font.PLAIN, 18)); // Tamaño de fuente ajustado
        infoTextPane.setBackground(getBackground());
        infoTextPane.setMargin(new Insets(10, 10, 10, 10));

        // Panel de navegación
        navigationPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH; // Ajusta para llenar todo el espacio

        // Botón de Productos
        productButton = createNavigationButton("Productos", "C:\\Users\\Christian\\IdeaProjects\\Proyecto-Marquez\\src\\com\\tienda\\Imagenes\\");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        navigationPanel.add(productButton, gbc);

        // Botón de Facturación
        billingButton = createNavigationButton("Facturación", "path/to/billing_icon.png");
        gbc.gridx = 1;
        gbc.gridy = 0;
        navigationPanel.add(billingButton, gbc);

        // Botón de Carrito
        cartButton = createNavigationButton("Carrito", "path/to/cart_icon.png");
        gbc.gridx = 0;
        gbc.gridy = 1;
        navigationPanel.add(cartButton, gbc);

        // Botón de Reportes
        reportsButton = createNavigationButton("Reportes", "path/to/reports_icon.png");
        gbc.gridx = 1;
        gbc.gridy = 1;
        navigationPanel.add(reportsButton, gbc);

        // Botón de Salir
        logoutButton = new JButton("Salir");
        logoutButton.setIcon(new ImageIcon("path/to/logout_icon.png")); // Ruta al icono de salir
        logoutButton.setPreferredSize(new Dimension(100, 50)); // Tamaño ajustado
        logoutButton.addActionListener(e -> System.exit(0)); // Cierra la aplicación
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weighty = 0; // No tomar todo el espacio vertical
        navigationPanel.add(logoutButton, gbc);

        // Agregar paneles al marco
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(infoTextPane, BorderLayout.CENTER);
        mainPanel.add(navigationPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
    }

    // Método auxiliar para crear botones de navegación con iconos
    private JButton createNavigationButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setIcon(new ImageIcon(iconPath)); // Ruta al icono
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setPreferredSize(new Dimension(50, 50)); // Tamaño ajustado
        button.addActionListener(e -> {
            // Aquí puedes agregar la lógica para cada botón
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
            }
        });
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenuWindow("Admin").setVisible(true));
    }
}
