package com.tienda.GUI;

import com.tienda.AdminActions.AddProductWindow;
import com.tienda.AdminActions.AddUserWindow;
import com.tienda.AdminActions.ViewProductsWindow;
import com.tienda.AdminActions.ViewSalesWindow;
import com.tienda.CajeroActions.CartWindow;
import com.tienda.CajeroActions.ReportsWindow;
import com.tienda.CajeroActions.SettingsWindow;
import com.tienda.Clases.Administrador;
import com.tienda.Clases.AdminReportsWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminMenuWindow extends JFrame {
    private Administrador administrador;
    private JPanel navigationPanel;
    private JButton productButton;
    private JButton addProductButton;
    private JButton viewSalesButton;
    private JButton addUserButton;
    private JButton cartButton;
    private JButton reportsButton;
    private JButton settingsButton;
    private JButton logoutButton;
    private JButton adminReportsButton;
    private JTextPane infoTextPane;
    private JLabel logoLabel;
    private CartWindow cartWindow;

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
        navigationPanel.setLayout(new GridLayout(9, 1, 10, 10)); // Ajuste el número de filas a 9

        // Botón para ver productos
        productButton = createNavigationButton("Ver Productos", "path/to/product_icon.png", e -> new ViewProductsWindow().setVisible(true));
        navigationPanel.add(productButton);

        // Botón para agregar productos
        addProductButton = createNavigationButton("Agregar Producto", "path/to/add_product_icon.png", e -> new AddProductWindow().setVisible(true));
        navigationPanel.add(addProductButton);

        // Botón para ver ventas
        viewSalesButton = createNavigationButton("Ver Ventas", "path/to/view_sales_icon.png", e -> new ViewSalesWindow().setVisible(true));
        navigationPanel.add(viewSalesButton);

        // Botón para agregar usuarios
        addUserButton = createNavigationButton("Agregar Usuario", "path/to/add_user_icon.png", e -> new AddUserWindow().setVisible(true));
        navigationPanel.add(addUserButton);

        // Botón de carrito
        cartButton = createNavigationButton("Carrito", "path/to/cart_icon.png", e -> cartWindow.setVisible(true));
        navigationPanel.add(cartButton);

        // Botón de reportes
        reportsButton = createNavigationButton("Reportes", "path/to/reports_icon.png", e -> new ReportsWindow().setVisible(true));
        navigationPanel.add(reportsButton);

        // Botón de configuración
        settingsButton = createNavigationButton("Configuración", "path/to/settings_icon.png", e -> new SettingsWindow().setVisible(true));
        navigationPanel.add(settingsButton);

        // Botón de reportes administrativos
        adminReportsButton = createNavigationButton("Reportes Administrativos", "path/to/admin_reports_icon.png", e -> new AdminReportsWindow().setVisible(true));
        navigationPanel.add(adminReportsButton);

        // Botón de salir
        logoutButton = createNavigationButton("Salir", "path/to/logout_icon.png", e -> System.exit(0));
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
    private JButton createNavigationButton(String text, String iconPath, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setIcon(new ImageIcon(iconPath));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.addActionListener(actionListener);
        return button;
    }
}
