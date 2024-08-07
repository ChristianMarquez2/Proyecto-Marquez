package com.tienda.GUI;

import com.tienda.CajeroActions.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
/**
 * Ventana principal de la aplicación de tienda que muestra el menú de navegación principal.
 * Esta ventana permite al usuario acceder a diferentes secciones como productos, configuración,
 * carrito y reportes. También incluye un botón para cerrar la aplicación.
 *
 * <p>Dependiendo del rol del usuario, algunos botones pueden estar habilitados o deshabilitados.</p>
 *
 * @since 1.0
 */
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

    /**
     * Crea una nueva ventana del menú principal.
     *
     * @param userRole El rol del usuario que accede a la ventana, determina qué funcionalidades están disponibles.
     */
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
        topPanel.setBackground(new Color(30, 144, 255));

        ImageIcon logoIcon = new ImageIcon("src/com/tienda/Imagenes/LogoCell.png");
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

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(navigationPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    /**
     * Crea un botón de navegación con el texto especificado y el icono asociado.
     *
     * @param text El texto que se mostrará en el botón.
     * @param iconPath La ruta al archivo de imagen del icono del botón.
     * @return El botón de navegación creado.
     */
    private JButton createNavigationButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setIcon(createScaledIcon(iconPath, 40, 40));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setPreferredSize(new Dimension(180, 80));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(240, 240, 240));
        button.setBorder(createRoundedBorder(15));
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

    /**
     * Crea un {@link ImageIcon} redimensionado a las dimensiones especificadas.
     *
     * @param imagePath La ruta al archivo de imagen.
     * @param width El ancho de la imagen redimensionada.
     * @param height La altura de la imagen redimensionada.
     * @return El {@link ImageIcon} redimensionado.
     */
    private ImageIcon createScaledIcon(String imagePath, int width, int height) {
        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image image = originalIcon.getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    /**
     * Crea un borde redondeado con el radio especificado.
     *
     * @param radius El radio del borde redondeado.
     * @return El borde redondeado creado.
     */
    private Border createRoundedBorder(int radius) {
        return new RoundedBorder(radius);
    }

    /**
     * Clase interna para crear un borde redondeado.
     */
    private static class RoundedBorder implements Border {
        private int radius;

        /**
         * Crea un borde redondeado con el radio especificado.
         *
         * @param radius El radio del borde redondeado.
         */
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

    /**
     * Método principal para ejecutar la aplicación.
     *
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenuWindow("Admin").setVisible(true));
    }
}
