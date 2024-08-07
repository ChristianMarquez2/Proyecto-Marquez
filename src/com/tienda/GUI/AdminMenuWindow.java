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

/**
 * La clase {@code AdminMenuWindow} representa la ventana principal del menú para el administrador en la aplicación.
 * Esta ventana proporciona acceso a diversas funcionalidades administrativas, incluyendo la visualización y adición de productos,
 * la gestión de usuarios y la generación de reportes.
 */
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

    /**
     * Crea una instancia de {@code AdminMenuWindow} y configura la ventana principal para el administrador.
     *
     * @param administrador El objeto {@code Administrador} asociado a esta ventana.
     */
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
        navigationPanel.setLayout(new GridLayout(5, 2, 10, 10));
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        navigationPanel.setBackground(Color.WHITE);

        // Botones de navegación
        productButton = createNavigationButton("Ver Productos", "C:\\Users\\Christian\\IdeaProjects\\Proyecto-Marquez\\src\\com\\tienda\\Imagenes\\Admin\\verproducto.png", e -> new ViewProductsWindow().setVisible(true));
        addProductButton = createNavigationButton("Agregar Producto", "C:\\Users\\Christian\\IdeaProjects\\Proyecto-Marquez\\src\\com\\tienda\\Imagenes\\Admin\\agregar.png", e -> new AddProductWindow().setVisible(true));
        navigationPanel.add(productButton);
        navigationPanel.add(addProductButton);

        viewSalesButton = createNavigationButton("Ver Ventas", "C:\\Users\\Christian\\IdeaProjects\\Proyecto-Marquez\\src\\com\\tienda\\Imagenes\\Admin\\verventas.png", e -> new ViewSalesWindow().setVisible(true));
        adminReportsButton = createNavigationButton("Reportes Administrativos", "C:\\Users\\Christian\\IdeaProjects\\Proyecto-Marquez\\src\\com\\tienda\\Imagenes\\Admin\\reportes.png", e -> new AdminReportsWindow().setVisible(true));
        navigationPanel.add(viewSalesButton);
        navigationPanel.add(adminReportsButton);

        addUserButton = createNavigationButton("Agregar Usuario", "C:\\Users\\Christian\\IdeaProjects\\Proyecto-Marquez\\src\\com\\tienda\\Imagenes\\Admin\\agregar-usuario.png", e -> new AddUserWindow().setVisible(true));
        deleteUserButton = createNavigationButton("Eliminar Usuario", "C:\\Users\\Christian\\IdeaProjects\\Proyecto-Marquez\\src\\com\\tienda\\Imagenes\\Admin\\borrar.png", e -> new DeleteUserWindow().setVisible(true));
        navigationPanel.add(addUserButton);
        navigationPanel.add(deleteUserButton);

        settingsButton = createNavigationButton("Configuración", "C:\\Users\\Christian\\IdeaProjects\\Proyecto-Marquez\\src\\com\\tienda\\Imagenes\\Admin\\configuracion.png", e -> new SettingsWindow().setVisible(true));
        logoutButton = createNavigationButton("Salir", "C:\\Users\\Christian\\IdeaProjects\\Proyecto-Marquez\\src\\com\\tienda\\Imagenes\\Admin\\salir.png", e -> System.exit(0));
        navigationPanel.add(settingsButton);
        navigationPanel.add(logoutButton);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(navigationPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    /**
     * Crea un botón de navegación con texto, icono y acción asociada.
     *
     * @param text El texto del botón.
     * @param iconPath La ruta del icono para el botón.
     * @param actionListener El {@code ActionListener} asociado al botón.
     * @return El botón creado.
     */
    private JButton createNavigationButton(String text, String iconPath, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setIcon(createScaledIcon(iconPath, 30, 30));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setPreferredSize(new Dimension(200, 50));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(240, 240, 240));
        button.setBorder(createRoundedBorder(15));
        button.setFocusPainted(false);
        button.addActionListener(actionListener);
        return button;
    }

    /**
     * Crea un {@code ImageIcon} escalado con el tamaño especificado.
     *
     * @param imagePath La ruta de la imagen.
     * @param width El ancho deseado.
     * @param height La altura deseada.
     * @return El {@code ImageIcon} escalado.
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
     * El punto de entrada de la aplicación. Crea y muestra una instancia de {@code AdminMenuWindow}.
     *
     * @param args Los argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminMenuWindow(new Administrador(1, "Admin", "password")).setVisible(true));
    }

    /**
     * La clase {@code RoundedBorder} implementa un borde redondeado para los componentes.
     */
    private static class RoundedBorder implements Border {
        private int radius;

        /**
         * Crea un {@code RoundedBorder} con el radio especificado.
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
}
