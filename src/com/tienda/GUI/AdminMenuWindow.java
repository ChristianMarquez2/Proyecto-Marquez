package com.tienda.GUI;

import com.tienda.AdminActions.*;
import com.tienda.CajeroActions.CartWindow;
import com.tienda.CajeroActions.SettingsWindow;
import com.tienda.Clases.Administrador;
import com.tienda.Clases.AdminReportsWindow;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminMenuWindow extends JFrame {
    private Administrador administrador;
    private JPanel navigationPanel;
    private JButton productButton;
    private JButton addProductButton;
    private JButton viewSalesButton;
    private JButton addUserButton;
    private JButton deleteUserButton;
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
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Inicializa la ventana del carrito
        cartWindow = new CartWindow();

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));

        // Panel superior para el título y el logo
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(30, 144, 255)); // Azul oscuro

        ImageIcon logoIcon = new ImageIcon("src/com/tienda/Imagenes/LogoCell.png"); // Ruta al archivo del logo
        logoLabel = new JLabel(logoIcon);
        topPanel.add(logoLabel, BorderLayout.WEST);

        // Texto de bienvenida
        infoTextPane = new JTextPane();
        infoTextPane.setText("Bienvenido al Menú Principal de CellTechHub.");
        infoTextPane.setEditable(false);
        infoTextPane.setFont(new Font("Arial", Font.BOLD, 20));
        infoTextPane.setForeground(Color.WHITE);
        infoTextPane.setBackground(new Color(30, 144, 255));
        infoTextPane.setMargin(new Insets(10, 10, 10, 10));
        topPanel.add(infoTextPane, BorderLayout.CENTER);

        // Panel de navegación
        navigationPanel = new JPanel();
        navigationPanel.setLayout(new GridLayout(5, 2, 10, 10));
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        navigationPanel.setBackground(Color.WHITE);

        // Botones de navegación
        productButton = createNavigationButton("Ver Productos", "src/com/tienda/Imagenes/product_icon.png", e -> new ViewProductsWindow().setVisible(true));
        addProductButton = createNavigationButton("Agregar Producto", "src/com/tienda/Imagenes/add_product_icon.png", e -> new AddProductWindow().setVisible(true));
        navigationPanel.add(productButton);
        navigationPanel.add(addProductButton);

        viewSalesButton = createNavigationButton("Ver Ventas", "src/com/tienda/Imagenes/view_sales_icon.png", e -> new ViewSalesWindow().setVisible(true));
        adminReportsButton = createNavigationButton("Reportes Administrativos", "src/com/tienda/Imagenes/admin_reports_icon.png", e -> new AdminReportsWindow().setVisible(true));
        navigationPanel.add(viewSalesButton);
        navigationPanel.add(adminReportsButton);

        addUserButton = createNavigationButton("Agregar Usuario", "src/com/tienda/Imagenes/add_user_icon.png", e -> new AddUserWindow().setVisible(true));
        deleteUserButton = createNavigationButton("Eliminar Usuario", "src/com/tienda/Imagenes/delete_user_icon.png", e -> new DeleteUserWindow().setVisible(true));
        navigationPanel.add(addUserButton);
        navigationPanel.add(deleteUserButton);

        settingsButton = createNavigationButton("Configuración", "src/com/tienda/Imagenes/settings_icon.png", e -> new SettingsWindow().setVisible(true));
        logoutButton = createNavigationButton("Salir", "src/com/tienda/Imagenes/logout_icon.png", e -> System.exit(0));
        navigationPanel.add(settingsButton);
        navigationPanel.add(logoutButton);

        // Agregar paneles al marco
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(navigationPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    // Método auxiliar para crear botones de navegación con iconos
    private JButton createNavigationButton(String text, String iconPath, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setIcon(new ImageIcon(iconPath));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setPreferredSize(new Dimension(200, 50)); // Tamaño ajustado
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(240, 240, 240));
        button.setBorder(createRoundedBorder(15)); // Bordes redondeados
        button.setFocusPainted(false);
        button.addActionListener(actionListener);
        return button;
    }

    private Border createRoundedBorder(int radius) {
        return new RoundedBorder(radius);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminMenuWindow(new Administrador(1, "Admin", "password")).setVisible(true));
    }

    // Clase interna para bordes redondeados
    private static class RoundedBorder implements Border {
        private int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius + 2, this.radius + 2, this.radius + 2, this.radius + 2);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }
}
