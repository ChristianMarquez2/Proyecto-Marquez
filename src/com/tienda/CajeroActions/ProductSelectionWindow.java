package com.tienda.CajeroActions;

import com.tienda.Clases.Producto;
import com.tienda.Clases.ProductoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ProductSelectionWindow extends JFrame {
    private JComboBox<String> marcaComboBox;
    private JComboBox<String> modeloComboBox;
    private JTable productoTable;
    private DefaultTableModel tableModel;
    private JLabel imageLabel;
    private ProductoDAO productoDAO;
    private CartWindow cartWindow; // Añadido

    public ProductSelectionWindow(CartWindow cartWindow) { // Modificado
        setTitle("Selección de Productos");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        this.cartWindow = cartWindow; // Inicializa la instancia de CartWindow

        productoDAO = new ProductoDAO();

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Añadido margen

        // Panel de selección
        JPanel seleccionPanel = new JPanel();
        seleccionPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Añadido espaciado entre componentes

        // ComboBox de marcas
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        seleccionPanel.add(new JLabel("Marca:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        marcaComboBox = new JComboBox<>(new String[]{"Samsung", "Apple", "Xiaomi", "Huawei", "Sony"});
        marcaComboBox.addActionListener(e -> actualizarModelos());
        seleccionPanel.add(marcaComboBox, gbc);

        // ComboBox de modelos
        gbc.gridx = 0;
        gbc.gridy = 1;
        seleccionPanel.add(new JLabel("Modelo:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        modeloComboBox = new JComboBox<>();
        modeloComboBox.addActionListener(e -> mostrarProductos());
        seleccionPanel.add(modeloComboBox, gbc);

        mainPanel.add(seleccionPanel, BorderLayout.NORTH);

        // Tabla para mostrar productos
        tableModel = new DefaultTableModel(new Object[]{"Nombre", "Modelo", "Precio", "Stock"}, 0);
        productoTable = new JTable(tableModel);
        productoTable.setFillsViewportHeight(true);
        productoTable.setPreferredScrollableViewportSize(new Dimension(750, 300));
        mainPanel.add(new JScrollPane(productoTable), BorderLayout.CENTER);

        // Etiqueta para mostrar imagen del producto
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        mainPanel.add(imageLabel, BorderLayout.SOUTH);

        // Botón de añadir al carrito
        JButton addToCartButton = new JButton("Añadir al Carrito");
        addToCartButton.setBackground(new Color(0, 123, 255)); // Color de fondo
        addToCartButton.setForeground(Color.WHITE); // Color de texto
        addToCartButton.setFocusPainted(false);
        addToCartButton.setPreferredSize(new Dimension(200, 40)); // Tamaño preferido
        addToCartButton.addActionListener(e -> addProductToCart());
        mainPanel.add(addToCartButton, BorderLayout.SOUTH);

        add(mainPanel);

        // Cargar modelos al iniciar
        actualizarModelos();
    }

    private void actualizarModelos() {
        String marcaSeleccionada = (String) marcaComboBox.getSelectedItem();
        modeloComboBox.removeAllItems();

        // Añadir modelos según la marca seleccionada
        try {
            List<Producto> productos = productoDAO.obtenerProductosPorMarca(obtenerMarcaId(marcaSeleccionada));
            for (Producto producto : productos) {
                modeloComboBox.addItem(producto.getModelo());
            }
            if (modeloComboBox.getItemCount() == 0) {
                modeloComboBox.addItem("No hay modelos disponibles");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al obtener modelos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarProductos() {
        String modeloSeleccionado = (String) modeloComboBox.getSelectedItem();
        tableModel.setRowCount(0); // Limpiar la tabla
        imageLabel.setIcon(null); // Limpiar la imagen

        // Mostrar productos según el modelo seleccionado
        try {
            List<Producto> productos = productoDAO.obtenerProductosPorModelo(modeloSeleccionado);
            if (productos.isEmpty()) {
                tableModel.addRow(new Object[]{"No hay productos disponibles", "", "", ""});
            } else {
                for (Producto producto : productos) {
                    tableModel.addRow(new Object[]{
                            producto.getNombre(),
                            producto.getModelo(),
                            "$" + producto.getPrecio(),
                            producto.getStock()
                    });
                    // Cargar imagen del producto (puedes añadir el campo de imagen en la clase Producto si es necesario)
                    // Si tienes una ruta de imagen, puedes cargarla aquí
                    // ImageIcon productImage = new ImageIcon("ruta/a/imagen.png");
                    // imageLabel.setIcon(productImage);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al obtener productos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addProductToCart() {
        int selectedRow = productoTable.getSelectedRow();
        if (selectedRow >= 0) {
            String nombre = (String) tableModel.getValueAt(selectedRow, 0);
            String modelo = (String) tableModel.getValueAt(selectedRow, 1);
            double precio = Double.parseDouble(((String) tableModel.getValueAt(selectedRow, 2)).replace("$", ""));
            cartWindow.addProductToCart(nombre, modelo, precio, 1); // Agrega al carrito con cantidad 1
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para añadir al carrito.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private int obtenerMarcaId(String marcaNombre) {
        switch (marcaNombre) {
            case "Samsung":
                return 1;
            case "Apple":
                return 2;
            case "Xiaomi":
                return 3;
            case "Huawei":
                return 4;
            case "Sony":
                return 5;
            default:
                return -1;
        }
    }
}
