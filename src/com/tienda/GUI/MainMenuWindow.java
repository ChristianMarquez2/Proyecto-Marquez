package com.tienda.GUI;

import com.tienda.CajeroActions.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MainMenuWindow extends JFrame {
    private String userRole;
    private JPanel navigationPanel;
    private JButton productButton;
    private JButton settingsButton;
    private JButton cartButton;
    private JButton reportsButton;
    private JButton logoutButton;
    private JTextPane infoTextPane;
    private JLabel logoLabel;
    private CartWindow cartWindow;

    public MainMenuWindow(String userRole) {
        this.userRole = userRole;
        setTitle("Menú Principal - CellTechHub");
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
        navigationPanel.setLayout(new GridLayout(3, 2, 10, 10));
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        navigationPanel.setBackground(Color.WHITE);

        // Botones de navegación
        productButton = createNavigationButton("Productos", "src/com/tienda/Imagenes/cajero/productos.png");
        navigationPanel.add(productButton);

        settingsButton = createNavigationButton("Configuración", "src/com/tienda/Imagenes/cajero/configuracion.png");
        navigationPanel.add(settingsButton);

        cartButton = createNavigationButton("Carrito", "src/com/tienda/Imagenes/cajero/carrito.png");
        navigationPanel.add(cartButton);

        reportsButton = createNavigationButton("Reportes", "src/com/tienda/Imagenes/cajero/informe.png");
        navigationPanel.add(reportsButton);

        // Botón de Salir
        logoutButton = new JButton("Salir");
        String logoutImagePath = "src/com/tienda/Imagenes/cajero/salir.png";
        logoutButton.setIcon(createScaledIcon(logoutImagePath, 20, 20));
        logoutButton.setFont(new Font("Arial", Font.BOLD, 16));
        logoutButton.setBackground(new Color(255, 69, 58));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setPreferredSize(new Dimension(120, 40));
        logoutButton.addActionListener(e -> System.exit(0));
        navigationPanel.add(logoutButton);

        // Agregar paneles al marco
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(navigationPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    private JButton createNavigationButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setIcon(createScaledIcon(iconPath, 40, 40)); // Escalar icono a 40x40 píxeles
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setPreferredSize(new Dimension(180, 80)); // Ajusta el tamaño para acomodar el texto e icono
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(240, 240, 240));
        button.setBorder(createRoundedBorder(15)); // Bordes redondeados
        button.setFocusPainted(false);
        button.addActionListener(e -> {
            switch (text) {
                case "Productos":
                    new ProductSelectionWindow(cartWindow).setVisible(true);
                    break;
                case "Configuración":
                    new SettingsWindow().setVisible(true);
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

    private ImageIcon createScaledIcon(String imagePath, int width, int height) {
        // Carga la imagen original
        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image image = originalIcon.getImage();
        // Escala la imagen al tamaño deseado
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        // Crea un nuevo ImageIcon con la imagen escalada
        return new ImageIcon(scaledImage);
    }

    private Border createRoundedBorder(int radius) {
        return new RoundedBorder(radius);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenuWindow("Admin").setVisible(true));
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
