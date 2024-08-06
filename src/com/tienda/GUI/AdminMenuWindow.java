package com.tienda.GUI;

import com.tienda.CajeroActions.BillingWindow;
import com.tienda.CajeroActions.CartWindow;
import com.tienda.CajeroActions.ReportsWindow;
import com.tienda.CajeroActions.SettingsWindow;
import com.tienda.Clases.Administrador;
import com.tienda.Clases.AdminReportsWindow;
import com.tienda.Clases.Producto;
import com.tienda.Clases.Usuario;

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
    private JButton billingButton;
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
        navigationPanel.setLayout(new GridLayout(10, 1, 10, 10));

        // Botón de productos
        productButton = createNavigationButton("Ver Productos", "path/to/product_icon.png", e -> administrador.verProductos());
        navigationPanel.add(productButton);

        // Botón para agregar productos
        addProductButton = createNavigationButton("Agregar Producto", "path/to/add_product_icon.png", e -> agregarProducto());
        navigationPanel.add(addProductButton);

        // Botón para ver ventas
        viewSalesButton = createNavigationButton("Ver Ventas", "path/to/view_sales_icon.png", e -> administrador.generarInformeVentas());
        navigationPanel.add(viewSalesButton);

        // Botón para agregar usuarios
        addUserButton = createNavigationButton("Agregar Usuario", "path/to/add_user_icon.png", e -> agregarUsuario());
        navigationPanel.add(addUserButton);

        // Botón de facturación
        billingButton = createNavigationButton("Facturación", "path/to/billing_icon.png", e -> new BillingWindow().setVisible(true));
        navigationPanel.add(billingButton);

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

    // Método para agregar un producto (abrir una ventana o mostrar un diálogo)
    private void agregarProducto() {
        Producto nuevoProducto = new Producto(0, "Nuevo Producto", 100.0, 50, "path/to/image.jpg", 1, "Modelo X");
        administrador.agregarProducto(nuevoProducto);
        JOptionPane.showMessageDialog(this, "Producto agregado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    // Método para agregar un usuario (abrir una ventana o mostrar un diálogo)
    private void agregarUsuario() {
        Usuario nuevoUsuario = new Usuario(0, "Nuevo Usuario", "contraseña"); // Ejemplo de usuario
        administrador.agregarUsuario(nuevoUsuario);
        JOptionPane.showMessageDialog(this, "Usuario agregado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
}
